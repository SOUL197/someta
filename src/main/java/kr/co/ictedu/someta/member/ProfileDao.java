package kr.co.ictedu.someta.member;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.ProfileVO;

@Mapper
public interface ProfileDao {
	void insertProfile(ProfileVO vo);
	void updateProfile(ProfileVO vo);
}
