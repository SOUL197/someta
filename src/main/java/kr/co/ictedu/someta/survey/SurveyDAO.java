package kr.co.ictedu.someta.survey;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.someta.vo.SurveyContentVO;
import kr.co.ictedu.someta.vo.SurveyResultVO;
import kr.co.ictedu.someta.vo.SurveyVO;

@Mapper
public interface SurveyDAO {
	//select count(*) from survey
	Long maxSurveyNum();
	//select * from survey where num=#{num}
	List<SurveyResultVO> findBySNUM(Long num);
	
	void saveSurvey(SurveyVO vo);

	void saveSurveyContentList(List<SurveyContentVO> list);

	void incrementSurveyCount(@Param("subcode") int subcode, @Param("surveytype") String surveytype);
}
