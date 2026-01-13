package kr.co.ictedu.someta.gongji;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.GongjiVO;

@Mapper
public interface GongjiDao {
	void add(GongjiVO vo);
	void delete(int num);
	GongjiVO detail(int num);
	int totalCount(Map<String, String>map);
	List<GongjiVO> glist(Map<String, String>map);
}
