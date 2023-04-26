package kr.re.amc.storage.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.storage.dto.StrgeReqInfDto;
import kr.re.amc.storage.dto.StorageSearchDto;

@Repository("myStorageInfoDao")
public class MyStorageInfoDao extends EgovComAbstractDAO{

	public List<StrgeReqInfDto> inqStorageUsedList(StorageSearchDto storageSearchDto){
		return selectList("myStorageInfoDao.inqStorageUsedList", storageSearchDto);
	}
	
	public List<StrgeReqInfDto> inqStorageUsedSummary(){
		return selectList("myStorageInfoDao.inqStorageUsedSummary");
	}
}
