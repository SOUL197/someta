package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Alias("mpivo")
public class MemberProfileImageVO {
	private int userid;
	private String profileimage;
}
