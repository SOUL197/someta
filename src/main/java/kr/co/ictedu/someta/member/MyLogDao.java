package kr.co.ictedu.someta.member;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.someta.vo.LoginLogVO;

@Mapper
public interface MyLogDao {

	@Insert("INSERT INTO LOGINLOG VALUES(LOGINLOG_SEQ.NEXTVAL,\r\n"
			+ " #{idn}, #{reip}, #{uagent}, #{status}, SYSDATE)")
	public void addLoginLogging(LoginLogVO vo);
	
	@Select("SELECT * FROM LOGINLOG WHERE idn = #{num}")
    public List<LoginLogVO> getLoginLogging(int num);
}
