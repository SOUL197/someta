package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("gongji")
public class GongjiVO {
	private int num;
	private String title;
	private String writer;
	private int member_num;
	private String content;
	private String gdate;
	
}
/*CREATE TABLE gongji(
num NUMBER PRIMARY key,
title VARCHAR2(100) NOT null,
writer VARCHAR2(30) NOT null,
member_num NUMBER NOT NULL,
content clob,
gdate DATE DEFAULT SYSDATE);*/