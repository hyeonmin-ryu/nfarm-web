package kr.re.amc.databoxFrame.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.databoxFrame.dto.DataBoxFrameSearchDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeAdmSvrAcntInfDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeAdmSvrInfDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeCmnuseDataInfDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeCreatInfDto;

@Repository("dataBoxFrameDao")
public class DataBoxFrameDao extends EgovComAbstractDAO{
	
	public List<DtbxFrmeCreatInfDto> inqDataBoxFrameList(DataBoxFrameSearchDto dataBoxFrameSearchDto) {
		return selectList("dataBoxFrameDao.inqDataBoxFrameList", dataBoxFrameSearchDto);
	}

	public int regDtbxFrmeCreatInf(DtbxFrmeCreatInfDto dtbxFrmeCreatInfDto) {
		return insert("dataBoxFrameDao.regDtbxFrmeCreatInf", dtbxFrmeCreatInfDto);
	}

	public int regDtbxFrmeAdmSvrInf(DtbxFrmeAdmSvrInfDto dtbxFrmeAdmSvrInfDto) {
		return insert("dataBoxFrameDao.regDtbxFrmeAdmSvrInf", dtbxFrmeAdmSvrInfDto);
		
	}

	public int regDtbxFrmeAdmSvrAcntInf(DtbxFrmeAdmSvrAcntInfDto dtbxFrmeAdmSvrAcntInfDto) {
		return insert("dataBoxFrameDao.regDtbxFrmeAdmSvrAcntInf", dtbxFrmeAdmSvrAcntInfDto);
		
	}

	public int regDtbxFrmeCmnuseDataInf(DtbxFrmeCmnuseDataInfDto dtbxFrmeCmnuseDataInfDto) {
		return insert("dataBoxFrameDao.regDtbxFrmeCmnuseDataInf", dtbxFrmeCmnuseDataInfDto);
		
	}

	public DtbxFrmeCreatInfDto inqDtbxFrmeCreatInf(Long dtbxFrmeNo) {
		return selectOne("dataBoxFrameDao.inqDtbxFrmeCreatInf", dtbxFrmeNo);
	}

	public List<DtbxFrmeAdmSvrInfDto> inqDtbxFrmeAdmSvrInf(Long dtbxFrmeNo) {
		return selectList("dataBoxFrameDao.inqDtbxFrmeAdmSvrInf", dtbxFrmeNo);
	}

	public List<DtbxFrmeAdmSvrAcntInfDto> inqDtbxFrmeAdmSvrAcntInf(Long dtbxFrmeNo) {
		return selectList("dataBoxFrameDao.inqDtbxFrmeAdmSvrAcntInf", dtbxFrmeNo);
	}

	public List<DtbxFrmeCmnuseDataInfDto> inqDtbxFrmeCmnuseDataInf(Long dtbxFrmeNo) {
		return selectList("dataBoxFrameDao.inqDtbxFrmeCmnuseDataInf", dtbxFrmeNo);
	}

	public int delDtbxFrmeAdmSvrInf(Long dtbxFrmeNo) {
		return delete("dataBoxFrameDao.delDtbxFrmeAdmSvrInf", dtbxFrmeNo);
		
	}

	public int delDtbxFrmeAdmSvrAcntInf(Long dtbxFrmeNo) {
		return delete("dataBoxFrameDao.delDtbxFrmeAdmSvrAcntInf", dtbxFrmeNo);
		
	}

	public int delDtbxFrmeCmnuseDataInf(Long dtbxFrmeNo) {
		return delete("dataBoxFrameDao.delDtbxFrmeCmnuseDataInf", dtbxFrmeNo);
		
	}

	public int updtDtbxFrmeCreatInf(DtbxFrmeCreatInfDto dtbxFrmeCreatInfDto) {
		return update("dataBoxFrameDao.updtDtbxFrmeCreatInf", dtbxFrmeCreatInfDto);
		
	}


}
