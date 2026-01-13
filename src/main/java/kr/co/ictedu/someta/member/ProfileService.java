package kr.co.ictedu.someta.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.someta.vo.ProfileVO;

@Service
public class ProfileService {

	@Autowired
	private ProfileDao profileDao;
	
	// 회원가입 직후
	public void createProfile(ProfileVO vo) {
		profileDao.insertProfile(vo);
	}
	
	// 회원정보 수정
	public void updateProfile(ProfileVO vo) {
		profileDao.updateProfile(vo);
	}
}
