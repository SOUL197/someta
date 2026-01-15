package kr.co.ictedu.someta.survey;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.someta.vo.SurveyContentVO;
import kr.co.ictedu.someta.vo.SurveyResultVO;
import kr.co.ictedu.someta.vo.SurveyVO;


@Service
public class SurveyService {

	@Autowired
	private SurveyDAO surveyDAO;

	public Long maxSurveyNum() {
		return surveyDAO.maxSurveyNum();
	}

	@Transactional
	public void saveSurvey(SurveyVO vo) {
		surveyDAO.saveSurvey(vo);
		char stype = 'A';
		List<SurveyContentVO> contentList = new ArrayList<>();
		for (SurveyContentVO c : vo.getContents()) {
			SurveyContentVO contentVO = new SurveyContentVO();
			contentVO.setSurveytitle(c.getSurveytitle());
			contentVO.setSurveytype(String.valueOf(stype));
			contentVO.setSurveycnt(0);
			contentList.add(contentVO);
			stype++;
		}
		surveyDAO.saveSurveyContentList(contentList);
	}

	public List<SurveyVO> getSurveyList() {
		List<SurveyVO> surveyList = new ArrayList<>();

		for (int i = 0; i < surveyDAO.maxSurveyNum(); i++) {
			List<SurveyResultVO> result = surveyDAO.findBySNUM((long) i + 1);

			if (result.isEmpty()) {
				continue;
			} else {
				SurveyVO surveyVO = new SurveyVO();
				List<SurveyContentVO> contentVOList = new ArrayList<>();
				SurveyResultVO resultVO = result.get(0);
				surveyVO.setNum(resultVO.getSurveyNum());
				surveyVO.setSub(resultVO.getSurveySub());
				surveyVO.setCode(resultVO.getSurveyCode());
				surveyVO.setSdate(resultVO.getSurveyDate());
				for (SurveyResultVO vo : result) {
					SurveyContentVO contentVO = new SurveyContentVO();
					contentVO.setSurveytype(vo.getSurveytype());
					contentVO.setSurveytitle(vo.getSurveytitle());
					contentVO.setSurveycnt(vo.getSurveycnt());
					contentVOList.add(contentVO);
				}
				surveyVO.setContents(contentVOList);
				surveyList.add(surveyVO);
			}
		}
		return surveyList;
	}

	public SurveyVO findBySNUM(Long num) {
		List<SurveyResultVO> result = surveyDAO.findBySNUM(num);

		if (result == null) {
			return null;
		}

		SurveyVO surveyVO = new SurveyVO();
		List<SurveyContentVO> contentVOList = new ArrayList<>();

		SurveyResultVO resultVO = result.get(0);
		surveyVO.setNum(resultVO.getSurveyNum());
		surveyVO.setSub(resultVO.getSurveySub());
		surveyVO.setCode(resultVO.getSurveyCode());
		surveyVO.setSdate(resultVO.getSurveyDate());
		for (SurveyResultVO vo : result) {
			SurveyContentVO contentVO = new SurveyContentVO();
			contentVO.setSurveytype(vo.getSurveytype());
			contentVO.setSurveytitle(vo.getSurveytitle());
			contentVO.setSurveycnt(vo.getSurveycnt());
			contentVOList.add(contentVO);
		}
		surveyVO.setContents(contentVOList);
		return surveyVO;
	}

	public void incrementSurveyCount(int subcode, String surveytype) {
		surveyDAO.incrementSurveyCount(subcode, surveytype);
	}
}
