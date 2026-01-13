package kr.co.ictedu.someta.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.someta.vo.MemberVO;

@Service
public class MemberService {

	
	@Autowired
	private MemberDao memberDao;
	
	public void create(MemberVO vo) {
		memberDao.insertMember(vo);
	}
	
	public int checkNickname(String nickname) {
		return memberDao.checkNickname(nickname);
	}
	
	public int checkId(String id) {
		return memberDao.checkId(id);
	}
	
	public int checkEmailDuplicate(Map<String, String> map) {
		return memberDao.countByEmail(map);
	}
	
	// 승민작업
	public String findId(MemberVO vo) {
		return memberDao.findId(vo);
	}
	public void findPassword(MemberVO vo) {
		memberDao.findPassword(vo);
	}
}
