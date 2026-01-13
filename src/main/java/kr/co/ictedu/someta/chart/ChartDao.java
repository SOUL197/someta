package kr.co.ictedu.someta.chart;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChartDao {
	int userTotalCount();
	int activeUserCount();
	int dailyMatchCount();
	List<Map<String, Object>> genderCount();
	List<Map<String, Object>> addrCount();
	
	int likeCount(String userid);
	int matchCount(String userid);
	int dateCount(String userid);
	int responseCount(String userid);
	int sstimeAverage(String userid);
	
	int dauCount();
	int wauCount();
	int mauCount();
	int yauCount();
	int visitorCount();
	int churnCount();
}
