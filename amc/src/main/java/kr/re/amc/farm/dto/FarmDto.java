package kr.re.amc.farm.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuppressWarnings("serial")
public class FarmDto implements Serializable {

	private int page;
	
	private int size;

	Long userId;
	
    private String farmId;         //농장아이디
    private String farmNm;         //농장명
    private String fmhsNo;         //농가번호
    private String fmhsAddr;       //농가주소(AES256암호화)
    private String frmrNm;         //농장주명(AES256암호화)
    private String frmrTelno;      //농장주전화번호(AES256암호화)
    private String exmnInstMngrId; //조사기관아이디
    

    private String exmnInstId;	   //조사기관아이디
    private String exmnInstNm;     //조사기관명
    private String exmnInstDesc;   //조사기관설명
    private String picNm;		   //담당자명
    private String picTelno;	   //담당자전화번호
    private String mngInstYn;	   //관리기관여부

    private String userTotalCount; //조사기관총인원
    
    private String usrId;		   //사용자ID
    private String usrNm;		   //사용자이름 
    private String emailAddr;	   //이메일주소 
    private String usrTelno;	   //사용자전화번호 
    private String lastCntnDt;	   //최종접속일시 
    private String tmprPswdYn;	   //임시비밀번호여부 
    private String useStopYn;	   //사용중지여부 
    private String useStopYmd;	   //사용중지일자 

    
    private String crtUsrId;       //생성사용자 아이디
    private String crtDt;          //생성일시
    private String chgUsrId;       //변경사용자 아이디
    private String chgDt;          //변경일시

    private String grwStepNm;          //변경일시
    private String grwExmnNm;          //변경일시

}
