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
    private String exmnInstNm;     //조사기관명
    private String crtUsrId;       //생성사용자 아이디
    private String crtDt;          //생성일시
    private String chgUsrId;       //변경사용자 아이디
    private String chgDt;          //변경일시
	
}
