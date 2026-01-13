package kr.co.ictedu.someta.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.someta.vo.MemberVO;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	// {"id":"tess01","pwd":"11"} -> 이런 형태로 넘어오면, vo에 저장할 것
	@PostMapping("/dologin")
	public String doLogin(HttpSession session, HttpServletRequest request,
			@RequestHeader("User-Agent") String userAgent,
			@RequestBody MemberVO vo) {
		
		Map<String, Object> result = loginService.loginCheck(vo);
		System.out.println("result" + result);
		
		if (result != null && result.get("CNT") != null) {
			int cnt = ((Number) result.get("CNT")).intValue();
			
			if (cnt == 1) {
				System.out.println("세션 처리 완료!");
				vo.setNickname(result.get("NICKNAME").toString());
				
				// 로그인 처리를 완료하기 위해서 세션 Scope에 키(key)와 값(value)으로 저장
				// vo에는 nickname,
				session.setAttribute("loginMember", vo);
				return "success";
			}
		}
		return "fail";
	}
	
	@GetMapping("/dologout")
	public String doLogout(HttpSession session, HttpServletRequest request,
			@RequestHeader("User-Agent") String userAgent) {
		System.out.println("로그아웃 처리 완료!");
		session.invalidate();
		return "logout";
	}
	
	@GetMapping("/session")
	public MemberVO session(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		
		// 로그인의 상태를 확인할 때 setPassword는 json으로 노출 안 되게 null
		if (loginMember != null) {
			loginMember.setPwd(null);
		}
		
		return loginMember; // username, id
	}
}
