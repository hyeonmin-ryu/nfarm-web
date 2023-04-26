package kr.re.amc.demand.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.demand.dto.DemandCostDto;
import kr.re.amc.demand.dto.DemandSearchDto;
import kr.re.amc.demand.dto.ProductDemandCostDto;
import kr.re.amc.demand.dto.ProductDemandCostDto;
import kr.re.amc.demand.dto.ProductDemandCostPopDto;
import kr.re.amc.demand.service.DemandService;
import kr.re.amc.util.ExcelDownLoadUtil;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class DemandRestCtrl {

	@Resource(name = "demandService")
	private DemandService demandService;
	
	@GetMapping(value = "/agency/demand/list")
    public ApiResult<PageInfo<DemandCostDto>> getAgencyDemandList(
    		 DemandSearchDto demandSearchDto) {

		PageHelper.startPage(demandSearchDto.getPage(), demandSearchDto.getSize());
        List<DemandCostDto> pageDemandList =
        		demandService.getAgencyDemandList(demandSearchDto);
        PageInfo<DemandCostDto> pageData = new PageInfo<>(pageDemandList);
        
        return success(pageData);
    }
	
	
	@GetMapping(value = "/agency/demand/chart")
    public ApiResult<List<DemandCostDto>> getAgencyDemandChart(
    		DemandSearchDto demandSearchDto
            ) {

    	List<DemandCostDto> listDemandList =
        		demandService.getAgencyDemandChart(demandSearchDto);
        
        return success(listDemandList);
    }
	
	
	@GetMapping(value = "/agency/demand/view")
    public ApiResult<PageInfo<DemandCostDto>> getAgencyDemandView(
    		DemandSearchDto demandSearchDto){

		PageHelper.startPage(demandSearchDto.getPage(), demandSearchDto.getSize());
        List<DemandCostDto> pageDemandList = demandService.getAgencyDemandView(demandSearchDto);
        PageInfo<DemandCostDto> pageData = new PageInfo<>(pageDemandList);
        
        return success(pageData);
    }
	
	@GetMapping(value = "/agency/demand/pop")
    public ApiResult<ProductDemandCostPopDto> getAgencyDemandPop( DemandSearchDto demandSearchDto
    		) {

		
    	ProductDemandCostPopDto pageDemandList = demandService.getAgencyDemandPop(demandSearchDto);

        return success(pageDemandList);
    }
	@GetMapping(value = "/agency/demand/excel/list")
    public void getExcelDownload(
    		DemandSearchDto demandSearchDto, HttpServletResponse response) {

		ArrayList<ProductDemandCostDto> getAgencyDemandListExcel =
    			demandService.getAgencyDemandListExcel(demandSearchDto);
    	
		// 엑셀 헤더 정보 구성
		String[] cellHeader = {"네이버클라우드계정", "기관명", "사용자명", "데이터박스명" , "이용기간" , "이용금액" , "네이버클라우드제공할인" , "공급가액"};
    	
        ExcelDownLoadUtil exclUtil = new ExcelDownLoadUtil();
        exclUtil.gridToExcel( getAgencyDemandListExcel, cellHeader , demandSearchDto, response);
        
    }        
	
	
	@GetMapping(value = "/agency/demand/excel")
    public void getAgencyDemandExcel(
    		DemandSearchDto demandSearchDto,
            HttpServletResponse response){

    	ProductDemandCostPopDto productDemandCostPopDto = demandService.getAgencyDemandExcel(demandSearchDto);
        ExcelDownLoadUtil exclUtil = new ExcelDownLoadUtil();
        exclUtil.XLSXWriter(productDemandCostPopDto,demandSearchDto , response);
    }
	
}
