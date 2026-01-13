package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("faq")
public class FaqVO {
	private int num;
	private String title;
	private String writer;
	private int member_num;
	private String content;
	private String fdate;

}
/*CREATE TABLE faq(
num NUMBER PRIMARY KEY,
title VARCHAR2(30) NOT NULL,
writer VARCHAR2(20) NOT null,
member_num NUMBER NOT NULL,
content clob,
fdate DATE DEFAULT SYSDATE);
DROP TABLE FAQ;*/
