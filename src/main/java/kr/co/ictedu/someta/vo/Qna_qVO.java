package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("qnaq")
public class Qna_qVO {
	private int qnum;
	private String qtitle;
	private String qwriter;
	private int member_num;
	private String qcontent;
	private String qdate;
	}

/*CREATE TABLE qna_q(
qnum NUMBER PRIMARY KEY,
qtitle VARCHAR2(30) NOT NULL,
qwriter VARCHAR2(20) NOT null,
member_num NUMBER NOT NULL,
qcontent clob,
qdate DATE DEFAULT sysdate);
*/
