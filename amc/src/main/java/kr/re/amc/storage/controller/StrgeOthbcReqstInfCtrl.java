package kr.re.amc.storage.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import kr.re.amc.storage.dto.StrgeOthbcReqstInfDto;
import kr.re.amc.storage.dto.StorageSearchDto;
import kr.re.amc.storage.service.StorageService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class StrgeOthbcReqstInfCtrl {

	@Resource(name="storageService")
	private StorageService storageService;
	
	
    /**
     * 데이터 공개 신청 목록
     * @methodName : getOpenStorageList
     * @date : 2021-07-06 오후 5:08
     * @author : xeroman.k
     * @param openStorageStatCode
     * @param openDataName
     * @param pageable
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<org.springframework.data.domain.Page<com.itsm.dranswer.storage.StrgeOthbcReqstInfDto>>
     * @throws
     * @modifyed :
     *
    **/
    @GetMapping(value = "/storage/open/list")
    public ApiResult<PageInfo<StrgeOthbcReqstInfDto>> inqOpenStorageList( StorageSearchDto storageSearchDto){

//		PageHelper.startPage(storageSearchDto.getPage() , storageSearchDto.getSize());
		
//    	storageSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
		List<StrgeOthbcReqstInfDto> inqOpenStorageList = storageService.inqOpenStorageList(storageSearchDto);
		PageInfo<StrgeOthbcReqstInfDto> pageData = new PageInfo<>(inqOpenStorageList);

        return success(pageData);
    }	
	
    
    /**
     * 마이페이지 > 스토리지공개신청목록
     * @methodName : getMyOpenStorageList
     * @date : 2021-07-06 오후 5:08
     * @author : xeroman.k
     * @param loginUserInfo
     * @param openStorageStatCode
     * @param openDataName
     * @param pageable
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<org.springframework.data.domain.Page<com.itsm.dranswer.storage.StrgeOthbcReqstInfDto>>
     * @throws
     * @modifyed :
     *
    **/
    @GetMapping(value = "/my/management/storage/open/list")
    public ApiResult<PageInfo<StrgeOthbcReqstInfDto>> getMyOpenStorageList( StorageSearchDto storageSearchDto){

//		PageHelper.startPage(storageSearchDto.getPage() , storageSearchDto.getSize());
		
		storageSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
		List<StrgeOthbcReqstInfDto> inqOpenStorageList = storageService.inqOpenStorageList(storageSearchDto);
		PageInfo<StrgeOthbcReqstInfDto> pageData = new PageInfo<>(inqOpenStorageList);

        return success(pageData);
    }    
    
    /**
     * 데이터 공개 신청
     * @methodName : openStorage
     * @date : 2021-07-06 오후 5:08
     * @author : xeroman.k
     * @param StrgeOthbcReqstInfDto
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.StrgeOthbcReqstInfDto>
     * @throws
     * @modifyed :
     *
    **/
    @Secured(value = {"ROLE_MANAGER"})
    @PostMapping(value = "/storage/open")
    public ApiResult<String> openStorage(@RequestBody StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto){
    	storageService.regOpenStorage(StrgeOthbcReqstInfDto);
        return success("ok");
    }    
	
    /**
     * 마이페이지 > 스토리지공개신청목록 > 상세
     * @methodName : getMyOpenStorageInfo
     * @date : 2021-07-06 오후 5:08
     * @author : xeroman.k
     * @param othbcReqstId
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.StrgeOthbcReqstInfDto>
     * @throws
     * @modifyed :
     *
    **/
    @Secured(value = {"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_UPLOADER"})
    @GetMapping(value = "/my/management/storage/open/{othbcReqstId:.+(?<!\\.js)$}")
    public ApiResult<StrgeOthbcReqstInfDto> getMyOpenStorageInfo(
            @PathVariable String othbcReqstId){

        StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto =
                storageService.getOpenStorageInfo(othbcReqstId);

        return success(StrgeOthbcReqstInfDto);
    }    
    
    
    /**
     * 승인처리 (공개승인 or 취소승인)
     * @methodName : approveOpenStorage
     * @date : 2021-07-06 오후 5:09
     * @author : xeroman.k
     * @param othbcReqstId
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.StrgeOthbcReqstInfDto>
     * @throws
     * @modifyed :
     *
    **/
    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping(value = "/management/storage/open/approve/{othbcReqstId:.+(?<!\\.js)$}")
    public ApiResult<String> approveOpenStorage(
            @PathVariable String othbcReqstId) throws MessagingException, IOException {
    	
        storageService.approveOpenStorageInfo(othbcReqstId);

        return success("OK");
    }    
    
    
    /**
     * 공개신청 승인거절
     * @methodName : rejectOpenStorage
     * @date : 2021-07-08 오전 10:32
     * @author : xeroman.k
     * @param othbcReqstId
     * @param StrgeOthbcReqstInfDto
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.StrgeOthbcReqstInfDto>
     * @throws
     * @modifyed :
     *
    **/
    @Secured(value = {"ROLE_ADMIN"})
    @PostMapping(value = "/management/storage/open/reject/{othbcReqstId:.+(?<!\\.js)$}")
    public ApiResult<String> rejectOpenStorage(
            @PathVariable String othbcReqstId,
            @RequestBody StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto) throws MessagingException, IOException {

        storageService.rejectOpenStorageInfo(othbcReqstId, StrgeOthbcReqstInfDto);

        return success("OK");
    }	
    
    /**
     * 공개취소신청
     * @methodName : cancelOpenStorage
     * @date : 2021-07-06 오후 5:08
     * @author : xeroman.k
     * @param loginUserInfo
     * @param othbcReqstId
     * @param StrgeOthbcReqstInfDto
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.StrgeOthbcReqstInfDto>
     * @throws
     * @modifyed :
     *
    **/
    @Secured(value = {"ROLE_MANAGER"})
    @PostMapping(value = "/my/management/storage/open/cancel/{othbcReqstId:.+(?<!\\.js)$}")
    public ApiResult<String> cancelOpenStorage(
            @PathVariable String othbcReqstId,
            @RequestBody StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto
    ){
    	storageService.cancelOpenStorageInfo(othbcReqstId, StrgeOthbcReqstInfDto);
        return success("ok");
    }   
    
	
}
