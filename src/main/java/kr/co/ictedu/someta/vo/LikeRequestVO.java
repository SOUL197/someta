package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("likevo")
public class LikeRequestVO {
	private int id;
	private String requester_id;
	private String receiver_id;
	private String status;
	private String request_date;
}
