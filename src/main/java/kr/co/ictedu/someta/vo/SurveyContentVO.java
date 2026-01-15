package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("surveyContentvo")
public class SurveyContentVO {
	private String surveytype;

	private String surveytitle;

	private Integer surveycnt;
}
