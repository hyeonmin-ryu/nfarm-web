package kr.re.amc.users.dto;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;


public class JoinRequest {

    @Email(message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일을 작성해 주세요.")
    private String email;

    @NotBlank(message = "패스워드를 입력하세요.")
    private String inputPw;

    // 권한 Role.java 참고
    private String userRoleSecd;

    // 기관번호
    private Integer insttId;

    // 연구분석데이터코드 Disease.java 참고
    private String dissCd;

    @Size(message = "사용자 이름의 최대 길이를 초과 하였습니다.(max:20)", max = 20)
    @NotBlank(message = "사용자 이름을 작성해 주세요.")
    private String chargerNm;

    private String chargerMoblphonNo;

    // 연구분석 책임자 여부
    private String dissRspnberYn;

    // 네이버클라우드 ID
    private String ncloudId;

    // 네이버클라우드 오브젝트 스토리지 ID
    private String ncloudObjStrgeId;

    // 네이버클라우드AccessKey
    private String ncloudAccesKey;

    // 네이버클라우드SecretKey
    private String ncloudScrtyKey;

    // 가입상태코드
    private String sbscrbSttusCd;

    // 상위회원번호
    private Long upperUserId;

    private Long LOGIN_CO;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInputPw() {
		return inputPw;
	}

	public void setInputPw(String inputPw) {
		this.inputPw = inputPw;
	}

	public String getUserRoleSecd() {
		return userRoleSecd;
	}

	public void setUserRoleSecd(String userRoleSecd) {
		this.userRoleSecd = userRoleSecd;
	}

	public Integer getInsttId() {
		return insttId;
	}

	public void setInsttId(Integer insttId) {
		this.insttId = insttId;
	}

	public String getDissCd() {
		return dissCd;
	}

	public void setDissCd(String dissCd) {
		this.dissCd = dissCd;
	}

	public String getChargerNm() {
		return chargerNm;
	}

	public void setChargerNm(String chargerNm) {
		this.chargerNm = chargerNm;
	}

	public String getChargerMoblphonNo() {
		return chargerMoblphonNo;
	}

	public void setChargerMoblphonNo(String chargerMoblphonNo) {
		this.chargerMoblphonNo = chargerMoblphonNo;
	}

	public String getDissRspnberYn() {
		return dissRspnberYn;
	}

	public void setDissRspnberYn(String dissRspnberYn) {
		this.dissRspnberYn = dissRspnberYn;
	}

	public String getNcloudId() {
		return ncloudId;
	}

	public void setNcloudId(String ncloudId) {
		this.ncloudId = ncloudId;
	}

	public String getNcloudObjStrgeId() {
		return ncloudObjStrgeId;
	}

	public void setNcloudObjStrgeId(String ncloudObjStrgeId) {
		this.ncloudObjStrgeId = ncloudObjStrgeId;
	}

	public String getNcloudAccesKey() {
		return ncloudAccesKey;
	}

	public void setNcloudAccesKey(String ncloudAccesKey) {
		this.ncloudAccesKey = ncloudAccesKey;
	}

	public String getNcloudScrtyKey() {
		return ncloudScrtyKey;
	}

	public void setNcloudScrtyKey(String ncloudScrtyKey) {
		this.ncloudScrtyKey = ncloudScrtyKey;
	}

	public String getSbscrbSttusCd() {
		return sbscrbSttusCd;
	}

	public void setSbscrbSttusCd(String sbscrbSttusCd) {
		this.sbscrbSttusCd = sbscrbSttusCd;
	}

	public Long getUpperUserId () {
		return upperUserId;
	}

	public void setUpperUserId(Long upperUserId) {
		this.upperUserId = upperUserId;
	}

	public Long getLoginCo() {
		return LOGIN_CO;
	}

	public void setLoginCo(Long LOGIN_CO) {
		this.LOGIN_CO = LOGIN_CO;
	}
    
    
}
