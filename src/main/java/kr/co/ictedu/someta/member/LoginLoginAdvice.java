package kr.co.ictedu.someta.member;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.someta.vo.LoginLogVO;
import kr.co.ictedu.someta.vo.MemberVO;

@Component
@Aspect
public class LoginLoginAdvice {
// login 정보 저장
// 세션 획득 시 (로그인 성공) -> 로그를 저장
		
// logout
// 세션 삭제 전에 -> 로그를 저장

	@Autowired
	private MyLogDao myLogDao;
	private void createLoggin(String methodName, Object[] fd,
			ProceedingJoinPoint jp,
			String status) {
		
		LoginLogVO log = new LoginLogVO();
		
		// 첫 번째 매개변수 등의 규칙을 정해놓고 사용하니까 이렇게 간단하게 배열의 인덱스로 구분이 가능하다.
		if (fd[0] instanceof HttpSession && fd[1] instanceof HttpServletRequest) {
			// 캐스팅
			HttpSession session = (HttpSession) fd[0];
			HttpServletRequest request = (HttpServletRequest) fd[1];
			// 세션으로부터 로그인 된 정보를 받아 온다. *****
			MemberVO vo = (MemberVO) session.getAttribute("loginMember");
			
			if (vo != null) { // 로그인 된 정보가 있다는 것
				log.setIdn(vo.getNum());
				log.setStatus(status); // 로그인 / 로그아웃
				log.setReip(request.getRemoteAddr());
				String userAgent = request.getHeader("User-Agent");
				String parsedAgent = UserAgentUtils.parseAgent(userAgent);
				log.setUagent(parsedAgent);
				
				System.out.println("로그인 기록 :"+log);
				System.out.println("IDN :"+log.getIdn());
				System.out.println("Agent :"+log.getUagent());
				System.out.println("reip :"+log.getReip());
				System.out.println("status :"+log.getStatus());
				System.out.println("-----------------------------------");
				
				myLogDao.addLoginLogging(log);
			}
		}
	}
	
	@Around("execution(* kr.co.ictedu.someta.member.LoginController.doLog*(..))")
	public String loginLogger(ProceedingJoinPoint jp) {
		// 해당 타겟의 메서드 doLog로 시작하는 메서드의 매개변수들을 배열로 받아온다.
		Object[] fd = jp.getArgs();
		String rpath = null;
		
		// doLog로 시작하는 메서드의 정확한 이름 반환
		String methodName = jp.getSignature().getName();
		try {
			
			if (methodName.equals("doLogin")) {
				rpath = (String) jp.proceed(); // doLogin() 메서드를 호출
				createLoggin(methodName, fd, jp, "login"); // 로그를 저장하기 위한 메서드를 호출
				
			} else if (methodName.equals("doLogout")) {
				createLoggin(methodName, fd, jp, "logout"); // 로그를 저장하기 위한 메서드를 호출
				rpath = (String) jp.proceed(); // doLogout() 메서드를 호출 - 세션이 사라짐
			}
			
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		System.out.println("return :"+rpath);
		return rpath;
	}
}
