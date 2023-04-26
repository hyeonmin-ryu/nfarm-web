package kr.re.amc.demand.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDemandCostDto {
	 private String loginId;
	 private String ncloudId;
	 private String insttNm;
	 private String chargerNm;
	 private String dtbxNm;
	 private Long ncloudDscntAmount;
	 private Long totRqestAdupAmount;
	    private String demandMonth;
	    private Long promiseDiscountAmount;
	    private Long promotionDiscountAmount;
	    private Long etcDiscountAmount;
	    private Long productDiscountAmount;
	    private Long creditDiscountAmount;
	    private Long defaultAmount;
	    private Long useAmount;
	    private Long demandAmount;
	    private Date writeDate;
	    private Long memberPriceDiscountAmount;
	    private Long memberPromiseDiscountAddAmount;

	    private String rqestYm;
	    private String demandAttributeCodeName;
	    private Long thisMonthAmount;
	    private String monbet;
}
