package kr.co.ictedu.someta.chart;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.VisitorLogVO;

@Mapper
public interface VisitorLogDao {
	@Insert("INSERT INTO VISITORSLOG(\r\n"
            + "    NUM, IDN, REIP, UAGENT, REQ_URI, REQ_QUERY, REFERER, METHOD, STATUS_CODE, SESSION_ID, SSTIME\r\n"
            + "  ) VALUES (\r\n"
            + "    VISITORSLOG_SEQ.NEXTVAL, #{idn}, #{reip}, #{uagent}, #{reqUri}, #{reqQuery, jdbcType=VARCHAR}, #{referer, jdbcType=VARCHAR}, #{method, jdbcType=VARCHAR}, #{statusCode}, #{sessionId, jdbcType=VARCHAR}, SYSDATE\r\n"
            + "  )")
    public void insertVisitorLog(VisitorLogVO vo);
}
