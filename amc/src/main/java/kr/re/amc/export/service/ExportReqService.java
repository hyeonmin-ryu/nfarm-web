package kr.re.amc.export.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.re.amc.export.dto.ExportApproverInfoDto;
import kr.re.amc.export.dto.ExportReqDto;
import kr.re.amc.export.dto.ExportSearchDto;

public interface ExportReqService {

	public List<ExportReqDto> inqDataBoxList(ExportSearchDto exportSearchDto);
	
	public ExportReqDto getExportView(Long exportReqSeq);
	
	public List<ExportReqDto> getUseDataBoxList(ExportSearchDto exportSearchDto);
	
	public ExportReqDto regExport(ExportReqDto exportReqDto);
	
	public int cancelExport(ExportReqDto exportReqDto);
	
	public ExportReqDto myAdminExportAccept(ExportReqDto exportServerInfoDto);
	
	public ExportApproverInfoDto myExportApprove(ExportApproverInfoDto exportApproverInfoDto);
	
	public ExportApproverInfoDto myExportReject(ExportApproverInfoDto exportApproverInfoDto);
	
	public void myExportDownload(String exportReqSeq, HttpServletRequest request, HttpServletResponse response)throws IOException ;
}
