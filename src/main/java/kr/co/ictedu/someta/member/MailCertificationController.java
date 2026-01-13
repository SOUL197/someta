package kr.co.ictedu.someta.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.someta.vo.EmailCheckVO;
import kr.co.ictedu.someta.vo.EmailCountCheckVO;

@RestController
@RequestMapping("/api/auth")
public class MailCertificationController {

	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private CertificationNumberRedisDao certificationNumberRedisDao;
	
	@PostMapping("/emailCheck")
	public int sendEmail(@RequestBody Map<String, String> map) {
		System.out.println("요청 처리 됨" + map.get("email"));
		int checkEmail = emailSenderService.duplicateEmail(map);

		if (checkEmail == 0 && map.get("type").equals("sign")) {
			// 중복된 이메일이 없을 때 메일을 전송한다.
			emailSenderService.sendEmail(map.get("email"));
			return 0;
		} else if (checkEmail == 1 && map.get("type").equals("find")){
			emailSenderService.sendEmail(map.get("email"));
			return 2;
		} else if(checkEmail == 0 && map.get("type").equals("find")) {
			return 0;
		} else {
			return 1;
		}
	}
	
	@PostMapping("/emailCheck/certification")
	public ResponseEntity<EmailCountCheckVO> verifyCertificationNumber(@RequestBody EmailCheckVO dto) {
		boolean hasKey = certificationNumberRedisDao.hasKey(dto.getEmail());
		int attempts = certificationNumberRedisDao.getAttempt(dto.getEmail());
		
		if (!hasKey) { // stringRedisTemplate.opsForValue().set(email, authCode,Duration.ofSeconds(10000));
			return ResponseEntity.ok(new EmailCountCheckVO(false, "expired")); // 설정한 시간이 초과 되었다.
			
		} else if (attempts >= 3){
			return ResponseEntity.ok(new EmailCountCheckVO(false, "exceeded")); // exceeded 인증코드 시도를 3번 초과 했다.
			
	    } else if (certificationNumberRedisDao.getCertifiRedisNumber(dto.getEmail()).equals(dto.getCode())) {
	        certificationNumberRedisDao.deleteCertifiRedisNumber(dto.getEmail());
	        return ResponseEntity.ok(new EmailCountCheckVO(true, "ok")); // 정상이다.
	        
	    } else {
	        certificationNumberRedisDao.increaseAttempt(dto.getEmail());
	        return ResponseEntity.ok(new EmailCountCheckVO(false, "wrong"));
		}
	}
}
