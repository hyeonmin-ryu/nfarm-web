package kr.re.amc.databox.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class DataBoxSearchDto implements Serializable {

	private int page;
	
	private int size;

	Long userId;
	
	private String pageGubun;
	private String insttNm;
	private String insttId;
	
	private String dtbxNm;
	private String dtbxReqstSttusCd;
	
	private String othbcDataNm;
	private String  useReqstSttusSecd;
	
}
