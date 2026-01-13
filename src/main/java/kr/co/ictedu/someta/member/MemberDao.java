package kr.co.ictedu.someta.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.someta.vo.MemberVO;

@Mapper
public interface MemberDao {
	void insertMember(MemberVO vo);
	
	@Select("SELECT COUNT(*) cnt FROM MEMBER WHERE nickname = #{nickname}")
	int checkNickname(String nickname);
	
	@Select("SELECT COUNT(*) cnt FROM MEMBER WHERE id = #{id}")
	int checkId(String id);
	
	// select count(*) from member where email = #{email} and id = #{id}
	int countByEmail(Map<String, String> map);
	
	// Id&Pwd 찾기 (승민작업)
	String findId(MemberVO vo);
	void findPassword(MemberVO vo);
	
}
