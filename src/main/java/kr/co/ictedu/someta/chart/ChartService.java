package kr.co.ictedu.someta.chart;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChartService {
	@Autowired
	private ChartDao chartDao;
	
	public int userTotalCount() {return chartDao.userTotalCount();}
	public int activeUserCount() {return chartDao.activeUserCount();}
	public List<Map<String, Object>> genderCount() {return chartDao.genderCount();}
	public List<Map<String, Object>> addrCount() {return chartDao.addrCount();}
	public List<Map<String, Object>> districtCount(String sido) {return chartDao.districtCount(sido);}
	public List<Map<String, Object>> dailyMatch() {return chartDao.dailyMatch();}
	
	public int likeCount(String nickname) {return chartDao.likeCount(nickname);}
	public int matchCount(String nickname) {return chartDao.matchCount(nickname);}
	public int dateCount(String nickname) {return chartDao.dateCount(nickname);}
	public List<Map<String, Object>> weeklyMatch(String nickname) {return chartDao.weeklyMatch(nickname);}
	public List<Map<String, Object>> activityHeatmap(int num) {return chartDao.activityHeatmap(num);}
	public int responseCount(String nickname) {return chartDao.responseCount(nickname);}
	public double avgResponseRate() {return chartDao.avgResponseRate();}
	
	public int dauCount() {return chartDao.dauCount();}
	public int mauCount() {return chartDao.mauCount();}
	public List<Map<String, Object>> adminFunnel() {return chartDao.adminFunnel();}
	public List<Map<String, Object>> genderAge() {return chartDao.genderAge();}
	public List<Map<String, Object>> weeklyVisitor() {return chartDao.weeklyVisitor();}
	public List<Map<String, Object>> churnCount(List<Integer> weeks) {return chartDao.churnCount(weeks);}
	public int visitorCount() {return chartDao.visitorCount();}
	public int newMemberCount() {return chartDao.newMemberCount();}
}
