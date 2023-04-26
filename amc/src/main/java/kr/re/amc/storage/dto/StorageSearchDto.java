package kr.re.amc.storage.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@SuppressWarnings("serial")
public class StorageSearchDto implements Serializable {

	private int page;
	
	private int size;

	
	private String diseaseCode;
	private String insttNm;
	Long userId;
	Long dissCd;
	
	private String dataName = "";
	
    /** reqStrorage **/
    private String storgeNm = "";
    private String strgeReqSttusCd = "";	
    
    /** openStrorage **/
    private String othbcDataNm = "";
    private String  othbcReqstSttuscd;
    
    
    private String  useReqstSttusSecd;
    
	/** myStorage **/
    private String insttId;
    
    private Long dissRspnberUserId;
}
