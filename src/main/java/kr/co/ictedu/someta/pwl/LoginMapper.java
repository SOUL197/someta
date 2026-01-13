package kr.co.ictedu.someta.pwl;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {
	// Login Check
    UserInfo checkPassword(UserInfo userinfo);
    
    // Search for User Information
    UserInfo getUserInfo(UserInfo userinfo);
    
    // Password Update
    void updatePassword(UserInfo userinfo);
    
    // User Registration
    void createUserInfo(UserInfo userinfo);
    
    // User Deletion
    void withdrawUserInfo(UserInfo userinfo);
    
    // Password Change
    void changepw(UserInfo userinfo);
}
