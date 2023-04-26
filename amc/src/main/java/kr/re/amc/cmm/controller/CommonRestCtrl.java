package kr.re.amc.cmm.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.re.amc.cmm.dto.CodeDto;
import kr.re.amc.cmm.dto.MenuDto;
import kr.re.amc.cmm.service.CommonService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class CommonRestCtrl {
	
	@Resource(name="commonService")
	private CommonService commonService;
	
	@GetMapping(value="/common/menu/list")
	public ApiResult<List<MenuDto>> getNoticeList(MenuDto menuDto){
		
		menuDto.setUserRoleSecd(AmcUtil.getRole());
		List<MenuDto> menulist = commonService.MenuList(menuDto);
		return success(menulist);
	}
	
	
    @GetMapping(value = "/get/code/{code:.+(?<!\\.js)$}")
    public ApiResult<List<CodeDto>> getCodeList(@PathVariable String code){

    	List<CodeDto> codes = new ArrayList<>();

        switch (code){
            case "DISS_CD":
                codes  = commonService.inqCodeList("DISS_CD");
                break;
            case "OTHBC_REQST_STTUSCD":
                codes = commonService.inqCodeList("OTHBC_REQST_STTUSCD");
                break;
            case "STRGE_REQ_STTUS_CD":
                codes = commonService.inqCodeList("STRGE_REQ_STTUS_CD");
                break;
            case "INSTT_TY_CD":
                codes = commonService.inqCodeList("INSTT_TY_CD");
                break;
            case "SBSCRB_STTUS_CD":
                codes = commonService.inqCodeList("SBSCRB_STTUS_CD");
                break;
            case "IsYn":
                codes = commonService.inqCodeList("IsYn");
                break;
            case "INQRY_TYCD":
                codes = commonService.inqCodeList("INQRY_TYCD");
                break;
            case "ApproveStatus":
                codes = commonService.inqCodeList("ApproveStatus");
                break;
            case "ProductType":
                codes = commonService.inqCodeList("ProductType");
                break;
            case "OsImageType":
                codes = commonService.inqCodeList("OsImageType");
                break;
            case "USE_REQST_STTUS_SECD":
                codes = commonService.inqCodeList("USE_REQST_STTUS_SECD");
                break;
            case "USERTYPE":
                codes = commonService.inqCodeList("USERTYPE");
                break;
            case "DataBoxReqStatCode":
                codes = commonService.inqCodeList("DataBoxReqStatCode");
                break;
            case "TKOUT_PROCESS_STTUS_CD":
                codes = commonService.inqCodeList("TKOUT_PROCESS_STTUS_CD");
                break;
            default:
            	codes=commonService.inqCodeList(code);
            	break;
        }
        return success(codes);
    }	

    @GetMapping(value = "/get/codegroup/list")
    public ApiResult<List<CodeDto>> getCodeGroupList(){
    	return success(commonService.inqCodeGroupList());
    }
    
    @PostMapping(value="/code/delete/{nm:.+(?<!\\\\.js)$}")
    public ApiResult<List<CodeDto>> delCode(@PathVariable String nm){
    	commonService.delCode(nm);
    	return success(null);
    }
    
    @PostMapping(value="/code/delete/codegroup/{codeGroup:.+(?<!\\\\\\\\.js)$}")
    public ApiResult<List<CodeDto>> delCodeGroup(@PathVariable String codeGroup){
    	commonService.delCodeGroup(codeGroup);
    	return success(null);
    }
    
    @PostMapping(value="/code/save/code")
    public ApiResult<CodeDto> regCode(@RequestBody CodeDto codeDto){
    	return success(commonService.regCode(codeDto));
    }
    
    @PostMapping(value="/code/save/codegroup")
    public ApiResult<CodeDto> regCodeGroup(@RequestBody CodeDto codeDto){
    	return success(commonService.regCodeGroup(codeDto));
    }
}
