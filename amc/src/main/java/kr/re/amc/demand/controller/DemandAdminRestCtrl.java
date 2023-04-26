package kr.re.amc.demand.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.commons.CustomMailSender;
import kr.re.amc.demand.dto.DemandCostDto;
import kr.re.amc.demand.dto.DemandSearchDto;
import kr.re.amc.demand.dto.ProductDemandCostDto;
import kr.re.amc.demand.dto.ProductDemandCostPopDto;
import kr.re.amc.demand.service.DemandAdminService;
import kr.re.amc.users.dto.UserInfoDto;
import kr.re.amc.users.service.UserService;
import kr.re.amc.util.ExcelDownLoadUtil;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class DemandAdminRestCtrl {

	@Resource(name = "demandAdminService")
	private DemandAdminService demandAdminService;
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Autowired
	private CustomMailSender customMailSender;
	  
	@GetMapping(value = "/admin/demand/list")
    public ApiResult<PageInfo<DemandCostDto>> getAgencyDemandList(
    		DemandSearchDto demandSearchDto ){
		PageHelper.startPage(demandSearchDto.getPage(), demandSearchDto.getSize());
        List<DemandCostDto> pageDemandList = demandAdminService.getAgencyDemandList(demandSearchDto);
        PageInfo<DemandCostDto> pageData = new PageInfo<>(pageDemandList);
        return success(pageData);
    }
	
	@GetMapping(value = "/admin/demand/chart")
    public ApiResult<List<DemandCostDto>> getAdminDemandChart(
    		DemandSearchDto demandSearchDto){

    	List<DemandCostDto> listDemandList =
    			demandAdminService.getAdminDemandChart(demandSearchDto);
        
        return success(listDemandList);
    }
	
	@GetMapping(value = "/admin/demand/view")
    public ApiResult<PageInfo<DemandCostDto>> getAdminDemandView(
    		DemandSearchDto demandSearchDto) {
		PageHelper.startPage(demandSearchDto.getPage(), demandSearchDto.getSize());
        List<DemandCostDto> pageDemandList =
        		demandAdminService.getAdminDemandView(demandSearchDto);
        PageInfo<DemandCostDto> pageData = new PageInfo<>(pageDemandList);
        return success(pageData);
    }
	
	@GetMapping(value = "/admin/demand/pop")
    public ApiResult<ProductDemandCostPopDto> getAgencyDemandPop(
    		DemandSearchDto demandSearchDto){

		
    	ProductDemandCostPopDto pageDemandList = demandAdminService.getAdminDemandPop(demandSearchDto);

        return success(pageDemandList);
    }
	
	@GetMapping(value = "/admin/demand/excel")
    public void getAgencyDemandExcel(
    		DemandSearchDto demandSearchDto , HttpServletResponse response) {

    	ProductDemandCostPopDto productDemandCostPopDto = demandAdminService.getAdminDemandExcel( demandSearchDto);
        ExcelDownLoadUtil exclUtil = new ExcelDownLoadUtil();
        exclUtil.adminXLSXWriter(productDemandCostPopDto,demandSearchDto , response, "download");
    }
	
	@GetMapping(value = "/admin/demand/excel/list")
    public void getExcelDownload(
    		DemandSearchDto demandSearchDto , HttpServletResponse response) {

    	ArrayList<ProductDemandCostDto> getAgencyDemandListExcel =
        		demandAdminService.getAgencyDemandListExcel(demandSearchDto);
    	
		// 엑셀 헤더 정보 구성
		String[] cellHeader = {"네이버클라우드계정", "기관명", "사용자명", "데이터박스명" , "이용기간" , "이용금액" , "네이버클라우드제공할인" , "공급가액"};
    	
        ExcelDownLoadUtil exclUtil = new ExcelDownLoadUtil();
        exclUtil.gridToExcel( getAgencyDemandListExcel, cellHeader , demandSearchDto, response);
        
    }    
	
	
	@Secured(value = {"ROLE_ADMIN"})
    @PostMapping(value = "/user/demandCostListMailSend")
    public void demandCostListMailSend(
    		@RequestBody DemandSearchDto demandSearchDto,
            HttpServletResponse response) throws Exception {
    	UserInfoDto userinfoDto =  userService.getUserSeq(demandSearchDto.getUserId());

    	
    	ProductDemandCostPopDto productDemandCostPopDto = demandAdminService.getAdminDemandExcel( demandSearchDto/*demandSearchDto.getDemandMonth(), demandSearchDto.getLoginId(), demandSearchDto.getUserId()*/);
    	
        ExcelDownLoadUtil exclUtil = new ExcelDownLoadUtil();
        String excelFileName = exclUtil.adminXLSXWriter(productDemandCostPopDto, /*demandCostDto.getLoginId(), demandCostDto.getOrgName(), demandCostDto.getDemandMonth()*/ demandSearchDto, response, "sendmail");
        
        File file = new File(excelFileName);
        String email = userinfoDto.getEmail();
        String mailsubject = "[서울아산병원]계산서가 발행 되었습니다.";
        String title = "계산서 발행";
        String userName = demandSearchDto.getOrgName();
        String subject = "계산서 발행.";
        
        customMailSender.sendBillsMail(email, mailsubject, title, userName, subject, file, excelFileName, demandSearchDto.getMailText());
        file.delete();

    }
}
