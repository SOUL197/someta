package kr.co.ictedu.someta.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.someta.vo.LoginLogVO;

@Mapper
public interface MyLogDao {

	@Insert("INSERT INTO LOGINLOG VALUES(LOGINLOG_SEQ.NEXTVAL,\r\n"
			+ " #{idn}, #{reip}, #{uagent}, #{status}, SYSDATE)")
	public void addLoginLogging(LoginLogVO vo);

	@Select("SELECT * FROM(\r\n"
			+ "SELECT idn,reip,uagent,status,sstime,ROW_NUMBER() OVER(ORDER BY lognum ASC) row_num\r\n"
			+ "FROM LOGINLOG WHERE idn = #{num}) WHERE row_num BETWEEN #{begin} AND #{end}")
	public List<Map<String, Object>> getLoginLogging(Map<String, String> map);

	@Select("SELECT COUNT(*) FROM LOGINLOG WHERE idn = #{num}")
	public int totalCount(int num);
}
