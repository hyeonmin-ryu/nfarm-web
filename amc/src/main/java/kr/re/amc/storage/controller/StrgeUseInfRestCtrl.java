package kr.re.amc.storage.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.storage.dto.StrgeOthbcReqstInfDto;
import kr.re.amc.storage.dto.StorageSearchDto;
import kr.re.amc.storage.dto.StrgeUseInfDto;
import kr.re.amc.storage.service.StorageService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class StrgeUseInfRestCtrl {

	@Resource(name="storageService")
	private StorageService storageService;
	
   
    /**
    *
    * @methodName : getUseStorageList
    * @date : 2021-10-08 오후 2:32
    * @author : xeroman.k
    * @param disease
    * @param keyword
    * @param pageable
    * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<org.springframework.data.domain.Page<com.itsm.dranswer.storage.StrgeOthbcReqstInfDto>>
    * @throws
    * @modifyed :
    *
   **/
   @GetMapping(value = "/storage/use/list")
   public ApiResult<PageInfo<StrgeOthbcReqstInfDto>> getUseStorageList(StorageSearchDto storageSearchDto ){

		PageHelper.startPage(storageSearchDto.getPage() , storageSearchDto.getSize());
		
		storageSearchDto.setOthbcReqstSttuscd("O_ACC");
		List<StrgeOthbcReqstInfDto> inqUseStorageList = storageService.inqUseStorageList(storageSearchDto);
		PageInfo<StrgeOthbcReqstInfDto> pageData = new PageInfo<>(inqUseStorageList);

       return success(pageData);
   }	
	
   
	/**
	 *
	 * @methodName : getUseStorageList
	 * @date : 2021-10-08 오후 2:32
	 * @author : xeroman.k
	 * @param disease
	 * @param keyword
	 * @param pageable
	 * @return :
	 *         com.itsm.dranswer.utils.ApiUtils.ApiResult<org.springframework.data.domain.Page<com.itsm.dranswer.storage.StrgeOthbcReqstInfDto>>
	 * @throws
	 * @modifyed :
	 *
	 **/
	@GetMapping(value = "/storage/use/req/{othbcReqstId:.+(?<!\\\\.js)$}")
	public ApiResult<StrgeOthbcReqstInfDto> getUseStorageInfo(@PathVariable String othbcReqstId) {
		StrgeOthbcReqstInfDto inqUseStorageList = storageService.getUseStorageInfo(othbcReqstId);

		return success(inqUseStorageList);
	}
  
  
	/**
	 *
	 * @methodName : reqUseStorage
	 * @date : 2021-10-08 오후 2:32
	 * @author : xeroman.k
	 * @param loginUserInfo
	 * @param reqUseStorageInfoDto
	 * @return :
	 *         com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.UseStorageInfoDto>
	 * @throws
	 * @modifyed :
	 *
	 **/
	@Secured(value = { "ROLE_USER" })
	@PostMapping(value = "/storage/use/req")
	public ApiResult<String> reqUseStorage(@RequestBody StrgeUseInfDto strgeUseInfDto) {

		storageService.regReqUseStorage(strgeUseInfDto);

		return success("ok");
	}
	
	
	   /**
    *
    * @methodName : getMyUseStorageList
    * @date : 2021-10-08 오후 2:32
    * @author : xeroman.k
    * @param loginUserInfo
    * @param useStorageStat
    * @param dataName
    * @param pageable
    * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<org.springframework.data.domain.Page<com.itsm.dranswer.storage.UseStorageInfoDto>>
    * @throws
    * @modifyed :
    *
   **/
   @Secured(value = {"ROLE_USER"})
   @GetMapping(value = "/my/management/storage/use/list")
   public ApiResult<PageInfo<StrgeUseInfDto>> getMyUseStorageList(StorageSearchDto storageSearchDto ){

		PageHelper.startPage(storageSearchDto.getPage() , storageSearchDto.getSize());
		
		storageSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
		List<StrgeUseInfDto> inqUseStorageList = storageService.inqMyUseStorageList(storageSearchDto);
		PageInfo<StrgeUseInfDto> pageData = new PageInfo<>(inqUseStorageList);

       return success(pageData);
   }

   @Secured(value = {"ROLE_MANAGER"})
   @GetMapping(value = "/mine/management/storage/use/list")
   public ApiResult<PageInfo<StrgeUseInfDto>> getMineUseStorageList(StorageSearchDto storageSearchDto ){

		PageHelper.startPage(storageSearchDto.getPage() , storageSearchDto.getSize());
		
		
		storageSearchDto.setDissCd(AmcUtil.getPrincipal().getUserId());
		List<StrgeUseInfDto> inqUseStorageList = storageService.inqMyUseStorageList(storageSearchDto);
		PageInfo<StrgeUseInfDto> pageData = new PageInfo<>(inqUseStorageList);

       return success(pageData);
   }

	/**
	 *
	 * @methodName : getUseStorageList
	 * @date : 2021-10-08 오후 2:32
	 * @author : xeroman.k
	 * @param useStorageStat
	 * @param dataName
	 * @param pageable
	 * @return :
	 *         com.itsm.dranswer.utils.ApiUtils.ApiResult<org.springframework.data.domain.Page<com.itsm.dranswer.storage.UseStorageInfoDto>>
	 * @throws
	 * @modifyed :
	 *
	 **/
	@Secured(value = { "ROLE_ADMIN", "ROLE_MANAGER" })
	@GetMapping(value = "/management/storage/use/list")
	public ApiResult<PageInfo<StrgeUseInfDto>> getMngUseStorageList(StorageSearchDto storageSearchDto) {

		PageHelper.startPage(storageSearchDto.getPage(), storageSearchDto.getSize());

		List<StrgeUseInfDto> inqUseStorageList = storageService.inqMyUseStorageList(storageSearchDto);
		PageInfo<StrgeUseInfDto> pageData = new PageInfo<>(inqUseStorageList);

		return success(pageData);
	}

	/**
	 *
	 * @methodName : getMyUseStorageInfo
	 * @date : 2021-10-08 오후 2:32
	 * @author : xeroman.k
	 * @param useStorageId
	 * @return :
	 *         com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.UseStorageInfoDto>
	 * @throws
	 * @modifyed :
	 *
	 **/
	@Secured(value = { "ROLE_USER", "ROLE_MANAGER", "ROLE_ADMIN" })
	@GetMapping(value = "/my/management/storage/use/{useStorageId:.+(?<!\\.js)$}")
	public ApiResult<StrgeUseInfDto> getMyUseStorageInfo(@PathVariable String useStorageId) {

		StrgeUseInfDto useStorageInfoDto = storageService.getMyUseStorageInfo(useStorageId);

		return success(useStorageInfoDto);
	}
	
	

	/**
	 *
	 * @methodName : approveUseStorageInfo
	 * @date : 2021-10-08 오후 2:32
	 * @author : xeroman.k
	 * @param useStorageId
	 * @return :
	 *         com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.UseStorageInfoDto>
	 * @throws
	 * @modifyed :
	 *
	 **/
	@Secured(value = { "ROLE_MANAGER" })
	@PostMapping(value = "/management/storage/use/approve/{useStorageId:.+(?<!\\.js)$}")
	public ApiResult<String> approveUseStorageInfo(@PathVariable String useStorageId) {

		storageService.approveUseStorage(useStorageId);

		return success("ok");
	}
	
    /**
    *
    * @methodName : cancelUseStorageInfo
    * @date : 2021-10-08 오후 2:32
    * @author : xeroman.k
    * @param loginUserInfo
    * @param useStorageInfoDto
    * @param useStorageId
    * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.UseStorageInfoDto>
    * @throws
    * @modifyed :
    *
   **/
   @Secured(value = {"ROLE_USER"})
   @PostMapping(value = "/my/management/storage/use/cancel/{useStorageId:.+(?<!\\.js)$}")
   public ApiResult<String> cancelUseStorageInfo(
           @RequestBody StrgeUseInfDto useStorageInfoDto,
           @PathVariable String useStorageId){
       
       storageService.cancelUseStorage(useStorageId, useStorageInfoDto);

       return success("ok");
   }	
   
   /**
   *
   * @methodName : rejectUseStorageInfo
   * @date : 2021-10-08 오후 2:32
   * @author : xeroman.k
   * @param loginUserInfo
   * @param useStorageInfoDto
   * @param useStorageId
   * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.UseStorageInfoDto>
   * @throws
   * @modifyed :
   *
  **/
  @Secured(value = {"ROLE_MANAGER"})
  @PostMapping(value = "/management/storage/use/reject/{useStorageId:.+(?<!\\.js)$}")
  public ApiResult<String> rejectUseStorageInfo(
          @RequestBody StrgeUseInfDto useStorageInfoDto,
          @PathVariable String useStorageId){
    
      storageService.rejectUseStorage(useStorageId, useStorageInfoDto);

      return success("ok");
  }   
  
}
