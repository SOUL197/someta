package kr.co.ictedu.someta.gallery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ictedu.someta.vo.GalimgVO;
import kr.co.ictedu.someta.vo.GalleryVO;

@Mapper
public interface GalleryDAO {
	void add (GalleryVO vo);
	void addimg (List<GalimgVO> imgvo);
	List<Map<String, Object>> list(Map<String, String> map);
	void hit (int num);
	void elike (int num);
	List<Map<String, Object>> detail(int num);
	void delete (int num);
	int totalCount(Map<String, String> map);
}
