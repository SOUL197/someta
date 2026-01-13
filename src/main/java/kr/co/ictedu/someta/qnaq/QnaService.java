package kr.co.ictedu.someta.qnaq;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.someta.vo.Qna_qVO;

@Service
public class QnaService {
	@Autowired
	private QnaQDao qnaQDao;
	
	public void addq(Qna_qVO vo) {
		qnaQDao.addq(vo);
	}
	public void deleteq(int qnum) {
		qnaQDao.deleteq(qnum);
	}
	public Qna_qVO detail(int qnum) {
		return qnaQDao.detail(qnum);
	}
	public int totalCount(Map<String, String>map) {
		return qnaQDao.totalCount(map);
	}
	public List<Qna_qVO> myqna(Map<String, String>map){
		return qnaQDao.myqna(map); 
	}
	
	

	

}
