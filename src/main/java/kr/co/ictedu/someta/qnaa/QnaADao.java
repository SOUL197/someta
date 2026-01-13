package kr.co.ictedu.someta.qnaa;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.Qna_aVO;


@Mapper
public interface QnaADao {

	//관리자용
	void addanswer(Qna_aVO ans);
	void delanswer(int anum);
		
	int totalCount(Map<String, String>map);
	List<Qna_aVO> myqnaa(Map<String, String>map);
	
}
