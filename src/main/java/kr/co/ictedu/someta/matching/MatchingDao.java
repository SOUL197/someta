package kr.co.ictedu.someta.matching;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.someta.vo.MemberProfileImageVO;

@Mapper
public interface MatchingDao {
	
	void update(MemberProfileImageVO imageVO);
	
	List<Map<String, Object>> list(Map<String, Object> map);
	
	int totalCount(Map<String, Object> map);
	
	List<Map<String, Object>> detail(int num);
	
	String getProfileImage(@Param("nickname") String nickname);
}
