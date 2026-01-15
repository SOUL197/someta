package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("surveyresultvo")
public class SurveyResultVO {
	
	private Long surveyNum;
	
	private String surveySub;
	
	private Integer surveyCode;
	
	private String surveyDate;
	
	private Long subcode;
	
	private String surveytype;
	
	private String surveytitle;
	
	private Integer surveycnt;
}
