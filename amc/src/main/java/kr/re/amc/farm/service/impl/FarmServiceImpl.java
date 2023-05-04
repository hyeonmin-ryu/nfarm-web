package kr.re.amc.farm.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import kr.re.amc.board.dto.BoardSearchDto;
import kr.re.amc.board.dto.FaqDto;
import kr.re.amc.board.dto.NoticeDto;
import kr.re.amc.board.dto.NoticeFileDto;
import kr.re.amc.commons.CustomMailSender;
import kr.re.amc.export.Dao.ExportReqDao;
import kr.re.amc.export.dto.ExportApproverInfoDto;
import kr.re.amc.export.dto.ExportReqDto;
import kr.re.amc.export.dto.ExportSearchDto;
import kr.re.amc.export.dto.ExportServerInfoDto;
import kr.re.amc.export.service.ExportReqService;
import kr.re.amc.farm.service.FarmService;
import kr.re.amc.farm.dao.FarmDao;
import kr.re.amc.farm.dto.FarmDto;
import kr.re.amc.farm.dto.FarmSearchDto;
import kr.re.amc.users.dao.UserDao;
import kr.re.amc.users.dto.UserInfoDto;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.utils.StringUtil;


@Service("FarmService")
public class FarmServiceImpl extends EgovAbstractServiceImpl implements FarmService{

	
	@Resource(name = "farmDao")
	private FarmDao farmDao;
	
	

