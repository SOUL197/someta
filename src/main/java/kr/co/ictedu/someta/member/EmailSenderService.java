package kr.co.ictedu.someta.member;

import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailSenderService {

	// 스프링에서 메일을 전송하기 위한 객체 DI
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private CertificationNumberRedisDao certificationNumberRedisDao;
	
	private String authCode;
	
	// 이메일 중복 체크 - 데이터베이스에 가입된 이메일이 있는지 검색
	public int duplicateEmail(Map<String, String> map) {
		int checkEmail = memberDao.countByEmail(map);
		return checkEmail > 0 ? 1 : 0;
	}
	
	// A1B2C3 6자리의 난수를 사용해서 인증 코드를 만드는 메서드
	public void createAuthCode() {
		int length = 6;
		StringBuilder authCode = new StringBuilder();
		Random random = new Random();
		for (int i=0; i<length; i++) {
			int type = random.nextInt(3);
			
			switch (type) {
			
			case 0:
				authCode.append(random.nextInt(10));
				break;
			case 1:
				authCode.append((char)(random.nextInt(26) + 65)); // A
				break;
			case 2:
				authCode.append((char)(random.nextInt(26) + 97)); // a
				break;
			}
		}
		
		// 멤버 필드에 랜덤화 된 인증코드 6자리를 저장해 둔다.
		this.authCode = authCode.toString();
	}
	
	// 회원 가입 : 이메일 인증번호 발송
		public void sendEmail(String toEmail) {
			createAuthCode(); // 난수로 발급된 6자리 인증코드가 멤버필드에 authCode 저장된다.
			
			// MINE TYPE
			MimeMessage message = mailSender.createMimeMessage();
			
			try {
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setFrom("ssm970@naver.com"); // SMTP 설정 메일
				helper.setTo(toEmail); // 회원가입 시 받아온 이메일
				helper.setSubject("Someta 회원가입 인증 번호 발송");
				
				StringBuilder body = new StringBuilder();
				body.append("<html><body>");
				body.append("<h1>Someta 회원가입을 위한 인증 번호</h1>");
				body.append("<p>회원가입을 완료하기 위해 아래의 인증코드를 입력해 주세요.</p>");
				body.append("<p>인증 코드 : <strong>");
				body.append(authCode);
				body.append("</strong></p>");
				body.append("</body></html>");
				
				helper.setText(body.toString(), true);
				mailSender.send(message); // 메세지 전송
				System.out.println("인증 코드 (테스트용) : "+authCode);
				
				// Redis 메모리에 저장해 놓는다.
				certificationNumberRedisDao.saveCertifiRedisNumber(toEmail, authCode);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public boolean isVerify(String email, String authCode) {
			final int MAX_ATTEMPT = 3;
			
			// Redis에 인증 번호가 없으면 false
			if (!certificationNumberRedisDao.hasKey(email)) {
				return false;
			}
						
			// 현재 시도 횟수 확인
			int attemptCount = certificationNumberRedisDao.getAttempt(email);
						
			if (attemptCount >= MAX_ATTEMPT) {
				System.out.println("인증 실패 : 최대 인증 시도 초과");
				return false;
			}
						
			System.out.println("Redis의 이메일 :"+certificationNumberRedisDao.hasKey(email));
			System.out.println("Redis의 인증 코드 :"+authCode);
						
			// 인증번호 일치 여부 확인
			String savedCode = certificationNumberRedisDao.getCertifiRedisNumber(email);
						
			// 인증을 성공했다는 것은 본인의 이메일을 확인 하고 인증 번호를 작성해서 넘어 온 데이터
			if (savedCode != null && savedCode.equals(savedCode)) {
				certificationNumberRedisDao.deleteCertifiRedisNumber(email); // 성공하면 인증 번호 및 시도 횟수 삭제
				return true;
							
			} else {
				certificationNumberRedisDao.increaseAttempt(email); // 실패 시 시도 횟수 증가
				System.out.println("인증 실패 (" + (attemptCount + 1) + "회)");
				return false;
			}
		}
	}
