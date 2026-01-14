package kr.co.ictedu.someta.like;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.someta.vo.LikeRequestVO;
import kr.co.ictedu.someta.vo.MemberVO;
import kr.co.ictedu.someta.vo.PageVO;

@RestController
@RequestMapping("/api/like")
public class LikeController {

	@Autowired
	private LikeService likeService;

	@PostMapping("/request")
	public ResponseEntity<?> sendFriendRequest(@RequestBody Map<String, String> body, HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		String receiverId = body.get("receiverId");
		likeService.sendRequest(loginMember.getNickname(), receiverId);
		return ResponseEntity.ok("like요청완료");
	}

	@GetMapping("/incoming")
	public List<Map<String, Object>> getIncomingRequests(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return likeService.getPendingRequest(loginMember.getNickname());
	}

	@PostMapping("/respond")
	public ResponseEntity<?> respondToRequest(@RequestBody Map<String, String> body, HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		String nickName = loginMember.getNickname();
		String likeNickName = body.get("nickname");
		String action = body.get("action");
		likeService.respond(nickName, likeNickName, action);
		return ResponseEntity.ok("처리완료");
	}

	@PostMapping("/mylike")
	public Map<String, Object> myLike(@RequestBody Map<String, Object> bodyMap, HttpSession session, PageVO pageVO) {
		pageVO.setNumPerPage(12);
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		bodyMap.put("nickname", loginMember.getNickname());

		String cPage = String.valueOf(bodyMap.get("cPage"));

		if (cPage != null && !cPage.equals("null")) {
			pageVO.setNowPage(Integer.parseInt(cPage));
		} else {
			pageVO.setNowPage(1);
		}

		int totalCnt = likeService.totalCount(bodyMap);
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

		List<Map<String, Object>> likes = likeService.getLike(map);

		int startPage = (int) ((pageVO.getNowPage() - 1) / pageVO.getPagePerBlock()) * pageVO.getPagePerBlock() + 1;
		int endPage = startPage + pageVO.getPagePerBlock() - 1;

		if (endPage > pageVO.getTotalPage()) {
			endPage = pageVO.getTotalPage();
		}

		Map<String, Object> response = new HashMap<>();

		response.put("data", likes);
		response.put("totalItems", pageVO.getTotalRecord());
		response.put("totalPages", pageVO.getTotalPage());
		response.put("currentPage", pageVO.getNowPage());
		response.put("startPage", startPage);
		response.put("endPage", endPage);

		return response;
	}

	@GetMapping("/outgoing")
	public List<LikeRequestVO> ngetOutgoingRequests(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return likeService.getSentRequest(loginMember.getNickname());
	}

	@GetMapping("/likedetail")
	public Map<String, Object> GalleryDetail(@RequestParam("num") int num) {
		System.out.println(num);
		System.out.println(likeService.detail(num));
		return likeService.detail(num);
	}
}
