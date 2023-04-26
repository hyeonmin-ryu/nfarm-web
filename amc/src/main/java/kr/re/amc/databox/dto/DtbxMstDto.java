package kr.re.amc.databox.dto;

import java.util.ArrayList;
import java.util.List;


import lombok.Data;


@Data
public class DtbxMstDto {

	private Long dtbxSn;
	private String dtbxOperAcntId;
	private String dtbxSttusCd;
	private String useBeginDt;
	private String useEndDt;
	private String manageSvrIpAdres;
	private String manageSvrUserId;
	private String manageSvrUserPassword;
	
	

	
	private Long creatUserId;
	private String creatDt;
	private Long updtUserId;
	private String updtDt;
    
    
}
