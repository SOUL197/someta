package kr.co.ictedu.someta.chart;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.someta.vo.MemberVO;


@RestController
@RequestMapping("/chart")
public class ChartController {
	@Autowired
	private ChartService chartService;
	
	// =========================
    // 방문자 통계
    // =========================
    @GetMapping("/visitorStats")
    public Map<String, Object> visitorStats() {
    	Map<String, Object> map = new HashMap<>();
        map.put("userTotal", chartService.userTotalCount());        // 총 가입자
        map.put("activeUser", chartService.activeUserCount());      // 최근 7일 로그인 사용자
        map.put("genderCount", chartService.genderCount());         // 성별 통계
        map.put("addrCount", chartService.addrCount());             // 시/도 통계
        map.put("dailyMatch", chartService.dailyMatch());           // 최근 30일 매칭 수

        return map;
    }
    @GetMapping("/districtCount")
    public List<Map<String, Object>> districtCount(@RequestParam("sido") String sido) {
        return chartService.districtCount(sido);
    }

    // =========================
    // 사용자 통계
    // =========================
    @GetMapping("/userStats")
    public Map<String,Object> getUserStats(@RequestParam("nickname") String nickname, @RequestParam("num") int num) {
        Map<String,Object> stats = new HashMap<>();
        stats.put("likeCount", chartService.likeCount(nickname));
        stats.put("matchCount", chartService.matchCount(nickname));
        stats.put("dateCount", chartService.dateCount(nickname));
        stats.put("weeklyMatch", chartService.weeklyMatch(nickname));
        stats.put("activityHeatmap", chartService.activityHeatmap(num));
        stats.put("avgResponseRate", chartService.avgResponseRate());

        int totalLikes = chartService.likeCount(nickname);
        int responded = chartService.responseCount(nickname);
        stats.put("responseRate", totalLikes == 0 ? 0 : ((double) responded / totalLikes) * 100);
        return stats;
    }

    // =========================
    // 관리자 통계
    // =========================
    @GetMapping("/adminStats")
    public Map<String,Object> getAdminStats() {
        Map<String,Object> stats = new HashMap<>();
        stats.put("dau", chartService.dauCount());
        stats.put("mau", chartService.mauCount());
        stats.put("funnel", chartService.adminFunnel());
        stats.put("genderAge", chartService.genderAge());
        stats.put("weeklyVisitor", chartService.weeklyVisitor());
        stats.put("churnRate", calculateChurnRate());
        stats.put("conversionRate", calculateConversionRate());
        return stats;
    }

    private double calculateChurnRate() {
        int totalUsers = chartService.userTotalCount();
        int churned = chartService.churnCount();
        return totalUsers == 0 ? 0 : ((double) churned / totalUsers) * 100;
    }

    private double calculateConversionRate() {
        int visitors = chartService.visitorCount();
        int newMembers = chartService.newMemberCount();
        return visitors == 0 ? 0 : ((double) newMembers / visitors) * 100;
    }
}
