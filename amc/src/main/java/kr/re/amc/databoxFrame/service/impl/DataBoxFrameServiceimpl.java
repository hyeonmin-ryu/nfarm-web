package kr.re.amc.databoxFrame.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

import kr.re.amc.databoxFrame.dao.DataBoxFrameDao;
import kr.re.amc.databoxFrame.dto.DataBoxFrameSearchDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeAdmSvrAcntInfDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeAdmSvrInfDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeCmnuseDataInfDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeCreatInfDto;
import kr.re.amc.databoxFrame.service.DataBoxFrameService;
import kr.re.amc.util.AmcUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("dataBoxFrameService")
@Transactional
public class DataBoxFrameServiceimpl  extends EgovAbstractServiceImpl implements DataBoxFrameService {
    
	@Resource(name="dataBoxFrameDao")
	private DataBoxFrameDao dataBoxFrameDao;

	@Override
	public List<DtbxFrmeCreatInfDto> inqDataBoxFrameList(DataBoxFrameSearchDto dataBoxFrameSearchDto) {
		
		PageHelper.startPage(dataBoxFrameSearchDto.getPage() , dataBoxFrameSearchDto.getSize());
		
		return dataBoxFrameDao.inqDataBoxFrameList(dataBoxFrameSearchDto);
	}

	
	
	@Override
	public DtbxFrmeCreatInfDto reqDataBoxFrame(DtbxFrmeCreatInfDto dtbxFrmeCreatInfDto) {

		
		dtbxFrmeCreatInfDto.setDtbxFrmeSttusCd("Y");
		dtbxFrmeCreatInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
		dataBoxFrameDao.regDtbxFrmeCreatInf(dtbxFrmeCreatInfDto);
		
		
		for(DtbxFrmeAdmSvrInfDto dtbxFrmeAdmSvrInfDto : dtbxFrmeCreatInfDto.getDataBoxFrameAdminServerList()){ 
			dtbxFrmeAdmSvrInfDto.setDtbxFrmeNo(dtbxFrmeCreatInfDto.getDtbxFrmeNo());
			dtbxFrmeAdmSvrInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			
			dataBoxFrameDao.regDtbxFrmeAdmSvrInf(dtbxFrmeAdmSvrInfDto);
			
		}
		
		for(DtbxFrmeAdmSvrAcntInfDto dtbxFrmeAdmSvrAcntInfDto : dtbxFrmeCreatInfDto.getDataBoxFrameAccountList()){ 
			dtbxFrmeAdmSvrAcntInfDto.setDtbxFrmeNo(dtbxFrmeCreatInfDto.getDtbxFrmeNo());
			dtbxFrmeAdmSvrAcntInfDto.setDtbxFrmeAdmAcntId("root");
			dtbxFrmeAdmSvrAcntInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			
			
			dataBoxFrameDao.regDtbxFrmeAdmSvrAcntInf(dtbxFrmeAdmSvrAcntInfDto);
			
		}
		
		
		for(DtbxFrmeCmnuseDataInfDto dtbxFrmeCmnuseDataInfDto : dtbxFrmeCreatInfDto.getDataBoxFramePublicStorageList()){ 
			dtbxFrmeCmnuseDataInfDto.setDtbxFrmeNo(dtbxFrmeCreatInfDto.getDtbxFrmeNo());
			dtbxFrmeCmnuseDataInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			
			dataBoxFrameDao.regDtbxFrmeCmnuseDataInf(dtbxFrmeCmnuseDataInfDto);
			
		}
		
		
		return dtbxFrmeCreatInfDto;
	}



	@Override
	public DtbxFrmeCreatInfDto getDataBoxFrameDetailInfo(Long dtbxFrmeNo) {

		DtbxFrmeCreatInfDto dtbxFrmeCreatInfDto = dataBoxFrameDao.inqDtbxFrmeCreatInf(dtbxFrmeNo);
		
		dtbxFrmeCreatInfDto.setDataBoxFrameAdminServerList(dataBoxFrameDao.inqDtbxFrmeAdmSvrInf(dtbxFrmeNo));
		dtbxFrmeCreatInfDto.setDataBoxFrameAccountList(dataBoxFrameDao.inqDtbxFrmeAdmSvrAcntInf(dtbxFrmeNo));
		dtbxFrmeCreatInfDto.setDataBoxFramePublicStorageList(dataBoxFrameDao.inqDtbxFrmeCmnuseDataInf(dtbxFrmeNo));
		
		return dtbxFrmeCreatInfDto;
	}



	@Override
	public DtbxFrmeCreatInfDto updtDataBoxFrame(DtbxFrmeCreatInfDto dtbxFrmeCreatInfDto) {

		dataBoxFrameDao.delDtbxFrmeAdmSvrInf(dtbxFrmeCreatInfDto.getDtbxFrmeNo());
		dataBoxFrameDao.delDtbxFrmeAdmSvrAcntInf(dtbxFrmeCreatInfDto.getDtbxFrmeNo());
		dataBoxFrameDao.delDtbxFrmeCmnuseDataInf(dtbxFrmeCreatInfDto.getDtbxFrmeNo());
		
		
		dtbxFrmeCreatInfDto.setDtbxFrmeSttusCd("Y");
		dtbxFrmeCreatInfDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		dataBoxFrameDao.updtDtbxFrmeCreatInf(dtbxFrmeCreatInfDto);
		
		
		for(DtbxFrmeAdmSvrInfDto dtbxFrmeAdmSvrInfDto : dtbxFrmeCreatInfDto.getDataBoxFrameAdminServerList()){ 
			dtbxFrmeAdmSvrInfDto.setDtbxFrmeNo(dtbxFrmeCreatInfDto.getDtbxFrmeNo());
			dtbxFrmeAdmSvrInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			
			dataBoxFrameDao.regDtbxFrmeAdmSvrInf(dtbxFrmeAdmSvrInfDto);
			
		}
		
		for(DtbxFrmeAdmSvrAcntInfDto dtbxFrmeAdmSvrAcntInfDto : dtbxFrmeCreatInfDto.getDataBoxFrameAccountList()){ 
			dtbxFrmeAdmSvrAcntInfDto.setDtbxFrmeNo(dtbxFrmeCreatInfDto.getDtbxFrmeNo());
			dtbxFrmeAdmSvrAcntInfDto.setDtbxFrmeAdmAcntId("root");
			dtbxFrmeAdmSvrAcntInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			
			
			dataBoxFrameDao.regDtbxFrmeAdmSvrAcntInf(dtbxFrmeAdmSvrAcntInfDto);
			
		}
		
		
		for(DtbxFrmeCmnuseDataInfDto dtbxFrmeCmnuseDataInfDto : dtbxFrmeCreatInfDto.getDataBoxFramePublicStorageList()){ 
			dtbxFrmeCmnuseDataInfDto.setDtbxFrmeNo(dtbxFrmeCreatInfDto.getDtbxFrmeNo());
			dtbxFrmeCmnuseDataInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			
			dataBoxFrameDao.regDtbxFrmeCmnuseDataInf(dtbxFrmeCmnuseDataInfDto);
			
		}
		
		
		return dtbxFrmeCreatInfDto;
	}
	

    
}
	

