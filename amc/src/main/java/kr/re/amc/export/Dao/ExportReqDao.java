package kr.re.amc.export.Dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.export.dto.ExportApproverInfoDto;
import kr.re.amc.export.dto.ExportReqDto;
import kr.re.amc.export.dto.ExportSearchDto;
import kr.re.amc.export.dto.ExportServerInfoDto;

@Repository("exportReqDao")
public class ExportReqDao extends EgovComAbstractDAO {

	public List<ExportReqDto> inqDataBoxList(ExportSearchDto exportSearchDto){
		return selectList("exportReqDao.inqDataBoxList", exportSearchDto);
	}
	
	public ExportReqDto getExportView(Long exportReqSeq) {
		return selectOne("exportReqDao.getExportView", exportReqSeq);
	}
	
	public List<ExportApproverInfoDto> inqExportApproverList(Long exportReqSeq){
		return selectList("exportReqDao.inqExportApproverList", exportReqSeq);
	}
	
	public List<ExportReqDto> inqUseDataBoxList(ExportSearchDto exportSearchDto) {
		return selectList("exportReqDao.inqUseDataBoxList", exportSearchDto);
	}
	
	public List<ExportApproverInfoDto> inqBucketList(Long exportReqSeq){
		return selectList("exportReqDao.inqBucketList", exportReqSeq);
	}
	
	public int regExport(ExportReqDto exportReqDto) {
		return insert("exportReqDao.regExport", exportReqDto);
	}
	
	public int regExportServerInfo(ExportServerInfoDto exportServerInfoDto) {
		return insert("exportReqDao.regExportServerInfo", exportServerInfoDto);
	}
	
	public int regExportApproverInfo(ExportApproverInfoDto exportApproverInfoDto) {
		return insert("exportReqDao.regExportApproverInfo", exportApproverInfoDto);
	}
	
	public int cancelExport(ExportReqDto exportReqDto) {
		return update("exportReqDao.cancelExport", exportReqDto); 
	}
	
	public int myAdminExportAccept(ExportReqDto exportReqDto) {
		return update("exportReqDao.myAdminExportAccept", exportReqDto);
	}
	
	public int myExportAccept(Long tkoutReqstNo) {
		return update("exportReqDao.myExportAccept", tkoutReqstNo);
	}
	
	public ExportApproverInfoDto ExportConfmInfo(ExportApproverInfoDto exportApproverInfoDtos) {
		return selectOne("exportReqDao.ExportConfmInfo", exportApproverInfoDtos);
	}
	
	public int myExportApprove(ExportApproverInfoDto exportApproverInfoDto) {
		return update("exportReqDao.myExportApprove", exportApproverInfoDto);
	}
	
	public int ExportApprove(ExportReqDto exportReqDto) {
		return update("exportReqDao.ExportApprove", exportReqDto);
	}
	
	public int myExportReject(ExportApproverInfoDto exportApproverInfoDto) {
		return update("exportReqDao.myExportReject", exportApproverInfoDto);
	}
	
	public int ExportReject(ExportReqDto exportReqDto) {
		return update("exportReqDao.ExportReject", exportReqDto);
	}
	
	public ExportReqDto getExportSvrInfo(ExportApproverInfoDto exportApproverInfoDto) {
		return selectOne("exportReqDao.getExportSvrInfo", exportApproverInfoDto);
	}
}
