package kr.co.ictedu.someta.gallery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.someta.vo.GalimgVO;
import kr.co.ictedu.someta.vo.GalleryVO;



@Service
public class GalleryService {
	
	@Autowired
	private GalleryDAO galleryDao;

	@Transactional  //하나의 묶음으로 error 발생 시 일부 실행도 되지 않는다. 
	public void transcationProcess(GalleryVO gvo, List<GalimgVO> imgvo) {
		galleryDao.add(gvo);
		galleryDao.addimg(imgvo);
	}
	
	public List<Map<String, Object>> list (Map<String, String> map){
		return galleryDao.list(map);
	}
	public int totalCount(Map<String, String> map) {
		return galleryDao.totalCount(map);
	}
	public void delete(int num) {
		galleryDao.delete(num);
	}
	void elike (int num) {
		galleryDao.elike(num);
	}
	
	public Map<String, Object> detail(int num){
		galleryDao.hit(num);
		List<Map<String, Object>> rows=galleryDao.detail(num);
		if(rows.isEmpty()) return null;
		Map<String, Object> result = new HashMap<>();
		result.put("num", rows.get(0).get("NUM"));
		result.put("title", rows.get(0).get("TITLE"));
		result.put("writer", rows.get(0).get("WRITER"));
		result.put("contents", rows.get(0).get("CONTENTS"));
		result.put("hit", rows.get(0).get("HIT"));
		result.put("elike", rows.get(0).get("ELIKE"));
		result.put("reip", rows.get(0).get("REIP"));
		result.put("gdate", rows.get(0).get("GDATE"));
		result.put("member_num", rows.get(0).get("MEMBER_NUM"));
		//이미지 리스트 따로 모으기
		List<String> images=new ArrayList<>();
		for(Map<String, Object> row:rows) {
			images.add((String) row.get("IMAGENAME"));
		}
		result.put("getImgvo", images);
		return result;
		}
		
	}

