package kr.re.amc.demand.dto;

import java.util.Date;

import kr.re.amc.board.dto.NoticeDto;
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
public class DemandCostDto {

	private String ncloudId;
	
	private String insttNm;
	private Long insttId;
	
	private String chargerNm;
	private String dataBoxName;
	private Long useAmount;
	private Long ncloudDscntAmount;
	private Long totRqestAdupAmount;
	private String rqestYm;
	private String monBet;
	private Long userSeq;
	private String demandMonth;
	private String loginId;
	private String demandAttributeCodeName;
	private int page;
	
	private int size;
}
