package kr.re.amc.databoxFrame.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import kr.re.amc.databoxFrame.dto.DataBoxFrameSearchDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeCreatInfDto;
import kr.re.amc.databoxFrame.service.DataBoxFrameService;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class DataBoxFrameRestCtrl {

	@Resource(name= "dataBoxFrameService")
	private DataBoxFrameService dataBoxFrameService;


    /**
     * 데이터 저장 신청 목록
     * @methodName : getReqStorageList
     * @date : 2021-06-24 오후 2:33
     * @author : xeroman.k
     * @param reqStorageStatCode
     * @param dataName
     * @param pageable
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<org.springframework.data.domain.Page<com.itsm.dranswer.storage.ReqStorageInfoDto>>
     * @throws
     * @modifyed :
     *
    **/
    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/my/management/databoxFrame/list")
    public ApiResult<PageInfo<DtbxFrmeCreatInfDto>> getDataBoxList(DataBoxFrameSearchDto dataBoxFrameSearchDto ){

    	List<DtbxFrmeCreatInfDto> pageDataBoxFrmList = dataBoxFrameService.inqDataBoxFrameList(dataBoxFrameSearchDto);
    	
    	PageInfo<DtbxFrmeCreatInfDto> pageData = new PageInfo<>(pageDataBoxFrmList);

        return success(pageData);
    }
    
    @Secured(value = {"ROLE_ADMIN"})
  	@PostMapping(value = "/my/management/databoxFrame/reg")
  	public ApiResult<DtbxFrmeCreatInfDto> reqDataBoxFrame(@RequestBody DtbxFrmeCreatInfDto dtbxFrmeCreatInfDto) throws Exception {
  		
  		return success(dataBoxFrameService.reqDataBoxFrame(dtbxFrmeCreatInfDto));
  	}	
    
    
	/**
     * 상세정보
     * @methodName : myInfo
     * @date : 2021-07-06 오후 5:44
     * @author : xeroman.k
     * @param dataBoxReqSeq
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.users.UserInfoDto>
     * @throws
     * @modifyed :
     *
    **/
    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping(value = "/my/management/databoxFrame/req/{dtbxFrmeNo:.+(?<!\\.js)$}")
    public ApiResult<DtbxFrmeCreatInfDto> dataBoxReq(@PathVariable Long dtbxFrmeNo){
        return success(dataBoxFrameService.getDataBoxFrameDetailInfo(dtbxFrmeNo));
    }	
    
    
    
    @Secured(value = {"ROLE_ADMIN"})
	@PostMapping(value = "/my/management/databoxFrame/updt")
	public ApiResult<DtbxFrmeCreatInfDto> updtDataBoxFrame(@RequestBody DtbxFrmeCreatInfDto dtbxFrmeCreatInfDto) throws Exception {
		
		return success(dataBoxFrameService.updtDataBoxFrame(dtbxFrmeCreatInfDto));
	}	    
    
    
	
	
}
