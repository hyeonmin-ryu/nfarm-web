package kr.re.amc.board.dto;

/*
 * @package :
 * @name : 
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
public class GrwExmnDtl {

    public GrwExmnDtl(GrwExmnDtl grwExmnDtl) {
        this.farmId = null;
        this.grwStepCd=grwStepCd;
        this.farmId=farmId;
    }
    private String grwExmnYr;    //생육조사연도
    private String farmId;       //농장아이디
    private String crpCd;        //작물코드
    private String grwStepCd;    //생육단계코드
    private String paveLotno;    //포장지번
    private String rcplPerdDivCd;//이앙시기구분코드(공통코드)
    private String rcplYmd;      //이앙일자
    private String ppynPltgDnst; //평당재식밀도
    private String sdrCnt;       //주당재식본수
    private String rcplBoxCnt;   //이양상자수
    private String rcplArea;     //이양면적(평)
    private String tkcgExmnrId;  //담당조사자아이디
    private String cmptnYn;      //완료여부
    private String crtUsrId;     //생성사용자아이디
    private String crtDt;        //생성일시
    private String chgUsrId;     //변경사용자아이디
    private String chgDt;        //변경일시

}