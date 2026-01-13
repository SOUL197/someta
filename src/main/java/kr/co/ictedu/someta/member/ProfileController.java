package kr.co.ictedu.someta.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.someta.vo.ProfileVO;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/change")
	public ResponseEntity<?> profilejoin(ProfileVO profileDTO) {
		profileService.updateProfile(profileDTO);
		
		return ResponseEntity.ok().build();
	}
}
