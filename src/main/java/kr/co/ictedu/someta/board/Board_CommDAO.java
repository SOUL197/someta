package kr.co.ictedu.someta.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.Board_CommVO;




@Mapper
public interface Board_CommDAO {
	
void addComm(Board_CommVO comm);

void delcomm (int num);

List<Board_CommVO> listComm(Map<String, String> map);

int totalCount(Map<String, String> map);

}
