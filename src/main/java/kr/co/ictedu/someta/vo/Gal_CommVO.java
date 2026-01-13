package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("galcommvo")
public class Gal_CommVO {
	private int comm_num;
	private int gallery_num;
	private String gwriter;
	private int member_num;
	private String gcontent;
	private int elike;
	private String reip;
	private String gcdate;
}
