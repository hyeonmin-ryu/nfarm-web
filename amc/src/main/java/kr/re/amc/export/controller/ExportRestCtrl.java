package kr.re.amc.export.controller;


import static kr.re.amc.utils.ApiUtils.success;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.export.dto.ExportApproverInfoDto;
import kr.re.amc.export.dto.ExportReqDto;
import kr.re.amc.export.dto.ExportSearchDto;
import kr.re.amc.export.service.ExportReqService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class ExportRestCtrl {

	@Resource(name="exportReqService")
	private ExportReqService exportReqService;
	
	@GetMapping(value = "/export/list")
	public ApiResult<PageInfo<ExportReqDto>> inqDataBoxList(ExportSearchDto exportSearchDto){
		
		PageHelper.startPage(exportSearchDto.getPage() , exportSearchDto.getSize());
		List<ExportReqDto> exportInfo = exportReqService.inqDataBoxList(exportSearchDto);
		
		PageInfo<ExportReqDto> pageData = new PageInfo<>(exportInfo);
		
		return success(pageData);
	}
	
	@GetMapping(value = "/mypage/export/view/{tkoutReqstNo:.+(?<!\\.js)$}")
    public ApiResult<ExportReqDto> getExportView(@PathVariable Long tkoutReqstNo){
        return success(exportReqService.getExportView(tkoutReqstNo));
    }
    
	@GetMapping(value = "/export/use/list")
	   public ApiResult<PageInfo<ExportReqDto>> getUseDataBoxList(ExportSearchDto exportSearchDto){

		PageHelper.startPage(exportSearchDto.getPage(), exportSearchDto.getSize());
	    List<ExportReqDto> pageUseDataBoxes = exportReqService.getUseDataBoxList(exportSearchDto);
	    PageInfo<ExportReqDto> pageData = new PageInfo<>(pageUseDataBoxes);
	    
	    return success(pageData);
	   }
	
	@PostMapping(value = "/export/reg")
	public ApiResult<ExportReqDto> regExport(@RequestBody ExportReqDto exportReqDto){
		exportReqDto.setTkoutReqstUserId(AmcUtil.getPrincipal().getUserId());
		
		return success(exportReqService.regExport(exportReqDto));
	}
	
	@PostMapping(value = "/my/export/cancel")
	public ApiResult<ExportReqDto> myExportCancel(@RequestBody ExportReqDto exportReqDto) {
		exportReqService.cancelExport(exportReqDto);
		return success(null);
	}
	
	@PostMapping(value = "/my/admin/export/accept")
	public ApiResult<ExportReqDto> myAdminExportAccept(@RequestBody ExportReqDto exportServerInfoDto) {
		return success(exportReqService.myAdminExportAccept(exportServerInfoDto));
	}
	
	@PostMapping(value = "/my/export/approve")
	public ApiResult<ExportApproverInfoDto> myExportApprove(@RequestBody ExportApproverInfoDto exportApproverInfoDto){
		return success(exportReqService.myExportApprove(exportApproverInfoDto));
	}
	
	@PostMapping(value = "/my/export/reject")
	public ApiResult<ExportApproverInfoDto> myExportReject(@RequestBody ExportApproverInfoDto exportApproverInfoDto) {
		return success(exportReqService.myExportReject(exportApproverInfoDto));
	}
	
	@GetMapping(value = "/my/export/download")
	public void myExportDownload(String tkoutReqstNo, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		exportReqService.myExportDownload(tkoutReqstNo, request, response);
	}
}
