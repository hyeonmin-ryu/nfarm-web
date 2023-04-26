package kr.re.amc.storage.dto;
import lombok.Data;

@Data
public class StrgeOthbcReqstInfDto {

	private String useStrgeId; //useStorageId;
	
    private String othbcReqstId; //reqOpenId;	
    private String storgeReqstId; //reqStorageId;
    
    private String dissCd; //diseaseCode;
    private String dissNm; //diseaseCodeNm;	
    
    private String storgeNm;	
    private String insttNm; //agencyName;	
    private String chargerNm;// userName;
    private String email;
    
    private String othbcReqstSttuscd;
    private String othbcReqstSttusnm;	
    private String othbcDataNm;
    private String othbcDataDc;
    
    private String useReqstSttusSecd;
    
    private Long   dissRspnberUserId;
    
    private String chargerMoblphonNo;
    
    private String endDate;
    private String acceptDate;
    
    private String canclResn;
    private String rejectResn;
    private String deleteResn;
    private String bucketId;

    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;
    
    private int useDaycnt;
	
	
}
