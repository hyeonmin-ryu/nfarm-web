package kr.re.amc.storage.dto;
import lombok.Data;

@Data
public class StrgeUseInfDto {

	private String useStrgeId;// useStorageId;
    private String othbcReqstId; //reqOpenId;	
    private String storgeReqstId;//reqStorageId;
    
    private String dissCd;
    private String dissNm;	
    private String storgeNm;	
    private String insttNm;	
    private String chargerNm;// userName;
    private String email;
    private String chargerMoblphonNo;
    
    private String othbcReqstSttuscd;
    private String othbcReqstSttusnm;	
    private String othbcDataNm;
    private String othbcDataDc;
    
    private String useReqstSttusSecd;
    private String useReqstSttusSenm;
    
    private Long   dissRspnberUserId;
    
    private String endDt;
    private String confmDt;
    
    private String canclResn;
    private String rejectResn;
    private String deleteReason;
    private String bucketId;

    private Long bucketSize;
    
    private String creatDt;	
    private String updtDt;	
    private Long   creatUserId;
    private Long   updtUserId;
    
    private int useDaycnt;
    
    private String reqUserId;
    private String reqChargerNm;	
    private String reqInsttNm;	
    private String reqChargerMoblphonNo; 
    private String reqEmail;
    private String reqinsttId;
    
	
}
