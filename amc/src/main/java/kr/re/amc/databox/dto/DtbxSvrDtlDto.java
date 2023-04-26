package kr.re.amc.databox.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class DtbxSvrDtlDto {

    
    private Long dtbxSn;
    private Long svrSn;
    private String svrIpAdres;
    private String svrUserId;
    private String svrUserPassword;
    
    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;
    
    
    
}
