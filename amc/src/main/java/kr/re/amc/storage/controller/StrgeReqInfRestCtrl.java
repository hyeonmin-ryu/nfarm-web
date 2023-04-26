package kr.re.amc.storage.controller;


import static kr.re.amc.utils.ApiUtils.success;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;

import kr.re.amc.storage.dto.DtbxBucketMstDto;
import kr.re.amc.storage.dto.StrgeReqInfDto;
import kr.re.amc.storage.dto.StorageSearchDto;
import kr.re.amc.storage.service.StorageService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class StrgeReqInfRestCtrl {
	
	@Resource(name="storageService")
	private StorageService storageService;
	
	
	/**
	 * 데이터 저장 신청 목록
	 * 
	 * @param searchDto
	 * @return pageData
	 */
	@GetMapping(value="/storage/req/list")
	public ApiResult<PageInfo<StrgeReqInfDto>> inqReqStorageList(StorageSearchDto storageSearchDto){
		
		List<StrgeReqInfDto> ReqStorages = storageService.inqReqStorageList(storageSearchDto);
		PageInfo<StrgeReqInfDto> pageData = new PageInfo<>(ReqStorages);
		
		return success(pageData);
	}
	
	
    /**
     * 데이터 저장 신청
     * @methodName : reqStorage
     * @date : 2021-06-24 오후 2:33
     * @author : xeroman.k
     * @param strgeReqInfDto
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.strgeReqInfDto>
     * @throws
     * @modifyed :
     *
    **/
    @Secured(value = {"ROLE_MANAGER"})
    @PostMapping(value = "/storage/req")
    public ApiResult<String> reqStorage(@RequestBody @Valid StrgeReqInfDto strgeReqInfDto){
    	storageService.regReqStorage(strgeReqInfDto);
        return success("ok");
    }	
    
    /**
     * 마이페이지 > 스토리지저장신청목록 > 상세
     * @methodName : getMyReqStorageInfo
     * @date : 2021-06-25 오후 1:59
     * @author : xeroman.k
     * @param storgeReqstId
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.strgeReqInfDto>
     * @throws
     * @modifyed :
     *
    **/
    @Secured(value = {"ROLE_MANAGER", "ROLE_ADMIN", "ROLE_UPLOADER"})
    @GetMapping(value = "/my/management/storage/req/{storgeReqstId:.+(?<!\\.js)$}")
    public ApiResult<StrgeReqInfDto> getMyReqStorageInfo(
            @PathVariable String storgeReqstId){
        return success(storageService.inqStorageSeq(storgeReqstId));
    }
    
    
    /**
    *
    * @methodName : approveReqStorage
    * @date : 2021-06-28 오전 11:28
    * @author : xeroman.k
    * @param storgeReqstId
    * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.strgeReqInfDto>
    * @throws
    * @modifyed :
    *
   **/
   @Secured(value = {"ROLE_ADMIN"})
   @PostMapping(value = "/management/storage/req/approve/{storgeReqstId:.+(?<!\\.js)$}")
   public ApiResult<String> approveReqStorage(
           @PathVariable String storgeReqstId, @RequestBody DtbxBucketMstDto dtbxBucketMstDto) throws MessagingException, IOException {

	   storageService.approveReqStorageInfo(storgeReqstId, dtbxBucketMstDto);
       return success("ok");
   }    
   
   
   /**
   *
   * @methodName : rejectReqStorage
   * @date : 2021-07-08 오전 10:46
   * @author : xeroman.k
   * @param storgeReqstId
   * @param strgeReqInfDto
   * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.strgeReqInfDto>
   * @throws
   * @modifyed :
   *
  **/
  @Secured(value = {"ROLE_ADMIN"})
  @PostMapping(value = "/management/storage/req/reject/{storgeReqstId:.+(?<!\\.js)$}")
  public ApiResult<String> rejectReqStorage(
          @PathVariable String storgeReqstId,
          @RequestBody StrgeReqInfDto strgeReqInfDto) throws MessagingException, IOException {
	  	storageService.rejectReqStorageInfo(storgeReqstId, strgeReqInfDto);
      return success("ok");
  }
  
  /**
  *
  * @methodName : cancelReqStorage
  * @date : 2021-06-25 오후 3:09
  * @author : xeroman.k
  * @param loginUserInfo
  * @param storgeReqstId
  * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.strgeReqInfDto>
  * @throws
  * @modifyed :
  *
 **/
 @Secured(value = {"ROLE_MANAGER"})
 @PostMapping(value = "/my/management/storage/req/cancel/{storgeReqstId:.+(?<!\\.js)$}")
 public ApiResult<String> cancelReqStorage(
         @PathVariable String storgeReqstId
         ){
	 storageService.cancelReqStorageInfo( storgeReqstId);
     return success("ok");
 }  
	 
	 /**
	 *
	 * @methodName : deleteReqStorage
	 * @date : 2021-06-29 오후 4:32
	 * @author : xeroman.k
	 * @param loginUserInfo
	 * @param storgeReqstId
	 * @param strgeReqInfDto
	 * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.strgeReqInfDto>
	 * @throws
	 * @modifyed :
	 *
	**/
	@Secured(value = {"ROLE_MANAGER"})
	@PostMapping(value = "/my/management/storage/req/delete/{storgeReqstId:.+(?<!\\.js)$}")
	public ApiResult<String> deleteReqStorage(
	        @PathVariable String storgeReqstId,
	        @RequestBody StrgeReqInfDto strgeReqInfDto
	){
		
		
		 storageService.deleteReqStorageInfo(storgeReqstId, strgeReqInfDto);
	     return success("ok");
	} 
	
  /**
   * 마이페이지 > 스토리지저장신청목록
   * @param loginUserInfo
   * @param reqStorageStatCode
   * @param dataName
   * @param pageable
   * @return
   */
  @GetMapping(value = "/my/management/storage/req/list")
  public ApiResult<PageInfo<StrgeReqInfDto>> getMyReqStorageList(StorageSearchDto storageSearchDto){

		storageSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
		List<StrgeReqInfDto> ReqStorages = storageService.inqReqStorageList(storageSearchDto);
		PageInfo<StrgeReqInfDto> pageData = new PageInfo<>(ReqStorages);

      return success(pageData);
  }  
  
  
  /**
  *
  * @methodName : deleteReqStorage
  * @date : 2021-07-08 오전 10:46
  * @author : xeroman.k
  * @param storgeReqstId
  * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.strgeReqInfDto>
  * @throws
  * @modifyed :
  *
 **/
	@Secured(value = { "ROLE_ADMIN" })
	@PostMapping(value = "/management/storage/req/delete/{storgeReqstId:.+(?<!\\.js)$}")
	public ApiResult<String> deleteReqStorage(@PathVariable String storgeReqstId)
			throws MessagingException, IOException {
		storageService.deleteReqStorageInfo(storgeReqstId);
		return success("ok");
	}

	
	   /**
	   *
	   * @methodName : getReqStorageAuthList
	   * @date : 2021-10-12 오전 11:18
	   * @author : xeroman.k
	   * @param storgeReqstId
	   * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<java.util.List<com.itsm.dranswer.users.UserInfoDto>>
	   * @throws
	   * @modifyed :
	   *
	  **/
	  @GetMapping(value = "/storage/req/auth/list/{storgeReqstId:.+(?<!\\.js)$}")
	  public ApiResult<List<StrgeReqInfDto>> getReqStorageAuthList(@PathVariable String storgeReqstId){
	      return success(storageService.getReqStorageAuthList(storgeReqstId));
	  }	
}
