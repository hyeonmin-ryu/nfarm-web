package kr.re.amc.demand.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.demand.dto.DemandCostDto;
import kr.re.amc.demand.dto.DemandSearchDto;
import kr.re.amc.demand.dto.ProductDemandCostDto;

@Repository("demandDao")
public class DemandDao extends EgovComAbstractDAO{

	public List<DemandCostDto> inqAgencyDemandList(DemandSearchDto demandSearchDto){
		return selectList("demandDao.inqAgencyDemandList", demandSearchDto);
	}
	
	public List<DemandCostDto> inqAgencyDemandChart(DemandSearchDto demandSearchDto){
		return selectList("demandDao.inqAgencyDemandChart", demandSearchDto);
	}
	
	public List<DemandCostDto> getAgencyDemandView(DemandSearchDto demandSearchDto){
		return selectList("demandDao.getAgencyDemandView", demandSearchDto);
	}
	
	public List<ProductDemandCostDto> getAgencyDemandPop(DemandSearchDto demandSearchDto){
		return selectList("demandDao.getAgencyDemandPop", demandSearchDto);
	}
	public List<ProductDemandCostDto> getAgencyDemandExcel(DemandSearchDto demandSearchDto){
		return selectList("demandDao.getAgencyDemandExcel", demandSearchDto);
	}
	
	public List<ProductDemandCostDto> demandAgencyListBatchExcel(DemandSearchDto demandSearchDto){
		return selectList("demandDao.demandAgencyListBatchExcel", demandSearchDto);
	}
	
	public List<DemandCostDto> demandAdminListBatch (DemandSearchDto demandSearchDto){
		return selectList("demandDao.demandAdminListBatch", demandSearchDto);
	}
	
	public List<DemandCostDto> getAdminDemandChart(DemandSearchDto demandSearchDto){
		return selectList ("demandDao.getAdminDemandChart", demandSearchDto);
	}
	public List<DemandCostDto> demandAdminListViewBatch(DemandSearchDto demandSearchDto){
		return selectList ("demandDao.demandAdminListViewBatch", demandSearchDto);
	}
	
	public List<ProductDemandCostDto> getAdminDemandPop(DemandSearchDto demandSearchDto){
		return selectList("demandDao.getAdminDemandPop", demandSearchDto);
	}
	
	public List<ProductDemandCostDto> getAdminDemandExcel (DemandSearchDto demandSearchDto){
		return selectList("demandDao.getAdminDemandExcel", demandSearchDto);
	}
	
	public List<ProductDemandCostDto> demandAdminListBatchExcel(DemandSearchDto demandSearchDto){
		return selectList("demandDao.demandAdminListBatchExcel", demandSearchDto);
	}
}
