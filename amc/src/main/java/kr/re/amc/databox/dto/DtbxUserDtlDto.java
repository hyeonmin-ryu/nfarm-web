package kr.re.amc.databox.dto;

import lombok.Data;


@Data
public class DtbxUserDtlDto {

    private Long dtbxSn;
    private Long userSn;
    private String sslVpnUserId;
    private String sslVpnUserPassword;
    private String cnncSvrIpAdres;
    private String cnncSvrUserId;
    private String cnncSvrUserPassword;
    
    
    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;

    
    
}
