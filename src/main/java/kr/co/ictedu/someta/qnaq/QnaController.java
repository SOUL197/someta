package kr.co.ictedu.someta.qnaq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.someta.qnaa.QnaAService;
import kr.co.ictedu.someta.vo.PageVO;
import kr.co.ictedu.someta.vo.Qna_aVO;
import kr.co.ictedu.someta.vo.Qna_qVO;

@RestController
@RequestMapping("/qna")
public class QnaController {
	
	@Autowired
	private QnaService qnaService;
	
	@Autowired
	private QnaAService qnaAService;
	
	
	@Autowired
	private PageVO pageVO;
	
	@PostMapping("/addq")
	public ResponseEntity<?> addgongji( Qna_qVO vo){
	
		try {
			qnaService.addq(vo);
			return ResponseEntity.ok().body("ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 안됨");
	}
	
	
	@GetMapping("/qdel")
	public void delQna_q(@RequestParam("qnum")int qnum) {
		qnaService.deleteq(qnum);
	}
	
	@GetMapping("/qdetail")
	public Qna_qVO qdetail(@RequestParam("qnum")int qnum) {
		return qnaService.detail(qnum);
	}
	@RequestMapping("/qlist")
	public Map<String, Object> myqna(@RequestParam Map<String, String> paramMap){
		pageVO.setNumPerPage(10);
		String cPage = paramMap.get("cPage");

		int totalCnt = qnaService.totalCount(paramMap);
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
		List<Qna_qVO> list = qnaService.myqna(map);

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
	
	@RequestMapping("/alist")
	public Map<String, Object> qnaaList(@RequestParam Map<String, String> paramMap, HttpServletRequest req){
		pageVO.setNumPerPage(10);
		String cPage = paramMap.get("cPage");

		int totalCnt = qnaAService.totalCount(paramMap);
		
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
		List<Qna_aVO> list = qnaAService.myqnaa(map);

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
	
	
	@PostMapping("/addanswer")
	public ResponseEntity<?> addanswer(@RequestBody Qna_aVO vo){
		qnaAService.addanswer(vo);
		return ResponseEntity.ok().body("ok");
	}

	
	@GetMapping("/delanswer")
	public void delanswer(@RequestParam("anum")int anum) {
		qnaAService.delanswer(anum);
	}
	
	
	
	/*Map<String, Object> response = new HashMap<>();
		
		Map<String, String> map =new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));*/
	
	
}
