package kr.re.amc.databoxFrame.dto;

import lombok.Data;

@Data
public class DtbxFrmeAdmSvrAcntInfDto {

    private Long   dtbxFrmeNo;
    private Long   dtbxFrmeAcntNo;
    private String dtbxFrmeAdmHostNm;
    private String dtbxFrmeAdmIp;
    private String dtbxFrmeAdmAcntId;
    private String dtbxFrmeAdmAcntPassword;
    
	private Long creatUserId;
	private String creatDt;
	private Long updtUserId;
	private String updtDt;
    
    
}
