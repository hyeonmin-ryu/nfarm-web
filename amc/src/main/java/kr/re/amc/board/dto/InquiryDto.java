package kr.re.amc.board.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class InquiryDto {
	private Long inqryNo;
	private String answerYn;
	private String inqryCn;
	private String inqryTycd;
	private Long orginlInqryNo;
	private String clsdrPassword;
	private String othbcYn;
	private String inqrySj;
	private Long creatUserId;
	private String creatDt;
	private String creatAppId;
	private Long updtUserId;
	private String updtDt;
	private String updtAppId;
	private String inquiryTypeCode;
	
	//
	private String chargerNm;

	private List<InquiryDto> children = new ArrayList<>();
	
	private List<InquiryFileDto> inquiryFiles= new ArrayList<>();
	//private String password;
		
	
}
