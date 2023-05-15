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
import kr.re.amc.cmm.dto.CodeDto;
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
	public List<FarmDto> agencyUserList(FarmSearchDto farmSearchDto) {
		// TODO Auto-generated method stub
		return farmDao.agencyUserList(farmSearchDto);
	}
	
	@Override
	public List<FarmDto> growUserList(FarmSearchDto farmSearchDto) {
		// TODO Auto-generated method stub
		return farmDao.growUserList(farmSearchDto);
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
	
	@Override
	public FarmDto regGrow(FarmDto farmDto) {
		//farmDto.setCrtUsrId(AmcUtil.getPrincipal().getUserId());
		//farmDto.setChgUsrId(AmcUtil.getPrincipal().getUserId());

		farmDao.regGrow(farmDto);
		
		return farmDto;
	}

	@Override
	public List<CodeDto> farmIdList() {
		return farmDao.farmIdList();
	}
	
	@Override
	public List<CodeDto> growCodeList() {
		return farmDao.growCodeList();
	}

	
}
