package kr.re.amc.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserInfo {
	private Long userSeq;
	
	private String userEmail;
	
	private String userName;
	
	String [] roles;
	
	
}
