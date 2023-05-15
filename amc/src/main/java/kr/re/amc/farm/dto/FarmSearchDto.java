package kr.re.amc.farm.dto;

import java.io.Serializable;
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
@SuppressWarnings("serial")
public class FarmSearchDto implements Serializable{
		
	private int page;
	
	private int size;
	
    private String farmNm;         //농장명
    private String exmnInstNm;     //조사기관명
    private String exmnInstId;     //조사기관ID
    private String picNm;          //담당자이름
    private String usrId;          //담당자이름
    private String usrNm;          //담당자이름
    

}