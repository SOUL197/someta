package kr.co.ictedu.someta.date;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.someta.vo.DateRequestVO;

@Service
public class DateService {
	@Autowired
	private DateDao dateDao;

	@Transactional
	public void sendRequest(String requesterId, String receiverId, String dateLocation) {
		// 첫 데이트 요청인지 확인
		int exists = dateDao.checkRequestExists(requesterId, receiverId);
		// 첫 요청이면 insert 중복 요청이면 update하도록 하도록 위 check와 함께 트랜잭션 처리
		if (exists > 0) {
			dateDao.resendRequest(requesterId, receiverId, dateLocation);
		} else {
			dateDao.sendRequest(requesterId, receiverId, dateLocation);
		}
	}

	public List<Map<String, Object>> getPendingRequest(String nickname) {
		return dateDao.getPending(nickname);
	}

	@Transactional
	public String respond(String nickname, String likeNickName, String action) {
		// 응답시 서로 데이트 중인지 확인
		int myCount = dateDao.countDate(nickname);
		int LikeCount = dateDao.countDate(likeNickName);
		// 자신 또는 상대가 데이트 중이 아니라면 응답시 accept 아니라면 자동으로 reject를 보내도록 위 count와 함께 트랜잭션 처리
		String status = (action.equals("accept") && myCount == 0 && LikeCount == 0) ? "accepted" : "rejected";
		dateDao.updateStatus(nickname, likeNickName, status);
		return status;
	}

	public List<Map<String, Object>> getDate(String nickname) {
		return dateDao.getDate(nickname);
	}

	public List<DateRequestVO> getSentRequest(String nickname) {
		return dateDao.getSentRequest(nickname);
	}

	public int countDate(String nickname) {
		// 데이트 중이면 1 아니면 0 반환
		return dateDao.countDate(nickname);
	}

	public int checkPending(String nickname) {
		// pending 인 date 요청 개수 반환 
		return dateDao.checkPending(nickname);
	}
}
