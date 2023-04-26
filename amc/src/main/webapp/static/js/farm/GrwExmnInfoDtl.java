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
public class GrwExmnInfoDtl {

    public GrwExmnInfoDtl(GrwExmnInfoDtl grwExmnInfoDtl) {

        this.farmId = null;
        this.grwStepCd = null;
        this.grwStepCd=grwStepCd;
        this.farmId = farmId;
    }
    private String grwExmnYr;       //생육조사연도
    private String farmId;          //농장아이디
    private String crpCd;           //작물코드
    private String grwStepCd;       //생육단계코드
    private String paveLotno;       //포장지번
    private String rcplPerdDivCd;   //이앙시기구분코드
    private String rcplYmd;         //이앙일자
    private String zoneReptNmtm;    //구역반복횟수
    private String exmnReptNmtm;    //조사반복횟수
    private String riceHght;        //벼높이(초장)
    private String tllgCnt;         //분열수
    private String aiTxtDat;        //AI텍스트 데이터
    private String atchFileUuid;    //첨부파일UUID
    private String crtUserId;       //생성사용자아이디
    private String crtDt;           //생성일시
    private String chgUserId;       //변경사용자아이디
    private String chgDt;           //변경일시



}