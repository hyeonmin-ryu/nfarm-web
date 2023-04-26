package kr.re.amc.storage.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.re.amc.etc.FileUploadResponse;
import kr.re.amc.etc.RequestObjectDto;
import kr.re.amc.storage.dto.DtbxBucketMstDto;
import kr.re.amc.storage.dto.StrgeOthbcReqstInfDto;
import kr.re.amc.storage.dto.StrgeReqInfDto;
import kr.re.amc.storage.dto.S3ObjectDto;
import kr.re.amc.storage.dto.StorageSearchDto;
import kr.re.amc.storage.dto.StrgeUseInfDto;

public interface StorageService {
	
	
	List<StrgeReqInfDto> inqReqStorageList(StorageSearchDto storageSearchDto);
	int regReqStorage(StrgeReqInfDto tbStrgeReqInfDto);

	List<StrgeOthbcReqstInfDto> inqOpenStorageList(StorageSearchDto storageSearchDto);
	int regOpenStorage(StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto);
	
	List<StrgeOthbcReqstInfDto> inqUseStorageList(StorageSearchDto storageSearchDto);
	StrgeOthbcReqstInfDto getUseStorageInfo(String othbcReqstId);
	
	
	StrgeReqInfDto inqStorageSeq(String reqStorageId);
	
	List<StrgeReqInfDto> inqStorageUsedList(StorageSearchDto storageSearchDto);
	int regReqUseStorage(StrgeUseInfDto strgeUseInfDto);
	int approveReqStorageInfo(String reqStorageId, DtbxBucketMstDto dtbxBucketMstDto);
	int rejectReqStorageInfo(String reqStorageId, StrgeReqInfDto tbStrgeReqInfDto);
	int cancelReqStorageInfo(String reqStorageId);
	int deleteReqStorageInfo(String reqStorageId, StrgeReqInfDto tbStrgeReqInfDto);
	int deleteReqStorageInfo(String reqStorageId);
	StrgeOthbcReqstInfDto getOpenStorageInfo(String othbcReqstId);
	int approveOpenStorageInfo(String othbcReqstId);
	int rejectOpenStorageInfo(String othbcReqstId, StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto);
	int cancelOpenStorageInfo(String othbcReqstId, StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto);
	List<StrgeUseInfDto> inqMyUseStorageList(StorageSearchDto storageSearchDto);
	StrgeUseInfDto getMyUseStorageInfo(String useStorageId);
	int approveUseStorage(String useStorageId);
	int cancelUseStorage(String useStorageId, StrgeUseInfDto strgeUseInfDto);
	int rejectUseStorage(String useStorageId, StrgeUseInfDto strgeUseInfDto);

	List<StrgeReqInfDto> getReqStorageAuthList(String storgeReqstId);
	
	List<StrgeReqInfDto> inqStorageUsedSummary();
	List<StrgeReqInfDto> inqMyStorageBucketList();
	List<S3ObjectDto> getObjectList(String bucketName, String folderName);
	FileUploadResponse uploadFile(String bucketName, String folderName, List<MultipartFile> multipartFiles) throws InterruptedException;

	FileUploadResponse uploadBoardFile(String folderName, List<MultipartFile> multipartFiles) throws InterruptedException;
	void deleteObjects(List<RequestObjectDto> requestObjectDtos); 
}
