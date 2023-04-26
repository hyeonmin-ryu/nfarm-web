package kr.re.amc.users.dto;

/*
 * @package : com.itsm.dranswer.users
 * @name : UserInfoDto.java
 * @date : 2021-06-07 오전 11:02
 * @author : xeroman.k
 * @version : 1.0.0
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
public class UserInfoDto {

    public UserInfoDto(UserDetailDto userDetailDto) {
		this.userId = null;
		this.email=email;
		
	}
    
    private Long userId;
    private Long insttId;
    private String dissCd;
    private String dissNm;
    private String dissMainYn;
    
    private String dissRspnberYn;
    private String sbscrbSttusCd;
    private String lastLoginDt;
    private Integer loginCo;
    private String ncloudAccesKey;
    private String ncloudId;
    private String ncloudScrtyKey;
    private Long upperUserId;
    private String email;
    private String chargerNm;
    private String chargerMoblphonNo;
    private String userRoleSecd;
    private String ncloudObjStrgeId;
    private String scdCrtfcMoblphonNo;
    private Long creatUserId;
    private String creatDt;
    private String creatAppId;
    private Long updtUserId;
    private String updtDt;
    private String updtAppId;
    private String dissRspnberUserNm;
    
    private String rprsntvNm;
	private String bsnmNO;
	private String insttNm;
    private String insttTyCd;
    
    private String inputOldPw;
    private String inputNewPw;
    
    private Long managerUserSeq;
    private String managerUserName;
    

}
