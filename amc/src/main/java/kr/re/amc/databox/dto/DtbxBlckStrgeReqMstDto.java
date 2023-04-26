package kr.re.amc.databox.dto;

import lombok.Data;


@Data
public class DtbxBlckStrgeReqMstDto {

    
	private Long   dtbxReqNo;
	private Long   svrSn;
	private Long   blckStrgeReqSn;
	private String strgeTyCd;
	private Long   strgeSize;
    
    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;

    
    
}
