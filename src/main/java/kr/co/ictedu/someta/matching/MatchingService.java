package kr.co.ictedu.someta.matching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.ictedu.someta.vo.MemberProfileImageVO;

@Service
public class MatchingService {

	@Autowired
	private MatchingDao matchingDao;
	
	public void profileUpdate(MemberProfileImageVO vo) {
		matchingDao.update(vo);
	}

	public List<Map<String, Object>> list(Map<String, Object> map) {
		return matchingDao.list(map);
	}

	public int totalCount(Map<String, Object> map) {
		return matchingDao.totalCount(map);
	}
	
	public Map<String,Object> detail(int num) {
		List<Map<String, Object>> rows = matchingDao.detail(num);
		
		if (rows.isEmpty())
			return null;
		
		Map<String, Object> result = new HashMap<>();
		result.put("num", rows.get(0).get("NUM"));
		result.put("nickname", rows.get(0).get("NICKNAME"));
		result.put("birth", rows.get(0).get("BIRTH"));
		List<String> images = new ArrayList<>();
		for (Map<String, Object> row : rows) {
			images.add((String) row.get("PROFILEIMAGE"));
		}
		result.put("profileimage", images);
		return result;
	}
	
	public String getProfileImage(String nickname) {
        String profileImage = matchingDao.getProfileImage(nickname);
        // 이미지가 없을 경우 기본 이미지 반환 (선택 사항)
        return (profileImage != null) ? profileImage : "Default_user.jpg";
    }

}
