package kr.co.ictedu.someta.gallery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.BoardVO;
import kr.co.ictedu.someta.vo.Board_CommVO;
import kr.co.ictedu.someta.vo.Gal_CommVO;

@Mapper
public interface GalCommDAO {
	
void addcomm (Gal_CommVO comm);
	
List<Gal_CommVO> listcomm(Map<String, String> map);

int totalCount(Map<String, String> map);
}
