package kr.co.ictedu.someta.chart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.someta.vo.MemberVO;


@RestController
@RequestMapping("/chart")
public class ChartController {
	@Autowired
	private ChartService chartService;
	
	@GetMapping("/utotal")
	public int userTotalCount() {
		return chartService.userTotalCount();
	}
	@GetMapping("/autotal")
	public int activeUserCount() {
		return chartService.activeUserCount();
	}	
	@GetMapping("/dmtotal")
	public int dailyMatchCount() {
		return chartService.dailyMatchCount();
	}
	@GetMapping("/gcount")
	public List<Map<String, Object>> genderCount() {
		return chartService.genderCount();
	}
	@GetMapping("/addrcount")
	public List<Map<String, Object>> addrCount() {
		return chartService.addrCount();
	}
	
	@GetMapping("/ulcount")
	public int likeCount(@RequestBody MemberVO vo){
//		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
//		return chartService.likeCount(loginMember.getUserid());
		return chartService.likeCount(vo.getId());
	}
	@GetMapping("/umcount")
	public int matchCount(@RequestBody MemberVO vo){
//		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
//		return chartService.matchCount(loginMember.getUserid());
		return chartService.matchCount(vo.getId());
	}
	@GetMapping("/udcount")
	public int dateCount(@RequestBody MemberVO vo){
//		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
//		return chartService.dateCount(loginMember.getUserid());
		return chartService.dateCount(vo.getId());
	}
	@GetMapping("/urcount")
	public double responseCount(@RequestBody MemberVO vo){
//		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
//		return chartService.responseCount(loginMember.getUserid());
		int total = chartService.likeCount(vo.getId());
		int resp = chartService.responseCount(vo.getId());
		double respRate = (double) resp/total * 100;
		return respRate;
	}
	@GetMapping("/uacount")
	public int sstimeAverage(@RequestBody MemberVO vo){
//		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
//		return chartService.responseCount(loginMember.getUserid());
		return chartService.sstimeAverage(vo.getId());
	}
	
	@GetMapping("/adCount")
	public int dauCount() {
		return chartService.dauCount();
	}
	@GetMapping("/awCount")
	public int wauCount() {
		return chartService.wauCount();
	}
	@GetMapping("/amCount")
	public int mauCount() {
		return chartService.mauCount();
	}
	@GetMapping("/ayCount")
	public int yauCount() {
		return chartService.yauCount();
	}
	@GetMapping("/asCount")
	public double sticknessCount() {
		int dau = chartService.dauCount();
		int mau = chartService.mauCount();
		double stickness = (double) dau/mau * 100;
		return stickness;
	}
	@GetMapping("/acCount")
	public double conversionCount() {
		int reg = chartService.userTotalCount();
		int visitor = chartService.visitorCount();
		double conversionRate = (double) reg/visitor * 100;
		return conversionRate;
	}
	@GetMapping("/achCount")
	public double churnCount() {
		int reg = chartService.userTotalCount();
		int churn = chartService.churnCount();
		double churnRate = (double) churn/reg * 100;
		return churnRate;
	}
}
