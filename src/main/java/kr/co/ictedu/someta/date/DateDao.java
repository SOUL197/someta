package kr.co.ictedu.someta.date;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.ictedu.someta.vo.DateRequestVO;

@Mapper
public interface DateDao {
	void sendRequest(@Param("requester_id") String requesterId, @Param("receiver_id") String receiverId,
			@Param("date_location") String dateLocation);

	List<Map<String, Object>> getPending(@Param("nickname") String nickname);

	void updateStatus(@Param("nickName") String nickName, @Param("likeNickName") String likeNickName,
			@Param("status") String status);

	List<Map<String, Object>> getDate(@Param("nickname") String nickname);

	List<DateRequestVO> getSentRequest(@Param("nickname") String nickname);

	int checkRequestExists(@Param("requester_id") String requesterId, @Param("receiver_id") String receiverId);

	void resendRequest(@Param("requester_id") String requesterId, @Param("receiver_id") String receiverId,
			@Param("date_location") String dateLocation);

	int countDate(@Param("nickname") String nickname);
}
