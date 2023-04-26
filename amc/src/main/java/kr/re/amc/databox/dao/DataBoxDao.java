package kr.re.amc.databox.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.databox.dto.DataBoxSearchDto;
import kr.re.amc.databox.dto.DtbxBlckStrgeReqMstDto;
import kr.re.amc.databox.dto.DtbxDataReqMstDto;
import kr.re.amc.databox.dto.DtbxReqMstDto;
import kr.re.amc.databox.dto.DtbxSvrReqMstDto;
import kr.re.amc.databox.dto.DtbxUserReqMstDto;
import kr.re.amc.storage.dto.StrgeUseInfDto;

@Repository("dataBoxDao")
public class DataBoxDao extends EgovComAbstractDAO{
	
	public List<DtbxReqMstDto> inqReqStorageList(DataBoxSearchDto dataBoxSearchDto) {
		return selectList("dataBoxDao.inqReqStorageList", dataBoxSearchDto);
	}
	
	public DtbxReqMstDto inqDataBox(Long dtbxSn) {
		return selectOne("dataBoxDao.inqDataBox", dtbxSn);
	}

	public List<StrgeUseInfDto> getUseStorageList(DataBoxSearchDto dataBoxSearchDto) {
		return selectList("dataBoxDao.getUseStorageList", dataBoxSearchDto);
	}

	public int regDtbxReqMst(DtbxReqMstDto dtbxReqMstDto) {
		return insert("dataBoxDao.regDtbxReqMst", dtbxReqMstDto);
	}

	public int regDtbxMst(DtbxReqMstDto dtbxReqMstDto) {
		return insert("dataBoxDao.regDtbxMst", dtbxReqMstDto);
		
	}

	public int regDtbxUserReqMst(DtbxUserReqMstDto dtbxUserReqMstDto) {
		return insert("dataBoxDao.regDtbxUserReqMst", dtbxUserReqMstDto);
		
	}

	public int regDtbxUserDtl(DtbxUserReqMstDto dtbxUserReqMstDto) {
		return insert("dataBoxDao.regDtbxUserDtl", dtbxUserReqMstDto);
		
	}

	public int regDtbxSvrReqMst(DtbxSvrReqMstDto dtbxSvrReqMstDto) {
		return insert("dataBoxDao.regDtbxSvrReqMst", dtbxSvrReqMstDto);
		
	}

	public int regDtbxSvrDtl(DtbxSvrReqMstDto dtbxSvrReqMstDto) {
		 return insert("dataBoxDao.regDtbxSvrDtl", dtbxSvrReqMstDto);
		
	}

	public int regDtbxBlckStrgeReqMst(DtbxBlckStrgeReqMstDto dtbxBlckStrgeReqMstDto) {
		return insert("dataBoxDao.regDtbxBlckStrgeReqMst", dtbxBlckStrgeReqMstDto);
		
	}

	public int regDtbxDataReqMst(DtbxDataReqMstDto dtbxDataReqMstDto) {
		return insert("dataBoxDao.regDtbxDataReqMst", dtbxDataReqMstDto);
		
	}


	public DtbxReqMstDto inqDtbxReqMst(Long dtbxReqNo) {
		return selectOne("dataBoxDao.inqDtbxReqMst", dtbxReqNo);
	}
	
	public DtbxReqMstDto inqDtbxReqMstAdm(Long dtbxReqNo) {
		return selectOne("dataBoxDao.inqDtbxReqMstAdm", dtbxReqNo);
	}
	

	public List<DtbxUserReqMstDto> inqDtbxUserReqMst(Long dtbxReqNo) {
		return selectList("dataBoxDao.inqDtbxUserReqMst", dtbxReqNo);
	}

	public List<DtbxSvrReqMstDto> inqDtbxSvrReqMst(Long dtbxReqNo) {
		return selectList("dataBoxDao.inqDtbxSvrReqMst", dtbxReqNo);
	}

	public List<DtbxDataReqMstDto> inqDtbxDataReqMst(Long dtbxReqNo) {
		return selectList("dataBoxDao.inqDtbxDataReqMst", dtbxReqNo);
	}

	public List<DtbxBlckStrgeReqMstDto> inqDtbxBlckStrgeReqMst(DtbxSvrReqMstDto dtbxSvrReqMstDto) {
		return selectList("dataBoxDao.inqDtbxBlckStrgeReqMst", dtbxSvrReqMstDto);
	}

	public List<DtbxDataReqMstDto> inqDtbxDataReqMstAdm(Long dtbxReqNo) {
		return selectList("dataBoxDao.inqDtbxDataReqMstAdm", dtbxReqNo);
	}

	public int updtDtbxReqMst(DtbxReqMstDto dtbxReqMstDto) {
		return update("dataBoxDao.updtDtbxReqMst", dtbxReqMstDto);
		
	}

	public int updtDtbxMst(DtbxReqMstDto dtbxReqMstDto) {
		return update("dataBoxDao.updtDtbxMst", dtbxReqMstDto);
		
	}

	public int updtDtbxUserDtl(DtbxUserReqMstDto dataBoxUserInfoDto) {
		return update("dataBoxDao.updtDtbxUserDtl", dataBoxUserInfoDto);
		
	}

	public int updtDtbxSvrDtl(DtbxSvrReqMstDto dtbxSvrReqMstDto) {
		return update("dataBoxDao.updtDtbxSvrDtl", dtbxSvrReqMstDto);
		
	}

	public int regDtbxHst(Long dtbxReqNo) {
		return insert("dataBoxDao.regDtbxHst", dtbxReqNo);
		
	}

	public int updtDtbxReqMstDel(DtbxReqMstDto dtbxReqMstDto) {
		return update("dataBoxDao.updtDtbxReqMstDel", dtbxReqMstDto);
		
	}


}
