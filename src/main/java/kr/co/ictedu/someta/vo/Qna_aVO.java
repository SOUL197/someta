package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("qnaa")
public class Qna_aVO {
	private int anum;
	private int qnum;
	private String awriter;
	private int member_num;
	private String acontent;
	private String adate;
}
/*CREATE TABLE qna_a(
anum NUMBER PRIMARY key,
qnum NUMBER NOT NULL,
awriter VARCHAR2(20),
member_num NUMBER NOT NULL,
acontent clob,
adate DATE DEFAULT sysdate,
CONSTRAINT fk_qna_a_code
	 FOREIGN KEY (anum)
	 REFERENCES qna_q(qnum)
	 ON DELETE CASCADE
	 );*/