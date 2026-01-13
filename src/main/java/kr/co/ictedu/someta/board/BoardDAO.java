package kr.co.ictedu.someta.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.BoardVO;
import kr.co.ictedu.someta.vo.Board_CommVO;

@Mapper
public interface BoardDAO {
	void add (BoardVO vo);
	List<BoardVO> blist(Map<String, String> map);
	
	void hit (int num);
	void elike (int num);
	BoardVO detail(int num);
	void delete (int num);
	int totalCount(Map<String, String> map);
}
