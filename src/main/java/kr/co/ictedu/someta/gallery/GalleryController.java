package kr.co.ictedu.someta.gallery;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import kr.co.ictedu.someta.vo.Board_CommVO;
import kr.co.ictedu.someta.vo.Gal_CommVO;
import kr.co.ictedu.someta.vo.GalimgVO;
import kr.co.ictedu.someta.vo.GalleryVO;
import kr.co.ictedu.someta.vo.PageVO;



@RestController
@RequestMapping("/gallery")
public class GalleryController {
	
	@Autowired
	private GalleryService galleryService;
	@Autowired
	private PageVO pageVO;
	@Autowired
	private GalCommService galCommService;

	@Value("${spring.servlet.multipart.location}")
	private String uploadDir;

	@PostMapping("/galadd")
	public ResponseEntity<?> addGallery(@ModelAttribute GalleryVO galleryVO,
			@RequestParam("images") MultipartFile[] images, HttpServletRequest req) {
		galleryVO.setReip(req.getRemoteAddr());
		List<GalimgVO> imgList = new ArrayList<>();
		try {
			for (MultipartFile file : images) {
				if (!file.isEmpty()) {
					String originalFilename = file.getOriginalFilename();
					File f = new File(uploadDir + "/gallery/", originalFilename);
					file.transferTo(f);
					GalimgVO imageVO = new GalimgVO();
					imageVO.setImagename(originalFilename);
					imgList.add(imageVO);
					System.out.println(originalFilename);
				}
			}
			
			galleryVO.setImgvo(imgList);
			System.out.println(imgList);
			galleryService.transcationProcess(galleryVO, imgList);
			System.out.println("정상적인 처리");
			return ResponseEntity.ok("갤러리 등록 성공");
		} catch (Exception e) {
			System.out.println("오류가 났음!");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 실패");
		}
	}

	@RequestMapping("/gallist")
	public Map<String, Object> galleryList(@RequestParam Map<String, String> paramMap, HttpServletRequest req) {
		
		pageVO.setNumPerPage(9);
		System.out.println("Method =>" + req.getMethod());
		
		String cPage = paramMap.get("cPage");

		// 1.총 게시물 수 =>PageVO에 해당 property에 setter 호출해서 값을 저장해 둔다.
		int totalCnt = galleryService.totalCount(paramMap);
		pageVO.setTotalRecord(totalCnt);

		// 2.총 페이지 수 구하기
		// 11개의 데이터 ->11/10.0 =>1.1=>2
		int totalPage = (int) Math.ceil(totalCnt / (double) pageVO.getNumPerPage());
		pageVO.setTotalPage(totalPage);

		// 3. 총블록수 저장
		// 전체페이지/ pagePerBlock
		int totalBlock = (int) Math.ceil(totalPage / (double) pageVO.getPagePerBlock());
		pageVO.setTotalBlock(totalBlock);

		// 4.현재 페이지 설정
		if (cPage != null) {
			pageVO.setNowPage(Integer.parseInt(cPage));
		} else {
			pageVO.setNowPage(1);
		}

		// 5.현재 페이지의 시작 게시물과 끝 게시물 번호를 계산해서 pageVO에 저장
		// 시작페이지 공식 ((현재페이지값 -1) * 페이지당 보여줄수) +1
		pageVO.setBeginPerPage((pageVO.getNowPage() - 1) * pageVO.getNumPerPage() + 1);
		pageVO.setEndPerPage(pageVO.getBeginPerPage() + pageVO.getNumPerPage() - 1);

		// 6. result Map
		Map<String, Object> response = new HashMap<>();
		// 기존의 paramMap에 새로운 데이터를 추가한다.
		Map<String, String> map = new HashMap<>(paramMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));
		List<Map<String, Object>> list = galleryService.list(map);

		// 7. 페이지 블록을 구현
		int startPage = (int) ((pageVO.getNowPage() - 1) / pageVO.getPagePerBlock()) * pageVO.getPagePerBlock() + 1;
		int endPage = startPage + pageVO.getPagePerBlock() - 1;
		// 블록 초기화 전체 페이지값보다 크다면 전체 페이지값을 마지막 블로페이지 값으로 저장
		if (endPage > pageVO.getTotalPage()) {
			endPage = pageVO.getTotalPage();
		}

		response.put("data", list); // 페이징 처리가 완료된 리스트를 저장한 데이타
		// -------------------------
		response.put("totalItems", pageVO.getTotalRecord());
		response.put("totalPages", pageVO.getTotalPage());
		response.put("currentPage", pageVO.getNowPage());
		response.put("startPage", startPage);
		response.put("endPage", endPage);
		return response;
	}
	@GetMapping("/galdetail")
	public Map<String, Object> detail(@RequestParam("num") int num){
		return galleryService.detail(num);
	}
	@GetMapping("/galdel")
	public void galDel(@RequestParam("num") int num) {
		galleryService.delete(num);
		System.out.println("삭제 완료!");		
	}
	@PostMapping("/addcomm")
	public ResponseEntity<?> addgalComm(@RequestBody Gal_CommVO vo, HttpServletRequest req){
		vo.setReip(req.getRemoteAddr());
		galCommService.add(vo);
		return ResponseEntity.ok().body("ok");
	}
	
//	@GetMapping("/listcomm")
//	public List<Gal_CommVO> listgalComm(@RequestParam("num") int num){	
//		return galCommService.listComm(num);
//	}
	
	@RequestMapping("/listcomm")
	public Map<String, Object> galcommList(@RequestParam Map<String, String> paramMap, HttpServletRequest req){
		
		pageVO.setNumPerPage(7);
		String cPage = paramMap.get("cPage");

		int totalCnt = galCommService.totalCount(paramMap);
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
		List<Gal_CommVO> list = galCommService.listComm(map);
		
		int startPage =(int)((pageVO.getNowPage()-1)/pageVO.getPagePerBlock())*pageVO.getPagePerBlock()+1;
		int endPage=startPage+pageVO.getPagePerBlock()-1;
		//블록 초기화 전체 페이지값보다 크다면 전체 페이지값을 마지막 블로페이지 값으로 저장
		if(endPage>pageVO.getTotalPage()) {
			endPage=pageVO.getTotalPage();
		}
				
		response.put("data", list);   //페이징 처리가 완료된 리스트를 저장한 데이타	
		response.put("totalItems", pageVO.getTotalRecord());
		response.put("totalPages", pageVO.getTotalPage());
		response.put("currentPage", pageVO.getNowPage());
		response.put("startPage", startPage);
		response.put("endPage", endPage);
		return response;
	}
	
	@PostMapping("/elike")
	public ResponseEntity<?> eLike(@RequestParam("num") int num) {
	    // 서비스 호출하여 DB 업데이트 수행
	    galleryService.elike(num);
	    return ResponseEntity.ok().body("ok");
	}
	
}
