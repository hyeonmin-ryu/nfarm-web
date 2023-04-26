package kr.re.amc.cmm.dto;

import lombok.Data;

@Data
public class CommonDto{
	private Long noticeSeq;
	
	private String title;
	
	private Integer inqCount;
	
	private String contents;
	
//	private IsYn importantYn;
}
