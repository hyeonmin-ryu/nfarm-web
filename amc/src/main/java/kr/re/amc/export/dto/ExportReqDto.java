package kr.re.amc.export.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ExportReqDto {

	private Long tkoutReqstNo;
	private Long dtbxSn;
	private String tkoutResn;
	private String tkoutTrgetCours;
	private Long tkoutReqstUserId;
	private String reqstDt;
	private String canclDt;
	private String rceptDt;
	private String processDt;
	private String rejectResn;
	private String tkoutProcessSttusCd;
	private String tkoutAgreYn;
	private String tkoutAgreDt;
	private Integer tkoutDataCo;
	private Long tkoutDataSize;
	private String tkoutDataTy;
	private Long creatUserId;
	private String creatDt;
	private Long updtUserId;
	private String updtDt;
	private String dtbxReqNo;
	private String dtbxNm;
	private String chargerNm;
	private String insttNm;
	private String dataName;
	private String othbcDataNm;
	private String ChargerMoblphonNo;
	private String Email;
	private Long UserId;
	
	private String tkoutSvrIpAdres;
	private String tkoutSvrUserId;
	private String tkoutSvrUserPassword;
	
	
	private String exportReqStatCodeName;
	private List<ExportApproverInfoDto> exportApproverInfo = new ArrayList<>();
}
