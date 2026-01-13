package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("mem") 
@Setter
@Getter
public class MemberVO {
	private int num;
	private String username;
	private String nickname;
	private String id;
	private String pwd;
	private String email;
	private String regdate;

/*
CREATE TABLE MEMBER (
 	num NUMBER CONSTRAINT member_num_pk PRIMARY KEY, -- 회원번호
 	username VARCHAR2(50) NOT NULL, -- 이름
 	nickname VARCHAR2(50) NOT NULL UNIQUE, -- 닉네임
 	id VARCHAR2(50) NOT NULL UNIQUE, -- 아이디
 	pwd VARCHAR2(50) NOT NULL, -- 비밀번호
 	email VARCHAR2(100) NOT NULL UNIQUE, -- 이메일
 	regdate DATE DEFAULT SYSDATE NOT NULL -- 생성일자
 );
 
CREATE SEQUENCE MEMBER_SEQ INCREMENT BY 1 START WITH 1; -- 회원가입 시퀀스 생성
*/
}
