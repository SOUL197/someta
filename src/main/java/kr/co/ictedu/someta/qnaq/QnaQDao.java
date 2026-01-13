package kr.co.ictedu.someta.qnaq;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.Qna_aVO;
import kr.co.ictedu.someta.vo.Qna_qVO;

@Mapper
public interface QnaQDao {
	//사용자용, 마이페이지에서 사용
	void addq(Qna_qVO vo); //문의 추가
	void deleteq(int qnum);	//문의 삭제
	Qna_qVO detail(int qnum); //문의 상세
	
	//리스트
	int totalCount(Map<String, String>map);
	List<Qna_qVO> myqna(Map<String, String>map);
	

	
	
	
	
}
