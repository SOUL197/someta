package kr.co.ictedu.someta.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import kr.co.ictedu.someta.vo.MemberVO;

@Mapper
public interface LoginDao {
	
	@Select("SELECT NICKNAME, COUNT(*) cnt FROM MEMBER WHERE \r\n"
			+ "id=#{id} AND pwd=#{pwd} GROUP BY NICKNAME")
	Map<String, Object> loginCheck(MemberVO vo);
}