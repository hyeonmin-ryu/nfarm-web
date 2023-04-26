package kr.re.amc.storage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.cmm.dto.SearchDto;
import kr.re.amc.storage.dto.DtbxBucketMstDto;
import kr.re.amc.storage.dto.StrgeOthbcReqstInfDto;
import kr.re.amc.storage.dto.StrgeReqInfDto;
import kr.re.amc.storage.dto.StorageSearchDto;
import kr.re.amc.storage.dto.StrgeUseInfDto;
import kr.re.amc.users.dto.UserInfoDto;

@Repository("strgeReqInfDao")
public class StrgeReqInfDao extends EgovComAbstractDAO{
	
	
	/**
	 * 저장신청 조회
	 * 
	 * @param storageSearchDto
	 * @return
	 */
	public List<StrgeReqInfDto> inqReqStorageList(StorageSearchDto storageSearchDto){
		return selectList("strgeReqInfDao.inqReqStorageInfoList", storageSearchDto);
	}

	public int regReqStorage(StrgeReqInfDto strgeReqInfDto) {
		return insert("strgeReqInfDao.regReqStorage", strgeReqInfDto);
	}
	
	public StrgeReqInfDto inqStorageSeq(String storgeReqstId) {
		return selectOne("strgeReqInfDao.inqStorageSeq", storgeReqstId);
	}

	//승인용
	public int updtReqStorageApproveStat(DtbxBucketMstDto bucketInfoDto) {
		return update("strgeReqInfDao.updtReqStorageApproveStat", bucketInfoDto);
	}
	
	public int updtReqStorageStat(StrgeReqInfDto strgeReqInfDto) {
		return update("strgeReqInfDao.updtReqStorageStat", strgeReqInfDto);
	}

	public List<UserInfoDto> getReqStorageAuthList(String reqStorageId){
		return selectList("strgeReqInfDao.getReqStorageAuthList", reqStorageId);
	}

	public List<StrgeReqInfDto> inqMyStorageBucketList(StorageSearchDto storageSearchDto) {
		return selectList("strgeReqInfDao.inqMyStorageBucketList", storageSearchDto);
	}
	
	
}
