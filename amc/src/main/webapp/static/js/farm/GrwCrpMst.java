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
public class GrwCrpMst {



    public GrwCrpMst(GrwCrpMst grwCrpMst) {
        this.farmId = null;
        this.crpCd=crpCd;
        this.farmId=farmId;
    }
    private String farmId;     //농장아이디
    private String crpCd;      //작물코드
    private String crpMngYn;   //Y
    private String grpMngYmd;  //작물관리중지일자
    private String crtUsrId;   //생성사용자 아이디
    private String crtDt;      //생성일시
    private String chgUsrId;   //변경사용자 아이디
    private String chgDt;      //변경일시

}