	@Override
	public List<FarmDto> getFarmList(FarmSearchDto farmSearchDto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<FarmDto> farmList(FarmSearchDto farmSearchDto) {
		// TODO Auto-generated method stub
		return farmDao.farmList(farmSearchDto);
	}
	
	@Override
	public List<FarmDto> growList(FarmSearchDto farmSearchDto) {
		// TODO Auto-generated method stub
		return farmDao.growList(farmSearchDto);
	}
	
	@Override
	public FarmDto regFarm(FarmDto farmDto) {
		//farmDto.setCrtUsrId(AmcUtil.getPrincipal().getUserId());
		//farmDto.setChgUsrId(AmcUtil.getPrincipal().getUserId());

		farmDao.regFarm(farmDto);
		
		return farmDto;
	}


	/*
	 * @Override // 상세보기 public FarmDto getFarmList(FarmDto FarmDto) { int idx = 0;
	 * 
	 * ExportReqDto exportReqDto = exportReqDao.getExportView(exportReqSeq);
	 * 
	 * List<ExportApproverInfoDto> exportApproverInfoDto =
	 * exportReqDao.inqExportApproverList(exportReqSeq);
	 * 
	 * for(ExportApproverInfoDto exportApproverInfoDtos : exportApproverInfoDto) {
	 * exportApproverInfoDtos.setIdx(idx);
	 * exportApproverInfoDtos.setTkoutReqstNo(exportReqSeq);
	 * 
	 * ExportApproverInfoDto exportApproverInfoDto2 =
	 * exportReqDao.ExportConfmInfo(exportApproverInfoDtos);
	 * 
	 * exportApproverInfoDtos.setTkoutConfmSn(exportApproverInfoDto2.getTkoutConfmSn
	 * ());
	 * exportApproverInfoDtos.setConfmResn(exportApproverInfoDto2.getConfmResn());
	 * exportApproverInfoDtos.setTkoutConfmUserId(exportApproverInfoDto2.
	 * getTkoutConfmUserId());
	 * exportApproverInfoDtos.setProcessDt(exportApproverInfoDto2.getProcessDt());
	 * exportApproverInfoDtos.setTkoutProcessSttusCd(exportApproverInfoDto2.
	 * getTkoutProcessSttusCd());
	 * exportApproverInfoDtos.setTkoutProcessSttusCdNm(exportApproverInfoDto2.
	 * getTkoutProcessSttusCdNm());
	 * 
	 * idx++; } exportReqDto.setExportApproverInfo(exportApproverInfoDto);
	 * 
	 * if(!exportReqDto.getTkoutReqstUserId().equals(null)) { UserInfoDto
	 * userInfoDto = userDao.getUserSeq(exportReqDto.getTkoutReqstUserId());
	 * 
	 * exportReqDto.setInsttNm(userInfoDto.getInsttNm());
	 * exportReqDto.setChargerNm(userInfoDto.getChargerNm());
	 * exportReqDto.setChargerMoblphonNo(userInfoDto.getChargerMoblphonNo());
	 * exportReqDto.setEmail(userInfoDto.getEmail()); }
	 * 
	 * exportReqDto.setUserId(AmcUtil.getPrincipal().getUserId());
	 * 
	 * return exportReqDto; }
	 */
	/*
	 * @Override // 팝업 public List<ExportReqDto> getUseDataBoxList(ExportSearchDto
	 * exportSearchDto){
	 * 
	 * exportSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
	 * 
	 * List<ExportReqDto> exportReqDto =
	 * exportReqDao.inqUseDataBoxList(exportSearchDto);
	 * 
	 * 
	 * for(ExportReqDto exportReq : exportReqDto) { List<ExportApproverInfoDto>
	 * exportApproverInfoDto = exportReqDao.inqBucketList(exportReq.getDtbxSn());
	 * 
	 * exportReq.setExportApproverInfo(exportApproverInfoDto); }
	 * 
	 * return exportReqDto; }
	 * 
	 * @Override // 등록 public ExportReqDto regExport(ExportReqDto exportReqDto) {
	 * exportReqDto.setTkoutReqstUserId(AmcUtil.getPrincipal().getUserId());
	 * exportReqDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
	 * exportReqDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
	 * 
	 * exportReqDao.regExport(exportReqDto);
	 * 
	 * ExportServerInfoDto exportServerInfoDto = new
	 * ExportServerInfoDto(exportReqDto);
	 * 
	 * exportServerInfoDto.setTkoutReqstNo(exportReqDto.getTkoutReqstNo());
	 * 
	 * exportReqDao.regExportServerInfo(exportServerInfoDto);
	 * for(ExportApproverInfoDto exportApproverInfoDtos :
	 * exportReqDto.getExportApproverInfo() ) {
	 * 
	 * ExportApproverInfoDto exportApproverInfoDto = new ExportApproverInfoDto();
	 * 
	 * exportApproverInfoDto.setTkoutReqstNo(exportReqDto.getTkoutReqstNo());
	 * exportApproverInfoDto.setDissRspnberUserId(exportApproverInfoDtos.
	 * getDissRspnberUserId());
	 * exportReqDao.regExportApproverInfo(exportApproverInfoDto);
	 * 
	 * String template = "mail/exportAccept"; String subject =
	 * "[서울아산병원] 데이터박스 반출 신청"; String[] to = {
	 * userDao.getUserSeq(exportApproverInfoDtos.getDissRspnberUserId()).getEmail()}
	 * ; Context ctx = new Context(); ctx.setVariable("userName",
	 * exportReqDto.getChargerNm()); ctx.setVariable("exportReqSeq",
	 * exportReqDto.getTkoutReqstNo()); ctx.setVariable("dataBoxSeq",
	 * exportReqDto.getDtbxSn()); ctx.setVariable("dataBoxName",
	 * exportReqDto.getDtbxNm()); try { customMailSender.sendMail(template, subject,
	 * to, ctx); } catch (Exception e) {
	 * 
	 * } }
	 * 
	 * return exportReqDto; }
	 * 
	 * @Override public int cancelExport(ExportReqDto exportReqDto) {
	 * 
	 * return exportReqDao.cancelExport(exportReqDto); }
	 * 
	 * @Override public ExportReqDto myAdminExportAccept(ExportReqDto exportReqDto)
	 * {
	 * 
	 * exportReqDao.myAdminExportAccept(exportReqDto);
	 * exportReqDao.myExportAccept(exportReqDto.getTkoutReqstNo());
	 * 
	 * return exportReqDto; }
	 * 
	 * @Override public ExportApproverInfoDto myExportApprove(ExportApproverInfoDto
	 * exportApproverInfoDto) {
	 * 
	 * if(exportApproverInfoDto.getLastPang().equals("Y")) { ExportReqDto
	 * exportReqDto = new ExportReqDto();
	 * 
	 * exportReqDto.setTkoutReqstNo(exportApproverInfoDto.getTkoutReqstNo());
	 * exportReqDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
	 * 
	 * ExportReqDto exportServerInfoDto =
	 * exportReqDao.getExportSvrInfo(exportApproverInfoDto);
	 * 
	 * //어드민서버 마운트정보, SSH PORT정보 String filesystem = "/drAnswer/export"; int PORT =
	 * 22;
	 * 
	 * 
	 * Session session = null; Channel channel = null; ChannelSftp channelSftp =
	 * null;
	 * 
	 * String REMOTE_ADDR = exportServerInfoDto.getTkoutSvrIpAdres(); String
	 * USERNAME = exportServerInfoDto.getTkoutSvrUserId(); String PASSWORD =
	 * exportServerInfoDto.getTkoutSvrUserPassword(); try {
	 * 
	 * session = new JSch().getSession(USERNAME, REMOTE_ADDR, PORT);
	 * session.setPassword(PASSWORD); session.setConfig("StrictHostKeyChecking",
	 * "no"); // 호스트 정보를 검사하지 않도록 설정 session.connect(); session.setTimeout(5000);
	 * 
	 * channel = session.openChannel("sftp"); channel.connect(); channelSftp =
	 * (ChannelSftp) channel; } catch (Exception e) { throw new
	 * IllegalArgumentException("어드민서버에 접속할 수 없습니다. 접속정보를 확인해 주세요."); }
	 * 
	 * //NAS 복사 try {
	 * StringUtil.downloadDir(exportServerInfoDto.getTkoutTrgetCours() ,
	 * filesystem+"/"+exportServerInfoDto.getTkoutReqstNo() , channelSftp); } catch
	 * (Exception e) { throw new IllegalArgumentException("반출대상 파일 경로에 파일이 없습니다.");
	 * }
	 * 
	 * exportReqDao.ExportApprove(exportReqDto);
	 * exportReqDao.myExportApprove(exportApproverInfoDto); String template =
	 * "mail/exportApprove"; String subject = "[서울아산병원] 데이터박스 반출 신청 승인"; String[] to
	 * = {exportApproverInfoDto.getEmail()}; Context ctx = new Context();
	 * ctx.setVariable("userName", exportApproverInfoDto.getChargerNm());
	 * ctx.setVariable("exportReqSeq", exportApproverInfoDto.getTkoutReqstNo());
	 * ctx.setVariable("dataBoxSeq", exportApproverInfoDto.getDtbxSn());
	 * ctx.setVariable("dataBoxName", exportApproverInfoDto.getDtbxNm()); try {
	 * customMailSender.sendMail(template, subject, to, ctx); } catch (Exception e)
	 * { } } else { exportReqDao.myExportApprove(exportApproverInfoDto); } return
	 * exportApproverInfoDto; }
	 * 
	 * @Override public ExportApproverInfoDto myExportReject(ExportApproverInfoDto
	 * exportApproverInfoDto) { ExportReqDto exportReqDto = new ExportReqDto();
	 * 
	 * exportReqDao.myExportReject(exportApproverInfoDto);
	 * 
	 * exportReqDto.setTkoutReqstNo(exportApproverInfoDto.getTkoutReqstNo());
	 * exportReqDto.setRejectResn(exportApproverInfoDto.getRejectResn());
	 * 
	 * exportReqDao.ExportReject(exportReqDto);
	 * 
	 * String template = "mail/exportReject"; String subject =
	 * "[서울아산병원] 데이터박스 반출 신청 거절"; String[] to = {exportApproverInfoDto.getEmail()};
	 * Context ctx = new Context(); ctx.setVariable("userName",
	 * exportApproverInfoDto.getChargerNm()); ctx.setVariable("tkoutReqstNo",
	 * exportApproverInfoDto.getTkoutReqstNo()); ctx.setVariable("dataBoxSeq",
	 * exportApproverInfoDto.getDtbxSn()); ctx.setVariable("dataBoxName",
	 * exportApproverInfoDto.getDtbxNm()); ctx.setVariable("rejectReason",
	 * exportApproverInfoDto.getRejectResn()); try {
	 * customMailSender.sendMail(template, subject, to, ctx); } catch (Exception e)
	 * {
	 * 
	 * }
	 * 
	 * return exportApproverInfoDto; }
	 * 
	 * 
	 * public void myExportDownload(String exportReqSeq, HttpServletRequest request,
	 * HttpServletResponse response) throws IOException {
	 * 
	 * //반출 경로 String fileSystemSurfix = "/drAnswer/export";
	 * 
	 * String filesystem = fileSystemSurfix+"/"+exportReqSeq; String zipPath =
	 * fileSystemSurfix+"/"+exportReqSeq+".zip";
	 * 
	 * StringUtil.zip(filesystem, zipPath);
	 * 
	 * //파일다운로드 START response.setContentType("application/zip");
	 * response.setHeader("Content-Transfer-Encoding:", "binary");
	 * response.setHeader("Content-Disposition", "attachment; filename=" + zipPath);
	 * response.setHeader("Pragma", "no-cache"); response.setHeader("Expires",
	 * "-1"); // 다운로드 위해 추가. FileInputStream fiss = new FileInputStream(zipPath);
	 * BufferedInputStream bis = new BufferedInputStream(fiss); ServletOutputStream
	 * so = response.getOutputStream(); BufferedOutputStream bos = new
	 * BufferedOutputStream(so);
	 * 
	 * File fileSizeZip = new File(zipPath); byte[] data = new
	 * byte[(int)fileSizeZip.length()]; int input = 0; while((input =
	 * bis.read(data)) != -1) { bos.write(data, 0, input); bos.flush(); }
	 * 
	 * if (bos != null) bos.close(); if (bis != null) bis.close(); if (so != null)
	 * so.close(); if (fiss != null) fiss.close(); }
	 */

	

	
}
