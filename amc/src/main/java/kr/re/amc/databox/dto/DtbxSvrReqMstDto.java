package kr.re.amc.databox.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;


@Data
public class DtbxSvrReqMstDto {

	private Long dtbxReqNo;
	private Long dtbxSn;
	private Long svrSn;
	private String imageGoodsCd;
	private String imageGoodsNm;
	private String svrGoodsCd;
    private String svrGoodsNm;
	private int svrCo;
    
    private String svrIpAdres;
    private String svrUserId;
    private String svrUserPassword;
	
    private String creatDt;	
    private String updtDt;	
    
    private Long   creatUserId;
    private Long   updtUserId;

    private List<DtbxBlckStrgeReqMstDto> storageList = new ArrayList<>();
    
}
