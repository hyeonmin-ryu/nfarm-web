package kr.re.amc.users.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class UserSearchDto implements Serializable {

	private int page;
	
	private int size;
	
	private String inqryTycd="";
	
	private String userType="";
	
	private String sbscrbSttusCd = "";
	
	private String dissCd = "";
	
	private String insttId = "";
	
	private String chargerNm="";
	
	private String insttNm;
	
	private String insttTyCd;
}
