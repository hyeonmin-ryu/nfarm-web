package kr.re.amc.databox.dto;

import lombok.Data;


@Data
public class DtbxUserReqMstDto {

	
	
	private long dtbxSn;
	private long dtbxReqNo;
	private long userSn;
	
    private String dtbxUserName;
    private String dtbxUserMoblphonNo;
    
    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;
    
    private String sslVpnUserId;
    private String sslVpnUserPassword;
    private String cnncSvrIpAdres;
    private String cnncSvrUserId;
    private String cnncSvrUserPassword;
    
    
}
