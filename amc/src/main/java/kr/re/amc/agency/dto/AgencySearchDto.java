package kr.re.amc.agency.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class AgencySearchDto implements Serializable{

	private int page;
	
	private int size;
	
	private String dataName;
}
