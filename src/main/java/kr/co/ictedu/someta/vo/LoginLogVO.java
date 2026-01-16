package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Alias("log")
@Setter
@Getter
public class LoginLogVO {
	private int lognum;
	private int idn;
	private String reip;
	private String uagent;
	private String status;
	private String sstime, eetime; // 로그인 / 로그아웃 시간
	
/*
CREATE TABLE LOGINLOG (
	lognum NUMBER CONSTRAINT loginlog_lognum_pk PRIMARY KEY,
	idn VARCHAR2(50), -- 접속자 id
	reip VARCHAR2(30), -- ip 주소
	uagent VARCHAR2(100), -- os 및 브라우저
	status VARCHAR2(10), -- 상태값 (로그인 / 로그아웃)
	sstime DATE DEFAULT SYSDATE, -- 로그인 시점
	CONSTRAINT loginlogs_idn_fk FOREIGN KEY(lognum)
	REFERENCES MEMBER(num)
);

CREATE SEQUENCE LOGINLOG_SEQ INCREMENT BY 1 START WITH 1; 
*/
}
