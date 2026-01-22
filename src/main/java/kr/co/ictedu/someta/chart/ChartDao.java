package kr.co.ictedu.someta.chart;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChartDao {
	int userTotalCount();
	int activeUserCount();
	List<Map<String, Object>> genderCount();
	List<Map<String, Object>> addrCount();	
	List<Map<String, Object>> districtCount(String sido);	
	List<Map<String, Object>> dailyMatch();
	
	int likeCount(String nickname);
	int matchCount(String nickname);
	int dateCount(String nickname);
	List<Map<String, Object>> weeklyMatch(String nickname);
	List<Map<String, Object>> activityHeatmap(int num);
	int responseCount(String nickname);
	double avgResponseRate();
	
	int dauCount();
	int mauCount();
	List<Map<String, Object>> adminFunnel();
	List<Map<String, Object>> genderAge();
	List<Map<String, Object>> weeklyVisitor();
	List<Map<String, Object>> churnCount(List<Integer> weeks);
	int visitorCount();
	int newMemberCount();
}
