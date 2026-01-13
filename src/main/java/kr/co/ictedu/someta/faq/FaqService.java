package kr.co.ictedu.someta.faq;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.someta.vo.FaqVO;

@Service
public class FaqService {
	@Autowired
	private FaqDao faqDao;
	public void add(FaqVO vo) {
		faqDao.add(vo);
	}
	public void hit (int num) {
		faqDao.hit(num);
	}
	public void delete(int num) {
		faqDao.delete(num);
	}
	public FaqVO detail(int num) {
		return faqDao.detail(num);
	}
	public int totalCount(Map<String, String>map) {
		return faqDao.totalCount(map);
	}
	public List<FaqVO> flist(Map<String, String>map){
		return faqDao.flist(map);
	}
}
