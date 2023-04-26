package kr.re.amc.databox.service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import kr.re.amc.databox.dto.DataBoxSearchDto;
import kr.re.amc.databox.dto.DtbxReqMstDto;
import kr.re.amc.storage.dto.StrgeUseInfDto;

@Transactional
public interface DataBoxService {

	List<DtbxReqMstDto> getDataBoxList(DataBoxSearchDto dataBoxSearchDto);

	HashMap getReqImageList();

	HashMap getReqServerList(String data);

	List<StrgeUseInfDto> getUseStorageList(DataBoxSearchDto dataBoxSearchDto);

	DtbxReqMstDto reqDataBox(DtbxReqMstDto dtbxReqMstDto);

	DtbxReqMstDto getDataBoxDetailInfo(Long dtbxReqNo);

	DtbxReqMstDto myAdminAccept(DtbxReqMstDto dtbxReqMstDto);

	DtbxReqMstDto myAdminApprove(DtbxReqMstDto dtbxReqMstDto);

	DtbxReqMstDto myBoxComplete(DtbxReqMstDto dtbxReqMstDto);

	DtbxReqMstDto myBoxCancel(DtbxReqMstDto dtbxReqMstDto);

	DtbxReqMstDto myBoxDelete(DtbxReqMstDto dtbxReqMstDto);

	DtbxReqMstDto myAdminReject(DtbxReqMstDto dtbxReqMstDto);

	
}
   

    
	
	

