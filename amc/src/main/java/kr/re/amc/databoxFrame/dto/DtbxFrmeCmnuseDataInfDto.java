package kr.re.amc.databoxFrame.dto;

import lombok.Data;

@Data
public class DtbxFrmeCmnuseDataInfDto  {

    private Long   dtbxFrmeNo;
    private Long   dtbxFrmeCmnuseDataSn;
    private String cmnuseDataCpcty;
    private String cmnuseDataQy;
    
	private Long creatUserId;
	private String creatDt;
	private Long updtUserId;
	private String updtDt;
    
    
}
