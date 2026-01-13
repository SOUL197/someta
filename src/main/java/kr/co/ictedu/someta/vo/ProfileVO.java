package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("pro")
@Setter
@Getter
public class ProfileVO {
	private int memberid;
	private String gender;
	private String birth;
	private String phone;
	private String country;
	private String address;
	private String height;
	private String weight;
	private String hobby;
	private String mbti;
	private String religion;
	private String drinking;
	private String smoking;

/*
CREATE TABLE MEMBER_PROFILE (
 	memberid NUMBER CONSTRAINT member_profile_memberid_pk PRIMARY KEY, -- 회원번호
 	gender VARCHAR2(10), -- 성별
 	birth DATE, -- 생년월일
 	phone VARCHAR2(40), -- 전화번호
 	country VARCHAR2(30) -- 국가
 	address VARCHAR2(100), -- 주소
 	height NUMBER(10), -- 키
 	weight NUMBER(10), -- 체중
 	hobby VARCHAR2(100), -- 취미
 	mbti VARCHAR2(10),-- mbti
 	religion VARCHAR2(20), -- 종교
 	drinking VARCHAR2(50), -- 음주
 	smoking VARCHAR2(50), -- 흡연

 		CONSTRAINT fk_member_profile_member FOREIGN KEY (memberid)
 		REFERENCES MEMBER(num) ON DELETE CASCADE,
 		CONSTRAINT member_gender_ck CHECK(gender='남자' OR gender='여자')
 );

CREATE SEQUENCE MEMBER_PROFILE_SEQ INCREMENT BY 1 START WITH 1; 
*/
}
