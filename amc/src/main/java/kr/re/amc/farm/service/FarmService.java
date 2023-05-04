package kr.re.amc.farm.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.re.amc.board.dto.FaqDto;
import kr.re.amc.board.dto.InquiryDto;
import kr.re.amc.board.dto.NoticeDto;
import kr.re.amc.export.dto.ExportApproverInfoDto;
import kr.re.amc.export.dto.ExportReqDto;
import kr.re.amc.export.dto.ExportSearchDto;
import kr.re.amc.farm.dto.FarmDto;
import kr.re.amc.farm.dto.FarmSearchDto;

public interface FarmService {

    List<FarmDto> getFarmList(FarmSearchDto farmSearchDto);
	List<FarmDto> farmList(FarmSearchDto farmSearchDto);
	List<FarmDto> growList(FarmSearchDto farmSearchDto);
	
	public FarmDto regFarm(FarmDto farmDto);
	
	/*public ExportReqDto getExportView(Long exportReqSeq);
	 * public List<ExportReqDto> getUseDataBoxList(ExportSearchDto exportSearchDto);
	 * 
	 * public ExportReqDto regExport(ExportReqDto exportReqDto);
	 * 
	 * public int cancelExport(ExportReqDto exportReqDto);
	 * 
	 * public ExportReqDto myAdminExportAccept(ExportReqDto exportServerInfoDto);
	 * 
	 * public ExportApproverInfoDto myExportApprove(ExportApproverInfoDto
	 * exportApproverInfoDto);
	 * 
	 * public ExportApproverInfoDto myExportReject(ExportApproverInfoDto
	 * exportApproverInfoDto);
	 * 
	 * public void myExportDownload(String exportReqSeq, HttpServletRequest request,
	 * HttpServletResponse response)throws IOException ;
	 */
}
