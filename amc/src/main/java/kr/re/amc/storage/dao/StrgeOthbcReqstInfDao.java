package kr.re.amc.storage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.storage.dto.StrgeOthbcReqstInfDto;
import kr.re.amc.storage.dto.StorageSearchDto;

@Repository("strgeOthbcReqstInfDao")
public class StrgeOthbcReqstInfDao extends EgovComAbstractDAO{
	
	
	/**
	 * 저장신청 조회
	 * 
	 * @param storageSearchDto
	 * @return
	 */
	public List<StrgeOthbcReqstInfDto> inqOpenStorageList(StorageSearchDto storageSearchDto){
		return selectList("strgeOthbcReqstInfDao.inqOpenStorageList", storageSearchDto);
	}

	/**
	 * 저장 신청
	 * @param StrgeOthbcReqstInfDto
	 * @return
	 */
	public int regOpenStorage(StrgeOthbcReqstInfDto strgeOthbcReqstInfDto) {
		return insert("strgeOthbcReqstInfDao.regOpenStorage", strgeOthbcReqstInfDto);
	}

	public StrgeOthbcReqstInfDto getOpenStorageInfo(String reqOpenId) {
		return selectOne("strgeOthbcReqstInfDao.getOpenStorageInfo", reqOpenId);
	}

	public int updtOpenStorageStat(StrgeOthbcReqstInfDto strgeOthbcReqstInfDto) {
		return update("strgeOthbcReqstInfDao.updtOpenStorageStat", strgeOthbcReqstInfDto);
	}
}
