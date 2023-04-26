package kr.re.amc.apis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/*
 * @package : com.itsm.dranswer.apis
 * @name : ApiRestCtrl.java
 * @date : 2021-10-08 오후 1:10
 * @author : xeroman.k
 * @version : 1.0.0
 * @modifyed :
 */


import kr.re.amc.storage.service.StorageService;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class ApiRestCtrl {

    private final StorageService storageService;

    @Autowired
    public ApiRestCtrl(StorageService storageService) {
        this.storageService = storageService;
    }
    
	@Value("${ncp.amc.object-storage-id}")
	private String adminObjectStorageId;
	

    /**
     * 벌크 업로드 용 저장신청정보 조회
     * @methodName : uploadBulk
     * @date : 2021-10-08 오후 1:10
     * @author : xeroman.k
     * @param reqStorageId
     * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.storage.ReqStorageInfoDto>
     * @throws
     * @modifyed :
     *
    **/
//    @GetMapping(value = "/apis/upload/bulk/{reqStorageId:.+(?<!\\.js)$}")
//    public ApiResult<ReqStorageInfoDto> uploadBulk(
//            @PathVariable String reqStorageId) {
//
//        ReqStorageInfoDto reqStorageInfoDto =
//                storageService.getReqStorage(reqStorageId);
//
//        
//        reqStorageInfoDto.getDiseaseManagerUserInfo().setNCloudId(adminObjectStorageId);
//        return success(reqStorageInfoDto);
//    }
    
    
}
