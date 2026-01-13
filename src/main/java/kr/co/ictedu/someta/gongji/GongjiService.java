package kr.co.ictedu.someta.gongji;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.someta.vo.BoardVO;
import kr.co.ictedu.someta.vo.GongjiVO;

@Service
public class GongjiService {
	
	@Autowired
	private GongjiDao gongjiDao;
	
	public void add(GongjiVO vo) {
		gongjiDao.add(vo);
	}
	
	public void delete(int num) {
		gongjiDao.delete(num);
	}
	public GongjiVO detail(int num) {
		return gongjiDao.detail(num);
	}
	public int totalCount(Map<String, String>map) {
		return gongjiDao.totalCount(map);
	}
	public List<GongjiVO> glist(Map<String, String>map){
		return gongjiDao.glist(map);
	}
}
