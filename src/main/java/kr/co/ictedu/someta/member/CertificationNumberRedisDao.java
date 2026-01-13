package kr.co.ictedu.someta.member;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository // 데이터 처리(베이스) 즉 DAO에 부여하는 bean 어노테이션 이다.
public class CertificationNumberRedisDao {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	// 인증번호를 저장 => String authCode 랜덤한 문자열로 저장 1A2B3C
	public void saveCertifiRedisNumber(String email, String authCode) {

		// 10초 제한, 참고로 Duration.ofMinutes(3) : 3분 제한
		stringRedisTemplate.opsForValue().set(email, authCode, Duration.ofSeconds(10000));

		// -------------------- 추가 코드
		stringRedisTemplate.opsForValue().set(email + ":attempt", "0", Duration.ofSeconds(10000));
	}

	public String getCertifiRedisNumber(String email) {
		return stringRedisTemplate.opsForValue().get(email);
	}

	public void deleteCertifiRedisNumber(String email) {
		stringRedisTemplate.delete(email);

		// -------------------- 추가 코드
		stringRedisTemplate.delete(email + ":attempt");
	}

	public boolean hasKey(String email) {
		return stringRedisTemplate.hasKey(email);
	}
	//-------------------- 추가 코드
	// myEMAIL@naver.com 이메일에서 받는 Redis key는
    // myEMAIL@naver.com:attempt 가 되고 이 키의 값을 숫자다 인식 시켜서 + 1을 증가 시키겠다.
    // increment()는 해당 key가 없으면 0에서 시작해서 1로 설정하겠다. 그래서 계정을 입력하고
    // 인증 번호를 재시도 했을 때 틀릴 시 횟수 초과 되면 경고 메세지를 날리겠다는 설정 
	public void increaseAttempt(String email) {
		stringRedisTemplate.opsForValue().increment(email + ":attempt");
	}
	
	public int getAttempt(String email) {
		String attemptStr = stringRedisTemplate.opsForValue().get(email + ":attempt");
		return attemptStr == null ? 0 : Integer.parseInt(attemptStr);
	}
}
