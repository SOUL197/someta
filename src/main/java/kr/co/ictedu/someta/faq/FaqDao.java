package kr.co.ictedu.someta.faq;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.FaqVO;

@Mapper
public interface FaqDao {
	void add(FaqVO vo);
	void hit (int num);
	void delete(int num);
	FaqVO detail(int num);
	int totalCount(Map<String, String>map);
	List<FaqVO> flist(Map<String, String>map);
}
