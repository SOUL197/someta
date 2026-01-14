package kr.co.ictedu.someta.matching;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.someta.vo.MemberProfileImageVO;
import kr.co.ictedu.someta.vo.MemberVO;
import kr.co.ictedu.someta.vo.PageVO;

@RestController
@RequestMapping("/matching")
public class MatchingController {
	@Autowired
	private MatchingService matchingService;

	@Value("${spring.servlet.multipart.location}")
	private String uploadDir;

	@PostMapping("/profileup")
	public ResponseEntity<?> update(MemberProfileImageVO imgvo, @RequestParam("profile") MultipartFile[] images) {
		try {
			for (MultipartFile file : images) {
				if (!file.isEmpty()) {
					System.out.println(file);
					System.out.println(imgvo.getUserid());
					String oriFn = file.getOriginalFilename();
					File f = new File(uploadDir + "/profileimage/", oriFn);
					file.transferTo(f);
					imgvo.setProfileimage(oriFn);
					matchingService.profileUpdate(imgvo);
				}
			}
			System.out.println("정상적인 처리");
			return ResponseEntity.ok("갤러리 등록 성공");
		} catch (Exception e) {
			System.out.println("오류 발생");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업로드 실패");
		}

	}

	@RequestMapping("/matchinglist")
	public Map<String, Object> mathcingList(@RequestBody Map<String, Object> bodyMap, HttpSession session,
			PageVO pageVO) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		System.out.println(loginMember.getId());
		bodyMap.put("nickname", loginMember.getNickname());
		String cPage = String.valueOf(bodyMap.get("cPage"));

		if (cPage != null) {
			pageVO.setNowPage(Integer.parseInt(cPage));
		} else {
			pageVO.setNowPage(1);
		}

		int totalCnt = matchingService.totalCount(bodyMap);
		pageVO.setTotalRecord(totalCnt);

		int totalPage = (int) Math.ceil(totalCnt / (double) pageVO.getNumPerPage());
		pageVO.setTotalPage(totalPage);

		int totalBlock = (int) Math.ceil(totalPage / (double) pageVO.getPagePerBlock());
		pageVO.setTotalBlock(totalBlock);

		pageVO.setBeginPerPage((pageVO.getNowPage() - 1) * pageVO.getNumPerPage() + 1);
		pageVO.setEndPerPage(pageVO.getBeginPerPage() + pageVO.getNumPerPage() - 1);

		Map<String, Object> map = new HashMap<>(bodyMap);
		map.put("begin", String.valueOf(pageVO.getBeginPerPage()));
		map.put("end", String.valueOf(pageVO.getEndPerPage()));

		List<Map<String, Object>> list = matchingService.list(map);

		int startPage = (int) ((pageVO.getNowPage() - 1) / pageVO.getPagePerBlock()) * pageVO.getPagePerBlock() + 1;
		int endPage = startPage + pageVO.getPagePerBlock() - 1;

		if (endPage > pageVO.getTotalPage()) {
			endPage = pageVO.getTotalPage();
		}

		Map<String, Object> response = new HashMap<>();
		response.put("data", list);
		response.put("totalItems", pageVO.getTotalRecord());
		response.put("totalPages", pageVO.getTotalPage());
		response.put("currentPage", pageVO.getNowPage());
		response.put("startPage", startPage);
		response.put("endPage", endPage);
		return response;
	}

	@GetMapping("/matchingdetail")
	public Map<String, Object> GalleryDetail(@RequestParam("num") int num) {
		System.out.println(num);
		System.out.println(matchingService.detail(num));
		return matchingService.detail(num);
	}
	
	@GetMapping("/getimage")
    public ResponseEntity<String> getMemberImage(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        String imageName = matchingService.getProfileImage(loginMember.getNickname());
        return ResponseEntity.ok(imageName);
    }
}
