package kr.co.ictedu.someta.vo;

import org.apache.ibatis.type.Alias;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Alias("galimgvo")
public class GalimgVO {
	private String galleryid;
	private String imagename;
}
