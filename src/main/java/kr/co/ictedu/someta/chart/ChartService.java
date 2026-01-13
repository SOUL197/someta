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
	public int dailyMatchCount() {return chartDao.dailyMatchCount();}
	public List<Map<String, Object>> genderCount() {return chartDao.genderCount();}
	public List<Map<String, Object>> addrCount() {return chartDao.addrCount();}
	
	public int likeCount(String userid) {return chartDao.likeCount(userid);}
	public int matchCount(String userid) {return chartDao.matchCount(userid);}
	public int dateCount(String userid) {return chartDao.dateCount(userid);}
	public int responseCount(String userid) {return chartDao.responseCount(userid);}
	public int sstimeAverage(String userid) {return chartDao.sstimeAverage(userid);}
	
	public int dauCount() {return chartDao.dauCount();}
	public int wauCount() {return chartDao.wauCount();}
	public int mauCount() {return chartDao.mauCount();}
	public int yauCount() {return chartDao.yauCount();}
	public int visitorCount() {return chartDao.visitorCount();}
	public int churnCount() {return chartDao.churnCount();}
}
