package kr.re.amc.board.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class BoardSearchDto implements Serializable {
	
	private int page;
	
	private int size;
	
	private String keyword = "";
	
	private String questRspnsTycd="";
	
	private String startMonth ="";
	
	private String endMonth = "";
	
	private String dataName = "";
}
