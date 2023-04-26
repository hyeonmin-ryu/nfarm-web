package kr.re.amc.demand.dto;
import java.io.Serializable;

import kr.re.amc.board.dto.FaqDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class DemandSearchDto implements Serializable{

	private int page;
	
	private int size;
	 
	private String ncloudId = "";
	
	private String loginId = "";
	
	private String keyword = "";
	
	private String startMonth = "";
	
	private String endMonth = "";
	
	private String dataName="";
	
	private String orgName="";
	
	private String rqestYm="";
	
	private String demandMonth = "";
	
	private String mailText ="";
	private Long userId;
	private Long userSeq;
	
}
