package kr.co.ictedu.someta.like;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.ictedu.someta.vo.LikeRequestVO;

@Service
public class LikeService {

	@Autowired
	private LikeRequestDao likeRequestDao;

	@Transactional
	public void sendRequest(String from, String to) {
		// 첫 요청인지 아닌지 확인
		int exists = likeRequestDao.checkRequestExists(from, to);
		// 첫 요청이면 insert 중복 요청이면 update하도록 하도록 위 check와 함께 트랜잭션 처리
		if (exists > 0) {
			likeRequestDao.resendRequest(from, to);
		} else {
			likeRequestDao.sendRequest(from, to);
		}
	}

	public List<Map<String, Object>> getPendingRequest(String nickname) {
		List<Map<String, Object>> result = likeRequestDao.getPending(nickname);
		return result;
	}

	public void respond(String nickName, String likeNickName, String action) {
		// 수락시 accept 요청을 보내 db에 status를 accepted로 저장 아닐 시 rejected로 저장
		likeRequestDao.updateStatus(nickName, likeNickName, action.equals("accept") ? "accepted" : "rejected");
	}

	public List<Map<String, Object>> getLike(Map<String, Object> map) {
		return likeRequestDao.getLike(map);
	}

	public List<LikeRequestVO> getSentRequest(String nickname) {
		return likeRequestDao.getSentRequest(nickname);
	}

	public int totalCount(Map<String, Object> map) {
		return likeRequestDao.totalCount(map);
	}

	public Map<String, Object> detail(int num) {
		List<Map<String, Object>> rows = likeRequestDao.detail(num);

		if (rows.isEmpty()) {
			return null;
		}
		// 첫 줄의 유저의 프로필 데이터만 가져옴
		Map<String, Object> result = new HashMap<>(rows.get(0));
		// db의 트리거가 phone을 null로 넣어놓기에 null대신 phone:blank로 보내기
		if (result.get("PHONE") == null) {
			result.put("PHONE", "BLANK");
		}
		List<String> images = new ArrayList<>();
		// 프로필 이미지 전부 꺼내서 배열로 저장
		for (Map<String, Object> row : rows) {
			images.add((String) row.get("PROFILEIMAGE"));

		}
		// 첫줄 하나만 있던 프로필 이미지 지우고 PROFILEIMAGES로 배열로 저장
		result.remove("PROFILEIMAGE");
		result.put("PROFILEIMAGES", images);
		// 유저의 전체 프로필 데이터	
		return result;
	}

	public int checkPending(String nickname) {
		// Like 요청의 개수를 반환
		return likeRequestDao.checkPending(nickname);
	}
}
