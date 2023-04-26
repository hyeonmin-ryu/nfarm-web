package kr.re.amc.cmm.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MenuDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String menuType;
	private String userRoleSecd;
	
	private String menuId;	
	private String menuNm;
	private String menuDc;	
	private String menuUrl;	
	private int    menuIndictOrdr;	
	private String menuUseYn;
	private String parntsMenuId;	
	
	private String children;
	private String label;
	
    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;
	
    private String checkId;
	
}
