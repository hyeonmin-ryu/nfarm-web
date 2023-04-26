package kr.re.amc.agency.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.agency.Service.AdminAgencyService;
import kr.re.amc.agency.dto.AgencySearchDto;
import kr.re.amc.users.dto.AgencyDto;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class AdminAgencyRestCtrl {
	
	@Resource(name="adminAgencyService")
	private  AdminAgencyService adminAgencyService;
	
	@GetMapping(value = "/mypage/agency/list")
	public ApiResult<PageInfo<AgencyDto>> inqAgencyList(AgencySearchDto agencySearchDto){
		
		PageHelper.startPage(agencySearchDto.getPage(), agencySearchDto.getSize());
		List<AgencyDto> agencyInfo = adminAgencyService.inqAgencyList(agencySearchDto);
		PageInfo<AgencyDto> pageData = new PageInfo<>(agencyInfo);
		
		return success(pageData);
	}
	
	@GetMapping(value="/mypage/agency/view/{insttId:.+(?<!\\\\.js)$}")
	public ApiResult<AgencyDto> getAgencySeq(@PathVariable Long insttId){
		return success(adminAgencyService.getAgencySeq(insttId));
	}
	
	@PostMapping(value="/mypage/agency/save")
	public ApiResult<AgencyDto> regAgency(@RequestBody AgencyDto agencyDto){

		adminAgencyService.regAgency(agencyDto);
		return success(adminAgencyService.getAgencySeq(agencyDto.getInsttId()));	
	}
	
	@PostMapping(value="/mypage/agency/delete")
	public ApiResult<AgencyDto> delAgency(@RequestBody AgencyDto agencyDto){
		adminAgencyService.delAgency(agencyDto);
		    
		return success(null);
	}
}
