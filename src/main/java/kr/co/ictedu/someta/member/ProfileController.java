package kr.co.ictedu.someta.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.someta.vo.ProfileVO;

@RestController
@RequestMapping("/mypage")
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	@PostMapping("/detail")
	public ResponseEntity<?> profilejoin(@RequestBody ProfileVO profileDTO) {
	
		profileService.updateProfile(profileDTO);
	    return ResponseEntity.ok().body("저장 성공");
	}
	
	@GetMapping("/detail/{memberid}")
	public ResponseEntity<ProfileVO> getProfile(
	    @PathVariable("memberid") int memberid
	) {
	    ProfileVO profile = profileService.getProfile(memberid);
	    return ResponseEntity.ok(profile);
	}


}