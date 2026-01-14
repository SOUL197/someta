package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("datevo")
public class DateRequestVO {
	private int id;
	private String requester_id;
	private String receiver_id;
	private String status;
	private String date_location;
	private String request_date;
}
