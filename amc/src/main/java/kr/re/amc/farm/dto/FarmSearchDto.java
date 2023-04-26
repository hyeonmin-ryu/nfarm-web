package kr.re.amc.farm.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class FarmSearchDto implements Serializable{
	
	private String farmName = "";
	
	private int page;
	
	private int size;
}
