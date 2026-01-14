package kr.co.ictedu.someta.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.someta.pwl.UserInfo;
import kr.co.ictedu.someta.vo.MemberVO;

@Mapper
public interface LoginDao {
	
	@Select("SELECT num, NICKNAME, COUNT(*) cnt FROM MEMBER WHERE \r\n"
			+ "id=#{id} AND pwd=#{pwd} GROUP BY num, NICKNAME")
	Map<String, Object> loginCheck(MemberVO vo);
	
	// -------------------------Passwordless-----------------------------
	// Login Check
    MemberVO checkPassword(MemberVO vo);
    
    // Search for User Information
    MemberVO getUserInfo(MemberVO vo);

    // Password Change
    void changepw(MemberVO vo);
}