package kr.co.ictedu.someta.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("surveyvo")
public class SurveyVO {
	private Long num;
	private String sub;
	private Integer code;
	private String sdate;
	private List<SurveyContentVO> contents;
}
