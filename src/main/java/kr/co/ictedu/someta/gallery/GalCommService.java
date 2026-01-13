package kr.co.ictedu.someta.gallery;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import kr.co.ictedu.someta.vo.Gal_CommVO;

@Service
public class GalCommService {
	@Autowired
	private GalCommDAO galcommDao;
	
	public void add (Gal_CommVO vo) {
		galcommDao.addcomm(vo);
	}
	public List<Gal_CommVO> listComm(Map<String, String> map) {
		return galcommDao.listcomm(map);
	}
	public int totalCount(Map<String, String> map) {
		return galcommDao.totalCount(map);
	};
}
