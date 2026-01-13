package kr.co.ictedu.someta.member;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.LoginLogVO;

@Mapper
public interface MyLogDao {

	@Insert("INSERT INTO LOGINLOG VALUES(LOGINLOG_SEQ.NEXTVAL,\r\n"
			+ " #{idn}, #{reip}, #{uagent}, #{status}, SYSDATE)")
	public void addLoginLogging(LoginLogVO vo);
}
