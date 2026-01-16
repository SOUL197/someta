package kr.co.ictedu.someta.chart;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.VisitorLogVO;

@Mapper
public interface VisitorLogDao {
	@Insert("INSERT INTO VISITORSLOG(\r\n"
			+ "    NUM, IDN, REIP, UAGENT, REQ_URI, REQ_QUERY, REFERER, METHOD, STATUS_CODE, SESSION_ID, SSTIME\r\n"
			+ "  ) VALUES (\r\n"
			+ "    VISITORSLOG_SEQ.NEXTVAL, #{idn}, #{reip}, #{uagent}, #{reqUri}, #{reqQuery}, #{referer}, #{method}, #{statusCode}, #{sessionId}, SYSDATE\r\n"
			+ "  )")
	public void insertVisitorLog(VisitorLogVO vo);
}
