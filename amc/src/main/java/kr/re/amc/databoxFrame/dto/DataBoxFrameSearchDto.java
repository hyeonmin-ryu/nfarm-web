package kr.re.amc.databoxFrame.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class DataBoxFrameSearchDto implements Serializable {

	private int page;
	
	private int size;

	private String dtbxFrmeNm;
	private String dtbxFrmeSttusCd;
	
	
}
