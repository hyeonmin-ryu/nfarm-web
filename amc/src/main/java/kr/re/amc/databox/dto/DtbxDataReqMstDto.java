package kr.re.amc.databox.dto;

import lombok.Data;


@Data
public class DtbxDataReqMstDto {

    
    private Long dtbxReqNo;
    private Long useStrgeNo;
    private String useStrgeId;
    private String bucketId;
    
    
	private String storgeReqstId;
	private String strgeReqSttusCd;
    private String bucketDc;
    private Long bucketSize;
    private Long othbcUserId;
    
    private String ncloudAccesKey;
    private String ncloudScrtyKey;
    
    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;

    
    
}
