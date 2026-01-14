package kr.co.ictedu.someta.like;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		int exists = likeRequestDao.checkRequestExists(from, to);
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

		Map<String, Object> result = new HashMap<>(rows.get(0));
		if (result.get("PHONE") == null) {
			result.put("PHONE", "BLANK");
		}
		List<String> images = new ArrayList<>();
		for (Map<String, Object> row : rows) {
			images.add((String) row.get("PROFILEIMAGE"));

		}
		result.remove("PROFILEIMAGE");
		result.put("PROFILEIMAGES", images);

		return result;
	}

}
