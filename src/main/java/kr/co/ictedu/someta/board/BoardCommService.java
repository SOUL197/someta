package kr.co.ictedu.someta.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.someta.vo.BoardVO;
import kr.co.ictedu.someta.vo.Board_CommVO;

@Service
public class BoardCommService {
	@Autowired
	private Board_CommDAO boardCommDao;
	
	public void add (Board_CommVO vo) {
		boardCommDao.addComm(vo);
	}
	public List<Board_CommVO> listComm(Map<String, String> map) {
		return boardCommDao.listComm(map);
	}
	public int totalCount(Map<String, String> map) {
		return boardCommDao.totalCount(map);
	};
	public void del (int num) {
		boardCommDao.delcomm(num);
	}
}
