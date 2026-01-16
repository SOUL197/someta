package kr.co.ictedu.someta.chart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.someta.member.UserAgentUtils;
import kr.co.ictedu.someta.vo.MemberVO;
import kr.co.ictedu.someta.vo.VisitorLogVO;

@Component
public class VisitorLogInterceptor implements HandlerInterceptor {
	@Autowired
    private VisitorLogDao visitorLogDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        VisitorLogVO vo = new VisitorLogVO();
        vo.setReip(request.getRemoteAddr());
        vo.setUagent(UserAgentUtils.parseAgent(request.getHeader("User-Agent")));
        vo.setReqUri(request.getRequestURI());
        vo.setReqQuery(request.getQueryString());
        vo.setReferer(request.getHeader("Referer"));
        vo.setMethod(request.getMethod());
        vo.setSessionId(request.getSession().getId());

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object member = session.getAttribute("loginMember");
            if (member != null) {
                vo.setIdn(((MemberVO) member).getNum());
            }
        }

        request.setAttribute("visitorLogVO", vo);
        return true;
    }

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		VisitorLogVO vo = (VisitorLogVO) request.getAttribute("visitorLogVO");
        if (vo == null) return;

        // HTTP 상태 코드
        vo.setStatusCode(response.getStatus());

        visitorLogDao.insertVisitorLog(vo);
	}
    
}