package kr.re.amc.storage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.storage.dto.StrgeOthbcReqstInfDto;
import kr.re.amc.storage.dto.StorageSearchDto;
import kr.re.amc.storage.dto.StrgeUseInfDto;

@Repository("strgeUseInfDao")
public class StrgeUseInfDao extends EgovComAbstractDAO{
	
	
	
	/**
	 * 사용신청 조회
	 * 
	 * @param storageSearchDto
	 * @return
	 */
	public List<StrgeOthbcReqstInfDto> inqUseStorageList(StorageSearchDto storageSearchDto) {
		return selectList("strgeUseInfDao.inqUseStorageList", storageSearchDto);
	}

	public StrgeOthbcReqstInfDto getUseStorageInfo(String othbcReqstId) {
		return selectOne("strgeUseInfDao.getUseStorageInfo", othbcReqstId);
	}

	public int regReqUseStorage(StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto) {
		return insert("strgeUseInfDao.regReqUseStorage", StrgeOthbcReqstInfDto);
	}

	public List<StrgeUseInfDto> inqMyUseStorageList(StorageSearchDto storageSearchDto) {
		return selectList("strgeUseInfDao.inqMyUseStorageList", storageSearchDto);
	}

	public StrgeUseInfDto getMyUseStorageInfo(String useStrgeId) {
		return selectOne("strgeUseInfDao.getMyUseStorageInfo", useStrgeId);
	}

	public int updtUseStorageApproveStat(StrgeUseInfDto strgeUseInfDto) {
		return update("strgeUseInfDao.updtUseStorageApproveStat", strgeUseInfDto);
	}

	public int updtUseStorageStat(StrgeUseInfDto strgeUseInfDto) {
		return update("strgeUseInfDao.updtUseStorageStat", strgeUseInfDto);
	}


}
