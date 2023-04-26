package kr.re.amc.users.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class UserDetailDto implements UserDetails {

	private static final long serialVersionUID = 1265626978781829476L;
	
	private Long userId;
	private String email;
    private String loginPassword;
    private String userRoleSecd;
    private String chargerMoblphonNo;
    private String dissRspnberYn;
    private String ncloudId;
    private String ncloudObjStrgeId;
    private String ncloudAccesKey;
    private String ncloudScrtyKey;
    private String sbscrbSttusCd;
    private Integer insttId;
    private String dissCd;
    private Long upperUserId;
	
    
    private String agencyInfo;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getUsername() {
		return null;
	}


	@Override
	public String getPassword() {
		return loginPassword;
	}

	public UserInfoDto convertDto() {
        return new UserInfoDto(this);
    }

}
