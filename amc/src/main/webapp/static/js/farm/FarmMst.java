package kr.re.amc.board.dto;
/*
 * @package :
 * @name : 작물 마스터
 * @date : 
 * @author : 
 * @version : 0.1
 * @modifyed :

 */

import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import static org.springframework.beans.BeanUtils.copyProperties;


@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class FarmMst {


    public FarmMst(FarmMst farmMst) {
        this.farmId = null;
        this.farmId=farmId;
        this.fmhsNo=fmhsNo;
    }
    private String farmId;         //농장아이디
    private String farmNm;         //농장명
    private String fmhsNo;         //농가번호
    private String fmhsAddr;       //농가주소(AES256암호화)
    private String frmrNm;         //농장주명(AES256암호화)
    private String frmrTelno;      //농장주전화번호(AES256암호화)
    private String exmnInstMngrId; //조사기관아이디
    private String crtUsrId;       //생성사용자 아이디
    private String crtDt;          //생성일시
    private String chgUsrId;       //변경사용자 아이디
    private String chgDt;          //변경일시

}