package kr.re.amc.etc;


import static kr.re.amc.utils.ApiUtils.success;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/*
 * @package : com.itsm.dranswer.etc
 * @name : FileUploadCtrl.java
 * @date : 2021-10-08 오후 1:49
 * @author : xeroman.k
 * @version : 1.0.0
 * @modifyed :
 */

import com.amazonaws.services.s3.model.CannedAccessControlList;

import kr.re.amc.util.CustomObjectStorage;
import kr.re.amc.utils.ApiUtils.ApiResult;


@RestController
public class FileUploadCtrl {

    private CustomObjectStorage customObjectStorage;

    @Autowired
    public FileUploadCtrl(CustomObjectStorage customObjectStorage){
        this.customObjectStorage = customObjectStorage;
    }

    @PostMapping(path = "/etc/fileUpload")
    public ApiResult<FileUploadResponse> fileUpload(
            MultipartHttpServletRequest request,
            HttpServletResponse response
    ) throws Exception {

        final String endPoint = "https://kr.object.ncloudstorage.com";
        final String regionName = "kr-standard";
        String accessKey = "";
        String secretKey = "";

        String bucketName = "";
        String folderName = "";

        File tempDir = new File("/drAnswer/test");
        if(!tempDir.exists()){
            tempDir.mkdirs();
        }

        List<MultipartFile> multipartFiles = request.getFiles("multipartFile");

        for(MultipartFile multipartFile : multipartFiles){
            File targetFile = new File("/drAnswer/temp/" + multipartFile.getOriginalFilename());
            try {
                InputStream fileStream = multipartFile.getInputStream();
                FileUtils.copyInputStreamToFile(fileStream, targetFile);
            } catch (IOException e) {
                FileUtils.deleteQuietly(targetFile);
                e.printStackTrace();
            }

            String objectName = multipartFile.getOriginalFilename();

            customObjectStorage.uploadObject(endPoint, regionName, accessKey, secretKey, bucketName, folderName, objectName, targetFile, CannedAccessControlList.Private);

            targetFile.delete();
        }

        return success(new FileUploadResponse());
    }

}
