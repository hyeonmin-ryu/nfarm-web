package kr.re.amc.storage.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class StrgeReqInfDto {

    private String storgeReqstId; //reqStorageId;	
    
    private String dissCd; //diseaseCode;
    private String dissNm; //diseaseCodeNm;	
    
    @NotNull(message = "저장데이터명은 필수입니다.")
    private String storgeNm;	
    private String insttNm; //agencyName;	
    private String chargerNm;// userName;
    
    private String strgeReqSttusCd; //reqStorageStatCode;
    private String strgeReqSttusNm; //reqStorageStatCodeNm;	
    
    private Long  dissRspnberUserId; //diseaseManagerUserSeq;
    private String rejectResn; //rejectReason;
    private String deleteResn; //deleteReason;
    private String bucketId; //bucketName;
    private String bucketDc; //bucketDesc;
    
    private String dissRspnberUserNm;
    private String diseaseMainYn;

    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;
    
    private String chargerMoblphonNo;
    private String email;
	
    private Long bucketSize;
}
