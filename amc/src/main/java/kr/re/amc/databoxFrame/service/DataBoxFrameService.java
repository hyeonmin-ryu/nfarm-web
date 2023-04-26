package kr.re.amc.databoxFrame.service;

import java.util.List;

import javax.transaction.Transactional;

import kr.re.amc.databoxFrame.dto.DataBoxFrameSearchDto;
import kr.re.amc.databoxFrame.dto.DtbxFrmeCreatInfDto;

@Transactional
public interface DataBoxFrameService {

	List<DtbxFrmeCreatInfDto> inqDataBoxFrameList(DataBoxFrameSearchDto dataBoxFrameSearchDto);

	DtbxFrmeCreatInfDto reqDataBoxFrame(DtbxFrmeCreatInfDto dtbxFrmeCreatInfDto);

	DtbxFrmeCreatInfDto getDataBoxFrameDetailInfo(Long dtbxFrmeNo);

	DtbxFrmeCreatInfDto updtDataBoxFrame(DtbxFrmeCreatInfDto dtbxFrmeCreatInfDto);

	
}
   

    
	
	

