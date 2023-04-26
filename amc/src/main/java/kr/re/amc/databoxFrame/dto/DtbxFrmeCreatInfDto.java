package kr.re.amc.databoxFrame.dto;


import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DtbxFrmeCreatInfDto  {
    
    private Long   dtbxFrmeNo;              //dataBoxFrameSeq;               
    private String dtbxFrmeNm;              //dataBoxFrameName;              
    private String dtbxFrmeCn;               //dataBoxFrameContents;          
    private Long   myAdmSvrCo;              //myAdminServerCnt;              
    private Long   cmnuseNasCo;             //publicNasCnt;                  
    private String tkoutObjStrgeId;          //exportObjectStorageId;         
    private String tkoutObjStrgeAccesKey;    //exportObjectStorageAccessKey;  
    private String tkoutObjStrgeScrtyKey;    //exportObjectStorageSecurityKey;
    private String dtbxFrmeSttusCd;          //dataBoxFrameStatus;            
    
    
	private Long creatUserId;
	private String creatDt;
	private Long updtUserId;
	private String updtDt;
    
    private List<DtbxFrmeAdmSvrAcntInfDto> dataBoxFrameAccountList = new ArrayList<>();
    private List<DtbxFrmeAdmSvrInfDto> dataBoxFrameAdminServerList = new ArrayList<>();
    private List<DtbxFrmeCmnuseDataInfDto> dataBoxFramePublicStorageList = new ArrayList<>();
    
    private DtbxFrmeAdmSvrAcntInfDto dataBoxFrameAccountInf;
    private DtbxFrmeAdmSvrInfDto dataBoxFrameAdminServerInf;
    private DtbxFrmeCmnuseDataInfDto dataBoxFramePublicStorage;
    
}
