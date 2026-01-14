package kr.co.ictedu.someta.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.someta.vo.MemberVO;

@Mapper
public interface LoginDao {
	
	@Select("SELECT num, NICKNAME, COUNT(*) cnt FROM MEMBER WHERE \r\n"
			+ "id=#{id} AND pwd=#{pwd} GROUP BY num, NICKNAME")
	Map<String, Object> loginCheck(MemberVO vo);
}