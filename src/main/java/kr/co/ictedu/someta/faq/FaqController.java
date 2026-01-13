package kr.co.ictedu.someta.faq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.ictedu.someta.vo.FaqVO;
import kr.co.ictedu.someta.vo.PageVO;

@RestController
@RequestMapping("/faq")
public class FaqController {
	@Autowired
	private FaqService faqService;
	
	@Autowired
	private PageVO pageVO;
	
	@Value("${spring.servlet.multipart.location}")
	private String filePath;
	
	@PostMapping("/add")
	public ResponseEntity<?> addfaq(FaqVO vo){
		faqService.add(vo);
		return ResponseEntity.ok().body("ok");
	}
	
	@GetMapping("/delete")
	public void delfaq(@RequestParam("num")int num) {
		faqService.delete(num);
	}
	
	@GetMapping("/detail")
	public FaqVO detail(@RequestParam("num")int num) {
		return faqService.detail(num);
	}
	
	@RequestMapping("/list")
	public Map<String, Object> BoaradList(@RequestParam Map<String, String> paramMap){
		pageVO.setNumPerPage(10);
		String cPage = paramMap.get("cPage");

		int totalCnt = faqService.totalCount(paramMap);
		pageVO.setTotalRecord(totalCnt);
		
		int totalPage =(int)Math.ceil(totalCnt/ (double)pageVO.getNumPerPage());
		pageVO.setTotalPage(totalPage);
		
		int totalBlock=(int)Math.ceil(totalPage/(double)pageVO.getPagePerBlock());
		pageVO.setTotalBlock(totalBlock);
		
		if(cPage !=null) {
			pageVO.setNowPage(Integer.parseInt(cPage));
		}else {
			pageVO.setNowPage(1);
		}
		
		pageVO.setBeginPerPage((pageVO.getNowPage()-1)*pageVO.getNumPerPage()+1);
		pageVO.setEndPerPage(pageVO.getBeginPerPage()+pageVO.getNumPerPage()-1);
		
		
		
		Map<String, Object> response = new HashMap<>();
		
		Map<String, String> map =new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<FaqVO> list = faqService.flist(map);

		int startPage =(int)((pageVO.getNowPage()-1)/pageVO.getPagePerBlock())*pageVO.getPagePerBlock()+1;
		int endPage=startPage+pageVO.getPagePerBlock()-1;
		//블록 초기화 전체 페이지값보다 크다면 전체 페이지값을 마지막 블로페이지 값으로 저장
		if(endPage>pageVO.getTotalPage()) {
			endPage=pageVO.getTotalPage();
		}
				
		response.put("data", list);   //페이징 처리가 완료된 리스트를 저장한 데이타
		//-------------------------
		response.put("totalItems", pageVO.getTotalRecord());
		response.put("totalPages", pageVO.getTotalPage());
		response.put("currentPage", pageVO.getNowPage());
		response.put("startPage", startPage);
		response.put("endPage", endPage);
		return response;		
	}
	
}
