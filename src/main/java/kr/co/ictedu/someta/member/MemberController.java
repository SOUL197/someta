package kr.co.ictedu.someta.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.someta.vo.MemberVO;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> memberjoin(MemberVO memberDTO) {
		memberService.create(memberDTO);
		return ResponseEntity.ok().build();
	}
	
	// Postman에서 결과 값 0일 시 - 중복 x / 1일 시 - 중복 o
	@GetMapping("/nicknameCheck")
	public int nicknameCheck(@RequestParam("nickname") String nickname) { // key값 : nickname
		return memberService.checkNickname(nickname);
	}
	
	// Postman에서 결과 값 0일 시 - 중복 x / 1일 시 - 중복 o
	@GetMapping("/idCheck")
	public int idCheck(@RequestParam("id") String id) { // key값 : id
		return memberService.checkId(id);
	}
	
	// 승민작업
	@PostMapping("/findId")
	public String findId(@RequestBody MemberVO memberDTO) {
		if(memberService.findId(memberDTO)==null) {
			return "checkEmail";
		}
		return memberService.findId(memberDTO);
	}
	@PostMapping("/findPwd")
	public ResponseEntity<?> findPassword(@RequestBody MemberVO memberDTO) {		
		memberService.findPassword(memberDTO);
		return ResponseEntity.ok().build();
	}
}
