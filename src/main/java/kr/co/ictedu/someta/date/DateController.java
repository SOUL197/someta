package kr.co.ictedu.someta.date;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import kr.co.ictedu.someta.vo.DateRequestVO;
import kr.co.ictedu.someta.vo.MemberVO;

@RestController
@RequestMapping("/api/date")
public class DateController {
	@Autowired
	private DateService dateService;

	@PostMapping("/request")
	public ResponseEntity<?> sendFriendRequest(@RequestBody Map<String, String> body, HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		String receiverId = body.get("receiverId");
		String location = body.get("location");
		int myCount = dateService.countDate(loginMember.getNickname());
		int LikeCount = dateService.countDate(receiverId);
		if (myCount == 0 && LikeCount == 0) {
			dateService.sendRequest(loginMember.getNickname(), receiverId, location);
			return ResponseEntity.ok("date요청완료");
		} else {
			return ResponseEntity.ok("already");
		}
		
	}

	@GetMapping("/incoming")
	public List<Map<String, Object>> getIncomingRequests(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return dateService.getPendingRequest(loginMember.getNickname());
	}

	@PostMapping("/respond")
	public ResponseEntity<?> respondToRequest(@RequestBody Map<String, String> body, HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return ResponseEntity
				.ok(dateService.respond(loginMember.getNickname(), body.get("nickname"), body.get("action")));
	}

	@GetMapping("/mydate")
	public List<Map<String, Object>> myDate(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return dateService.getDate(loginMember.getNickname());
	}

	@GetMapping("/outgoing")
	public List<DateRequestVO> ngetOutgoingRequests(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return dateService.getSentRequest(loginMember.getNickname());
	}
	
	@GetMapping("/checkdate")
	public int CheckLike(HttpSession session) {
		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
		return dateService.checkPending(loginMember.getNickname());
	}

}
