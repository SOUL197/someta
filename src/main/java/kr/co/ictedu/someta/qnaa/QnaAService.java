package kr.co.ictedu.someta.qnaa;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.someta.vo.Qna_aVO;

@Service
public class QnaAService {
	
	@Autowired
	private QnaADao qnaADao;
	public void addanswer(Qna_aVO ans) {
		qnaADao.addanswer(ans);
	}
	public void delanswer(int anum) {
		qnaADao.delanswer(anum);
	}
	
	public int totalCount(Map<String, String>map) {
		return qnaADao.totalCount(map);
	}
	
	public List<Qna_aVO> myqnaa(Map<String, String>map){
		return qnaADao.myqnaa(map);
	}
}
