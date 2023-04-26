package kr.re.amc.databoxFrame.dto;

import lombok.Data;

@Data
public class DtbxFrmeAdmSvrInfDto {

    private Long   dtbxFrmeNo;
    private Long   admSvrSn;
    private String admSvrTyCd;
    private String admSvrSpecCd;
    private String admSvrQy;
    
	private Long creatUserId;
	private String creatDt;
	private Long updtUserId;
	private String updtDt;
    
}
