package kr.re.amc.databox.dto;

import java.util.ArrayList;
import java.util.List;

import kr.re.amc.users.dto.AgencyDto;
import kr.re.amc.users.dto.UserInfoDto;
import lombok.Data;


@Data
public class DtbxReqHst {

	private Long dtbxReqHstNo;
	private Long dtbxReqNo;
	private String dtbxReqTyCd;
	private String dtbxReqTyNm;
	private String dtbxNm;
	private String dtbxMemo;
	private Long reqUserId;
	private String reqDt;
	private String canclDt;
	private String rceptDt;
	private String processDt;
	private String rejectResn;
	private String deleteReqstDt;
	private String deleteCanclDt;
	private String deleteRceptDt;
	private String deleteProcessDt;
	private String deleteRejectResn;
	private String dtbxReqstSttusCd;
	private String dtbxReqstSttusNm;
	
	private Long dtbxSn;
	private String transrDtbxReqstSttusCd;
	private String transrDtbxReqstSttusNm;
	private Long dtbxFrmeNo;
	
	private Long creatUserId;
	private String creatDt;
	private Long updtUserId;
	private String updtDt;
	
    private String insttNm; //agencyName;	
    private String chargerNm;// userName;
    
    
    private UserInfoDto userInfo;
    
    private AgencyDto agencyInfo;
    
    private DtbxMstDto dtbxMst;

    
    private List<DtbxUserReqMstDto> userConList = new ArrayList<>();
    
    private List<DtbxSvrReqMstDto> serverList = new ArrayList<>();
    
    private List<DtbxDataReqMstDto> bucketList = new ArrayList<>();
    
    
    
    
}
