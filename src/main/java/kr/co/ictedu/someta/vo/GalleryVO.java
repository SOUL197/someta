package kr.co.ictedu.someta.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Alias("galvo")
public class GalleryVO {
	private int num;
	private String title;
	private String contents;
	private String writer;
	private int member_num;
	private String reip;
	private int hit;
	private int elike;
	private String gdate;
	private List<GalimgVO> imgvo;
	
}
