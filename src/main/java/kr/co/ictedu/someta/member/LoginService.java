package kr.co.ictedu.someta.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.someta.vo.MemberVO;

@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;
	
	public Map<String, Object> loginCheck(MemberVO vo) {
		return loginDao.loginCheck(vo);
	}
}
