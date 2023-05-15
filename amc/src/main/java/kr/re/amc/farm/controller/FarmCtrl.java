package kr.re.amc.farm.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.board.dto.FaqDto;
import kr.re.amc.board.dto.NoticeDto;
import kr.re.amc.cmm.dto.CodeDto;
import kr.re.amc.databox.service.DataBoxService;
import kr.re.amc.export.dto.ExportReqDto;
import kr.re.amc.farm.dto.FarmDto;
import kr.re.amc.farm.dto.FarmSearchDto;
import kr.re.amc.farm.service.FarmService;
import kr.re.amc.users.service.UserService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class FarmCtrl {

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "dataBoxService")
	private DataBoxService dataBoxService;

	/**
	 * 데이터 저장 신청 목록
	 * 
	 * @methodName : getReqStorageList
	 * @date : 2021-06-24 오후 2:33
	 * @author : xeroman.k
	 * @param reqStorageStatCode
	 * @param dataName
	 * @param pageable
	 * @return :
	 *         com.itsm.dranswer.utils.ApiUtils.ApiResult<org.springframework.data.domain.Page<com.itsm.dranswer.storage.ReqStorageInfoDto>>
	 * @throws
	 * @modifyed :
	 *
	 **/
	@Resource(name = "FarmService")
	private FarmService farmService;

	@GetMapping(value = "/farm/list/main")
	public ApiResult<PageInfo<FarmDto>> farmList(FarmSearchDto farmSearchDto) {

		PageHelper.startPage(farmSearchDto.getPage(), farmSearchDto.getSize());
		List<FarmDto> farmList = farmService.farmList(farmSearchDto);
		PageInfo<FarmDto> pageData = new PageInfo<>(farmList);
		return success(pageData);

	}
	
	@GetMapping(value = "/farm/list/agencyUserList")
	public ApiResult<PageInfo<FarmDto>> agencyUserList(FarmSearchDto farmSearchDto) {

		PageHelper.startPage(farmSearchDto.getPage(), farmSearchDto.getSize());
		List<FarmDto> agencyUserList = farmService.agencyUserList(farmSearchDto);
		PageInfo<FarmDto> pageData = new PageInfo<>(agencyUserList);
		return success(pageData);

	}
	
	@GetMapping(value = "/farm/list/growUserList")
	public ApiResult<PageInfo<FarmDto>> growUserList(FarmSearchDto farmSearchDto) {

		PageHelper.startPage(farmSearchDto.getPage(), farmSearchDto.getSize());
		List<FarmDto> growUserList = farmService.growUserList(farmSearchDto);
		PageInfo<FarmDto> pageData = new PageInfo<>(growUserList);
		return success(pageData);

	}
	
	@PostMapping(path = "/farm/reg")
	public ApiResult<FarmDto> regFarm(@RequestBody FarmDto farmDto) {
		//farmDto.setChgUsrId(AmcUtil.getPrincipal().getUserId());
		//farmDto.setCrtUsrId(AmcUtil.getPrincipal().getUserId());
		
		return success(farmService.regFarm(farmDto));
	}
	
	@PostMapping(path = "/grow/reg")
	public ApiResult<FarmDto> regGrow(@RequestBody FarmDto farmDto) {
		//farmDto.setChgUsrId(AmcUtil.getPrincipal().getUserId());
		//farmDto.setCrtUsrId(AmcUtil.getPrincipal().getUserId());
		
		return success(farmService.regGrow(farmDto));
	}
	
	@GetMapping(value = "/farm/list/grow")
	public ApiResult<PageInfo<FarmDto>> growList(FarmSearchDto farmSearchDto) {

		PageHelper.startPage(farmSearchDto.getPage(), farmSearchDto.getSize());
		List<FarmDto> growList = farmService.growList(farmSearchDto);
		PageInfo<FarmDto> pageData = new PageInfo<>(growList);
		return success(pageData);

	}
	
	@GetMapping(value = "/get/farmCodeList")
    public ApiResult<List<CodeDto>> getFarmIdList(FarmSearchDto farmSearchDto){

    	List<CodeDto> codes = new ArrayList<>();
        codes  = farmService.farmIdList();

        return success(codes);
    }	
	
	@GetMapping(value = "/get/growCodeList")
    public ApiResult<List<CodeDto>> getGrowCodeList(FarmSearchDto farmSearchDto){

    	List<CodeDto> codes = new ArrayList<>();
        codes  = farmService.growCodeList();

        return success(codes);
    }	

}
