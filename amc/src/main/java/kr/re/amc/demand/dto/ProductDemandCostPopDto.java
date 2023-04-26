package kr.re.amc.demand.dto;

import java.util.ArrayList;
import java.util.List;

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
public class ProductDemandCostPopDto {
	
	private String supOrgName;
	private String supOrgCeo;
	private String supOrgBlNumber;
	
	private String demOrgName;
	private String demOrgCeo;
	private String demOrgBlNumber;
	
	private List<ProductDemandCostDto> productDemandCostList = new ArrayList<ProductDemandCostDto>();

}
