package kr.re.amc.demand.service;

import java.util.ArrayList;
import java.util.List;

import kr.re.amc.demand.dto.DemandCostDto;
import kr.re.amc.demand.dto.DemandSearchDto;
import kr.re.amc.demand.dto.ProductDemandCostDto;
import kr.re.amc.demand.dto.ProductDemandCostPopDto;

public interface DemandService {

	List <DemandCostDto> getAgencyDemandList (DemandSearchDto demandSearchDto);
	
	List<DemandCostDto> getAgencyDemandChart(DemandSearchDto demandSearchDto);
	
	List <DemandCostDto> getAgencyDemandView(DemandSearchDto demandSearchDto);
	
	ProductDemandCostPopDto getAgencyDemandPop(DemandSearchDto demandSearchDto);
	
	ProductDemandCostPopDto getAgencyDemandExcel(DemandSearchDto demandSearchDto);
	
	ArrayList<ProductDemandCostDto> getAgencyDemandListExcel(DemandSearchDto demandSearchDto) ;
}
