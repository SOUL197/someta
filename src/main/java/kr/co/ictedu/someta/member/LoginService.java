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
	
	// -------------------------Passwordless-----------------------------
	// Login Check
	public MemberVO checkPassword(MemberVO vo) {
		return loginDao.checkPassword(vo);
	}
    
    // Search for User Information
	public MemberVO getUserInfo(MemberVO vo) {
		return loginDao.getUserInfo(vo);
	}

    // Password Change
	public void changepw(MemberVO vo) {
		loginDao.changepw(vo);
	}
}
