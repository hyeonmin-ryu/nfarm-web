package kr.re.amc.export.dto;

import static org.springframework.beans.BeanUtils.copyProperties;

import kr.re.amc.databox.dto.DtbxReqMstDto;
import kr.re.amc.users.dto.AgencyDto;
import kr.re.amc.users.dto.UserInfoDto;
import lombok.Data;

@Data
public class ExportApproverInfoDto {

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
	private String tkoutProcessSttusCdNm;
	
	/*private String tkoutAgreYn;
	private String tkoutAgreDt;
	private Integer tkoutDataCo;
	private Long tkoutDataSize;
	private String tkoutDataTy;
	*/
	private Long creatUserId;
	private String creatDt;
	private Long updtUserId;
	private String updtDt;
	
	private Long tkoutConfmSn;
	private String confmResn;
	private Long tkoutConfmUserId;
	private String dtbxReqNo;
	private String dtbxNm;
	private String chargerNm;
	private String insttNm;
	private Long insttId;
	private String dataName;
	private Long dissRspnberUserId;
	private String othbcDataNm;
	
	private String email;
	
	private Integer idx;
	private String lastPang;
}