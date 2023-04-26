package kr.re.amc.demand.service;

import java.util.ArrayList;
import java.util.List;

import kr.re.amc.demand.dto.DemandCostDto;
import kr.re.amc.demand.dto.DemandSearchDto;
import kr.re.amc.demand.dto.ProductDemandCostDto;
import kr.re.amc.demand.dto.ProductDemandCostPopDto;

public interface DemandAdminService {

	List <DemandCostDto> getAgencyDemandList(DemandSearchDto demandSearchDto);
	
	List <DemandCostDto> getAdminDemandChart(DemandSearchDto demandSearchDto);
	
	List <DemandCostDto> getAdminDemandView(DemandSearchDto demandSearchDto);
	
	ProductDemandCostPopDto getAdminDemandPop(DemandSearchDto demandSearchDto);
	
	ProductDemandCostPopDto getAdminDemandExcel(DemandSearchDto demandSearchDto);
	
	ArrayList<ProductDemandCostDto> getAgencyDemandListExcel(DemandSearchDto demandSearchDto);

}
