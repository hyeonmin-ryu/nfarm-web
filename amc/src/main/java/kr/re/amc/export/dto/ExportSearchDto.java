package kr.re.amc.export.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class ExportSearchDto implements Serializable{
	
	private String tkoutProcessSttusCd;
	private String dataName;
	private Long userId;
	private int page;
	
	private int size;
}
