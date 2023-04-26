package kr.re.amc.storage.controller;


import static kr.re.amc.utils.ApiUtils.success;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.etc.FileUploadResponse;
import kr.re.amc.etc.RequestObjectDto;
import kr.re.amc.storage.dto.StrgeReqInfDto;
import kr.re.amc.storage.dto.S3ObjectDto;
import kr.re.amc.storage.dto.StorageSearchDto;
import kr.re.amc.storage.service.StorageService;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class MyStorageRestCtrl {

	@Resource(name="storageService")
	private StorageService storageService;
	
	
    /**
     * 버킷리스트
     * @methodName : getMyStorageBucketList
     * @date : 2021-07-15 오후 6:09
     * @author : xeroman.k
     * @param loginUserInfo
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<java.util.List<com.itsm.dranswer.storage.ReqStorageInfoDto>>
     * @throws
     * @modifyed :
     *
    **/
    @GetMapping(value = "/my/management/storage/bucket/list")
    public ApiResult<List<StrgeReqInfDto>> getMyStorageBucketList(){

        List<StrgeReqInfDto> strgeReqInfDto =
                storageService.inqMyStorageBucketList();

        return success(strgeReqInfDto);
    }	
    
    
    /**
     * 오브젝트 리스트
     * @methodName : getMyStorageObjectList
     * @date : 2021-07-15 오후 6:09
     * @author : xeroman.k
     * @param loginUserInfo
     * @param bucketName
     * @param folderName
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<java.util.List<com.itsm.dranswer.storage.S3ObjectDto>>
     * @throws
     * @modifyed :
     *
    **/
    @GetMapping(value = "/my/management/storage/object/list/{bucketId:.+(?<!\\.js)$}")
    public ApiResult<List<S3ObjectDto>> getMyStorageObjectList(
            @PathVariable String bucketId,
            @RequestParam(required = false) String folderName){

        return success(storageService.getObjectList(bucketId, folderName));
    }    
	
    /**
     * 파일 업로드
     * @methodName : fileUpload
     * @date : 2021-07-15 오후 6:09
     * @author : xeroman.k
     * @param loginUserInfo
     * @param request
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.etc.FileUploadResponse>
     * @throws
     * @modifyed :
     *
    **/
    @PostMapping(path = "/my/management/storage/object/fileUpload")
    public ApiResult<FileUploadResponse> fileUpload(
            MultipartHttpServletRequest request
    ) throws InterruptedException {

        String bucketName = request.getParameter("bucketName");
        String folderName = request.getParameter("folderName");

        List<MultipartFile> multipartFiles = request.getFiles("multipartFile");

        return success(storageService.uploadFile(bucketName, folderName, multipartFiles));
    }    
    
    
    /**
     * 오브젝트삭제
     * @methodName : deleteObject
     * @date : 2021-07-15 오후 6:09
     * @author : xeroman.k
     * @param loginUserInfo
     * @param requestObjectDtos
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<java.lang.Boolean>
     * @throws
     * @modifyed :
     *
    **/
    @PostMapping(path = "/my/management/storage/object/delete")
    public ApiResult<Boolean> deleteObject(
            @RequestBody List<RequestObjectDto> requestObjectDtos
    ){
        storageService.deleteObjects(requestObjectDtos);
        return success(true);
    }    
    
    
	@GetMapping(path = "/storage/used/list")
    public ApiResult<PageInfo<StrgeReqInfDto>> inqStorageUsedList(StorageSearchDto storageSearchDto){

		PageHelper.startPage(storageSearchDto.getPage(), storageSearchDto.getSize());
		List<StrgeReqInfDto> storagelist = storageService.inqStorageUsedList(storageSearchDto);
		PageInfo<StrgeReqInfDto> pageData = new PageInfo<>(storagelist);
		
        return success(pageData);
    }
	
	@GetMapping(path = "/storage/used/summary")
    public ApiResult<List<StrgeReqInfDto>> inqStorageUsedSummary(){

        return success(storageService.inqStorageUsedSummary());
    }
}
