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
public class GrwExmnInfoMst {

    public GrwExmnInfoMst(GrwExmnInfoMst grwExmnInfoMst) {
		this.farmId = null;
		this.grwStepCd=grwStepCd;
	}
    private String grwExmnYr;   // 생육조사연도
    private String farmId;      // 농장아이디
    private String crpCd;       // 작물코드
    private String grwStepCd;   // 생육단계코드
    private String paveLotNo;   // 포장지번
    private String grwExmnNm;   // 생육조사명
    private String crtUserId;   // 생성사용자아이디
    private String crtDt;       // 생성일시
    private String chgUserId;   // 변경사용자아이디
    private String chgDt;       // 변경일시


}
