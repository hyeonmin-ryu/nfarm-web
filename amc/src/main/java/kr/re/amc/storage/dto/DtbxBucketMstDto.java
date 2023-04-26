package kr.re.amc.storage.dto;

import lombok.Data;


@Data
public class DtbxBucketMstDto {

	private String storgeReqstId;
	private String strgeReqSttusCd;
	private String bucketId;
    private String bucketDc;
    private Long bucketSize;
    private Long othbcUserId;
    
    
    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;
    

    
    
}
