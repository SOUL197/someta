package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("vvo")
@Getter
@Setter
public class VisitorLogVO {
	private int num;
    private int idn;          // 로그인한 회원이면 ID
    private String reip;       // IP
    private String uagent;     // User-Agent
    private String reqUri;     // 요청 URI
    private String reqQuery;   // 쿼리스트링
    private String referer;    // 이전 페이지
    private String method;     // GET/POST 등
    private int statusCode;// HTTP 상태
    private String sessionId;  // 세션 ID
    private String sstime;       // 요청 시각
}
