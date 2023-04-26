package kr.re.amc.users.dto;

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
public class AgencyDto {
	
	private Long insttId;
	
	private String insttNm;
	private String insttTyCd;
	private String bsnmNo;
	private String rprsntvNm;
	
}
