package kr.co.ictedu.someta.like;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.someta.vo.LikeRequestVO;
import kr.co.ictedu.someta.vo.MemberVO;

@Mapper
public interface LikeRequestDao {

	void sendRequest(@Param("requester_id") String requesterId, @Param("receiver_id") String receiverId);

	List<Map<String, Object>> getPending(@Param("nickname") String nickname);

	void updateStatus(@Param("nickName") String nickName, @Param("likeNickName") String likeNickName,
			@Param("status") String status);

	List<Map<String, Object>> getLike(Map<String, Object> map);

	List<LikeRequestVO> getSentRequest(@Param("nickname") String nickname);

	int checkRequestExists(@Param("requester_id") String requesterId, @Param("receiver_id") String receiverId);

	void resendRequest(@Param("requester_id") String requesterId, @Param("receiver_id") String receiverId);

	int totalCount(Map<String, Object> map);

	List<Map<String, Object>> detail(int num);
}
