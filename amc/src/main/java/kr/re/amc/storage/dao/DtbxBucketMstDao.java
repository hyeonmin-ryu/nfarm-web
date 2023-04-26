package kr.re.amc.storage.dao;


import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.storage.dto.DtbxBucketMstDto;

@Repository("dtbxBucketMstDao")
public class DtbxBucketMstDao extends EgovComAbstractDAO{
	
	/**
	 * 버킷정보 저장
	 * @param dtbxBucketMstDto
	 * @return
	 */
	public int regBucketInfo(DtbxBucketMstDto dtbxBucketMstDto) {
		return insert("dtbxBucketMstDao.regBucketInfo", dtbxBucketMstDto);
	}
}
