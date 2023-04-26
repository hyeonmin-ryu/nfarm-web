package kr.re.amc.farm.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.board.dto.FaqDto;
import kr.re.amc.databox.service.DataBoxService;
import kr.re.amc.export.dto.ExportReqDto;
import kr.re.amc.farm.dto.FarmDto;
import kr.re.amc.farm.dto.FarmSearchDto;
import kr.re.amc.farm.service.FarmService;
import kr.re.amc.users.service.UserService;
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
		System.out.println("널체크1");
		List<FarmDto> farmList = farmService.FarmList(farmSearchDto);
		System.out.println("널체크2");
		PageInfo<FarmDto> pageData = new PageInfo<>(farmList);
		System.out.println("널체크3");
		return success(pageData);

	}
	
	@GetMapping(value = "/farm/getList")
	public ApiResult<PageInfo<FarmDto>> farmList2(FarmSearchDto farmSearchDto) {

		List<FarmDto> pageDataBoxList = farmService.getFarmList(farmSearchDto); 
		PageInfo<FarmDto> pageData = new PageInfo<>(pageDataBoxList);
		return success(pageData); 
	}
	
	/*
	 * @GetMapping(value = "/data/box/user") public ApiResult<UserInfoDto>
	 * getReqUser() {
	 * 
	 * UserInfoDto userInfoDto =
	 * userService.getUserSeq(AmcUtil.getPrincipal().getUserId());
	 * 
	 * return success(userInfoDto); }
	 * 
	 * 
	 * @GetMapping(value = "/data/box/image") public ApiResult<HashMap>
	 * getImageList() {
	 * 
	 * HashMap results = dataBoxService.getReqImageList();
	 * 
	 * return success(results); }
	 * 
	 * 
	 * @GetMapping(value = "/data/box/server") public ApiResult<HashMap>
	 * getServerList(@RequestParam(required = true) String data) throws Exception {
	 * 
	 * HashMap results = dataBoxService.getReqServerList(data);
	 * 
	 * return success(results); }
	 * 
	 * 
	 * @GetMapping(value = "/data/box/use/list") public
	 * ApiResult<PageInfo<StrgeUseInfDto>> getUseStorageList( DataBoxSearchDto
	 * dataBoxSearchDto){
	 * 
	 * List<StrgeUseInfDto> pageDataBoxList =
	 * dataBoxService.getUseStorageList(dataBoxSearchDto);
	 * 
	 * PageInfo<StrgeUseInfDto> pageData = new PageInfo<>(pageDataBoxList);
	 * 
	 * return success(pageData); }
	 * 
	 * 
	 * @PostMapping(value = "/data/box/reg") public ApiResult<DtbxReqMstDto>
	 * reqDataBox(@RequestBody DtbxReqMstDto dtbxReqMstDto) throws Exception {
	 * 
	 * dtbxReqMstDto.setReqUserId(AmcUtil.getPrincipal().getUserId());
	 * 
	 * return success(dataBoxService.reqDataBox(dtbxReqMstDto)); }
	 * 
	 * 
	 *//**
		 * 상세정보
		 * 
		 * @methodName : myInfo
		 * @date : 2021-07-06 오후 5:44
		 * @author : xeroman.k
		 * @param dataBoxReqSeq
		 * @return :
		 *         com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.users.UserInfoDto>
		 * @throws
		 * @modifyed :
		 *
		 **//*
			 * @GetMapping(value = "/mypage/data/req/{dtbxReqNo:.+(?<!\\.js)$}") public
			 * ApiResult<DtbxReqMstDto> dataBoxReq(@PathVariable Long dtbxReqNo){ return
			 * success(dataBoxService.getDataBoxDetailInfo(dtbxReqNo)); }
			 * 
			 * 
			 * @PostMapping(value = "/my/admin/databox/accept") public
			 * ApiResult<DtbxReqMstDto> myAdminAccept(@RequestBody DtbxReqMstDto
			 * dtbxReqMstDto) throws Exception { return
			 * success(dataBoxService.myAdminAccept(dtbxReqMstDto)); }
			 * 
			 * 
			 * @PostMapping(value = "/my/admin/databox/reg") public ApiResult<DtbxReqMstDto>
			 * myAdminApprove(@RequestBody DtbxReqMstDto dtbxReqMstDto) throws Exception {
			 * return success(dataBoxService.myAdminApprove(dtbxReqMstDto)); }
			 * 
			 * 
			 * @PostMapping(value = "/my/databox/complete") public ApiResult<DtbxReqMstDto>
			 * myBoxComplete(@RequestBody DtbxReqMstDto dtbxReqMstDto) throws Exception {
			 * return success(dataBoxService.myBoxComplete(dtbxReqMstDto)); }
			 * 
			 * @PostMapping(value = "/my/databox/cancel") public ApiResult<DtbxReqMstDto>
			 * myBoxCancel(@RequestBody DtbxReqMstDto dtbxReqMstDto) throws Exception {
			 * return success(dataBoxService.myBoxCancel(dtbxReqMstDto)); }
			 * 
			 * @PostMapping(value = "/my/databox/delete") public ApiResult<DtbxReqMstDto>
			 * myBoxDelete(@RequestBody DtbxReqMstDto dtbxReqMstDto) throws Exception {
			 * return success(dataBoxService.myBoxDelete(dtbxReqMstDto)); }
			 * 
			 * 
			 * @PostMapping(value = "/my/admin/databox/reject") public
			 * ApiResult<DtbxReqMstDto> myAdminReject(@RequestBody DtbxReqMstDto
			 * dtbxReqMstDto) throws Exception { return
			 * success(dataBoxService.myAdminReject(dtbxReqMstDto)); }
			 * 
			 */

}
