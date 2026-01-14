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
		int exists = dateDao.checkRequestExists(requesterId, receiverId);
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
		int myCount = dateDao.countDate(nickname);
		int LikeCount = dateDao.countDate(likeNickName);
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
		return dateDao.countDate(nickname);
	}
}
