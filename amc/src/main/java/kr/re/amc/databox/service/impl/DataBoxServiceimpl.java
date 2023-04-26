package kr.re.amc.databox.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.thymeleaf.context.Context;

import com.github.pagehelper.PageHelper;

import kr.re.amc.apis.ApiService;
import kr.re.amc.commons.CustomMailSender;
import kr.re.amc.databox.dao.DataBoxDao;
import kr.re.amc.databox.dto.DataBoxSearchDto;
import kr.re.amc.databox.dto.DtbxBlckStrgeReqMstDto;
import kr.re.amc.databox.dto.DtbxDataReqMstDto;
import kr.re.amc.databox.dto.DtbxReqMstDto;
import kr.re.amc.databox.dto.DtbxSvrReqMstDto;
import kr.re.amc.databox.dto.DtbxUserReqMstDto;
import kr.re.amc.databox.service.DataBoxService;
import kr.re.amc.storage.dto.StrgeUseInfDto;
import kr.re.amc.users.dto.UserInfoDto;
import kr.re.amc.users.service.UserService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.util.CustomObjectStorage;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service("dataBoxService")
@Transactional
public class DataBoxServiceimpl  extends EgovAbstractServiceImpl implements DataBoxService {

	
    @Value("${spring.mail.username}")
    private String adminMail;
	
    @Value("${ncp.api.domain}")
    private String apiDomain;
    
    @Value("${ncp.amc.end-point}")
    private String endPoint;
    @Value("${ncp.amc.region-name}")
    private String regionName;
    @Value("${ncp.amc.access-key}")
    private String laifAccessKey;
    @Value("${ncp.amc.secret-key}")
    private String laifSecretKey;
    @Value("${ncp.amc.server-access-key}")
    private String laifServerAccessKey;
    @Value("${ncp.amc.server-secret-key}")
    private String laifServerSecretKey;
    
    
    @Resource(name="userService")
    private UserService userService;
    
    
	@Resource(name="dataBoxDao")
	private DataBoxDao dataBoxDao;
	
	@Autowired
	private  CustomMailSender customMailSender;
    
	@Autowired
    private  CustomObjectStorage customObjectStorage;
    

	@Override
	public List<DtbxReqMstDto> getDataBoxList(DataBoxSearchDto dataBoxSearchDto) {
		
		
		if(!AmcUtil.isAadmin()){
            UserInfoDto userInfoDto = userService.getOriginUser(AmcUtil.getPrincipal().getUserId() );
            dataBoxSearchDto.setUserId(userInfoDto.getUserId());
		}
		
		PageHelper.startPage(dataBoxSearchDto.getPage() , dataBoxSearchDto.getSize());
		
		return dataBoxDao.inqReqStorageList(dataBoxSearchDto);
		
		
	}
	
	@Override
	public HashMap getReqImageList() {

		  ResponseEntity<HashMap> response = null;
          try {
      		/* nCloud fileSafer 파일전송 API */
            /***************************************************************/
            String apiURL = "/vserver/v2/getServerImageProductList"; 
            
        	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        	
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURL)
            	            .queryParam("responseFormatType", "json")
            	            .queryParam("isPartner", "true");
            	    params.forEach(uriBuilder::queryParam);
           
			HttpHeaders chkHeaders =  ApiService.getNcloudUserApiHeader(HttpMethod.GET, uriBuilder.toUriString(), laifAccessKey, laifSecretKey);
            

            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(apiDomain+uriBuilder.toUriString(), HttpMethod.GET, new HttpEntity<>(chkHeaders), HashMap.class);            
            
            log.info("CODE::"+response.getBody());
            log.info("상태::"+response.getBody().get("getServerImageProductListResponse"));	
      	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          HashMap respMap = response.getBody();
          
		return respMap;
	
	
	}

	@Override
	public HashMap getReqServerList(String data) {
		  ResponseEntity<HashMap> response = null;
          try {
      		/* nCloud fileSafer 파일전송 API */
            /***************************************************************/
            String apiURL = "/vserver/v2/getServerProductList"; 
            
        	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        	
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(apiURL)
            	            .queryParam("responseFormatType", "json")
            	            .queryParam("isPartner", "true")
            				.queryParam("regionCode", "KR")
            				.queryParam("serverImageProductCode", data);
            	    params.forEach(uriBuilder::queryParam);
           
			HttpHeaders chkHeaders =  ApiService.getNcloudUserApiHeader(HttpMethod.GET, uriBuilder.toUriString(), laifAccessKey, laifSecretKey);
            

            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(apiDomain+uriBuilder.toUriString(), HttpMethod.GET, new HttpEntity<>(chkHeaders), HashMap.class);            
            
            log.info("CODE::"+response.getBody());
            log.info("상태::"+response.getBody().get("getServerProductListResponse"));	
      	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
          HashMap respMap = response.getBody();
          
		return respMap;
	}

	@Override
	public List<StrgeUseInfDto> getUseStorageList(DataBoxSearchDto dataBoxSearchDto) {
		
		dataBoxSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
		dataBoxSearchDto.setUseReqstSttusSecd("U_ACC");
		
		PageHelper.startPage(dataBoxSearchDto.getPage() , dataBoxSearchDto.getSize());
		
		return dataBoxDao.getUseStorageList(dataBoxSearchDto);
	}

	@Override
	public DtbxReqMstDto reqDataBox(DtbxReqMstDto dtbxReqMstDto) {
		
		
		dtbxReqMstDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
		dataBoxDao.regDtbxMst(dtbxReqMstDto);
		dataBoxDao.regDtbxReqMst(dtbxReqMstDto);
		
		
		
		for(DtbxUserReqMstDto dtbxUserReqMstDto : dtbxReqMstDto.getUserConList()){ 
			
			dtbxUserReqMstDto.setDtbxSn(dtbxReqMstDto.getDtbxSn());
			dtbxUserReqMstDto.setDtbxReqNo(dtbxReqMstDto.getDtbxReqNo());
			dtbxUserReqMstDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			
			dataBoxDao.regDtbxUserReqMst(dtbxUserReqMstDto);
			dataBoxDao.regDtbxUserDtl(dtbxUserReqMstDto);
		}
		
		
		for(DtbxSvrReqMstDto dtbxSvrReqMstDto : dtbxReqMstDto.getServerList()){ 
			
			
			dtbxSvrReqMstDto.setDtbxSn(dtbxReqMstDto.getDtbxSn());
			dtbxSvrReqMstDto.setDtbxReqNo(dtbxReqMstDto.getDtbxReqNo());
			dtbxSvrReqMstDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			
			dataBoxDao.regDtbxSvrReqMst(dtbxSvrReqMstDto);
			dataBoxDao.regDtbxSvrDtl(dtbxSvrReqMstDto);
			
			
			for(DtbxBlckStrgeReqMstDto dtbxBlckStrgeReqMstDto : dtbxSvrReqMstDto.getStorageList()){
				
				dtbxBlckStrgeReqMstDto.setSvrSn(dtbxSvrReqMstDto.getSvrSn());
				dtbxBlckStrgeReqMstDto.setDtbxReqNo(dtbxReqMstDto.getDtbxReqNo());
				dtbxBlckStrgeReqMstDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
				dataBoxDao.regDtbxBlckStrgeReqMst(dtbxBlckStrgeReqMstDto);
				
			}
			
			
		}
		
		
		for(DtbxDataReqMstDto dtbxDataReqMstDto : dtbxReqMstDto.getBucketList()){ //연구분석데이터신청
			dtbxDataReqMstDto.setDtbxReqNo(dtbxReqMstDto.getDtbxReqNo());
			dtbxDataReqMstDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			
			dataBoxDao.regDtbxDataReqMst(dtbxDataReqMstDto);
			
        }
		
		
		String template = "mail/dataBoxRequest";
        String subject = "[서울아산병원] 데이터박스 사용 신청";
        String[] to = {adminMail};
        Context ctx = new Context();
        ctx.setVariable("userName", dtbxReqMstDto.getChargerNm());
        ctx.setVariable("dataBoxReqSeq", dtbxReqMstDto.getDtbxReqNo());
        ctx.setVariable("dataBoxSeq", dtbxReqMstDto.getDtbxSn());
        ctx.setVariable("dataBoxName", dtbxReqMstDto.getDtbxNm());
        try {
			customMailSender.sendMail(template, subject, to, ctx);
		} catch (Exception e) {
		}
		
		return dtbxReqMstDto;
	}

	@Override
	public DtbxReqMstDto getDataBoxDetailInfo(Long dtbxReqNo) {
		
		
		DtbxReqMstDto DtbxReqMstDto = new DtbxReqMstDto();
		List<DtbxDataReqMstDto> dtbxDataReqMstDto;
		
		if(AmcUtil.isAadmin()) {
			DtbxReqMstDto = dataBoxDao.inqDtbxReqMstAdm(dtbxReqNo);
			dtbxDataReqMstDto = dataBoxDao.inqDtbxDataReqMstAdm(dtbxReqNo);
		} else {
			DtbxReqMstDto = dataBoxDao.inqDtbxReqMst(dtbxReqNo);
			dtbxDataReqMstDto = dataBoxDao.inqDtbxDataReqMst(dtbxReqNo);
			
		}
		
		
		DtbxReqMstDto.setBucketList(dtbxDataReqMstDto);
		
		
		List<DtbxUserReqMstDto> dtbxUserReqMstDto = dataBoxDao.inqDtbxUserReqMst(dtbxReqNo);
		DtbxReqMstDto.setUserConList(dtbxUserReqMstDto);
		
		List<DtbxSvrReqMstDto> dtbxSvrReqMstDto = dataBoxDao.inqDtbxSvrReqMst(dtbxReqNo);

		for ( DtbxSvrReqMstDto rtnDtbxSvrReqMstDto : dtbxSvrReqMstDto ) {
			List<DtbxBlckStrgeReqMstDto> dtbxBlckStrgeReqMstDto = dataBoxDao.inqDtbxBlckStrgeReqMst(rtnDtbxSvrReqMstDto);
			rtnDtbxSvrReqMstDto.setStorageList(dtbxBlckStrgeReqMstDto);
		}
		
		DtbxReqMstDto.setServerList(dtbxSvrReqMstDto);
		
		
		DtbxReqMstDto.setBucketList(dtbxDataReqMstDto);
		
		return DtbxReqMstDto;
	}

	@Override
	public DtbxReqMstDto myAdminAccept(DtbxReqMstDto dtbxReqMstDto) {
		
		DtbxReqMstDto searchDtbxReqMstDto = Optional.ofNullable(dataBoxDao.inqDtbxReqMst(dtbxReqMstDto.getDtbxReqNo())).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보 입니다."));
		
		if("USE".equals(searchDtbxReqMstDto.getDtbxReqTyCd())) {
			
			dtbxReqMstDto.setDtbxReqstSttusCd("ACCEPT");
			dtbxReqMstDto.setRceptDt(LocalDateTime.now().withNano(0).toString());
			
		} else {
			
			dtbxReqMstDto.setDtbxReqstSttusCd("ACCEPT");
			dtbxReqMstDto.setDeleteRceptDt(LocalDateTime.now().withNano(0).toString());
			
		}
		
		
		dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		dataBoxDao.updtDtbxReqMst(dtbxReqMstDto);
		
		String template = "mail/dataBoxAccept";
        String subject = "USE".equals(searchDtbxReqMstDto.getDtbxReqTyCd()) ? "[서울아산병원] 데이터박스 사용 신청 접수 안내" : "[서울아산병원] 데이터박스 삭제 신청 접수 안내";
        String[] to = {dtbxReqMstDto.getEmail()};
        Context ctx = new Context();
        ctx.setVariable("dataBoxType", "USE".equals(searchDtbxReqMstDto.getDtbxReqTyCd()) ? "사용" : "삭제");
        ctx.setVariable("userName", dtbxReqMstDto.getChargerNm());
        ctx.setVariable("dataBoxReqSeq", dtbxReqMstDto.getDtbxReqNo());
        ctx.setVariable("dataBoxSeq", dtbxReqMstDto.getDtbxSn());
        ctx.setVariable("dataBoxName", dtbxReqMstDto.getDtbxNm());
        try {
			customMailSender.sendMail(template, subject, to, ctx);
		} catch (Exception e) {
		}
		
		
		return dtbxReqMstDto;
	}

	@Override
	public DtbxReqMstDto myAdminApprove(DtbxReqMstDto dtbxReqMstDto) {
		
		DtbxReqMstDto searchDtbxReqMstDto = Optional.ofNullable(dataBoxDao.inqDtbxReqMst(dtbxReqMstDto.getDtbxReqNo())).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보 입니다."));
		
		
		
		if("USE".equals(searchDtbxReqMstDto.getDtbxReqTyCd())) {
			
			dtbxReqMstDto.setDtbxSttusCd("01"); //01:운영중, 02:삭제
			dtbxReqMstDto.setUseBeginDt(LocalDateTime.now().withNano(0).toString());
			dtbxReqMstDto.setUseEndDt(LocalDateTime.now().withNano(0).plusDays(9999).toString());
			
			//DTBX_MST Update
			dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
			dataBoxDao.updtDtbxMst(dtbxReqMstDto);
			
			for(DtbxUserReqMstDto dtbxUserReqMstDto : dtbxReqMstDto.getUserConList()) {
				
				//TB_DTBX_USER_DTL Update
				dtbxUserReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
				dataBoxDao.updtDtbxUserDtl(dtbxUserReqMstDto);
			}
			
			for(DtbxSvrReqMstDto dtbxSvrReqMstDto : dtbxReqMstDto.getServerList()) {
				
				//TB_DTBX_USER_DTL Update
				dtbxSvrReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
				dataBoxDao.updtDtbxSvrDtl(dtbxSvrReqMstDto);
			}
			
			
			if( "approve".equals(dtbxReqMstDto.getStateCode()) ){
				dtbxReqMstDto.setDtbxReqstSttusCd("APPROVE");
				dtbxReqMstDto.setProcessDt(LocalDateTime.now().withNano(0).toString());
				
			} else if( "create".equals(dtbxReqMstDto.getStateCode())){
				dtbxReqMstDto.setDtbxReqstSttusCd("CREATE");
				dtbxReqMstDto.setProcessDt(LocalDateTime.now().withNano(0).toString());
			}
			
			dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
			dataBoxDao.updtDtbxReqMst(dtbxReqMstDto);
			
			
			
		} else {
			
			dtbxReqMstDto.setDtbxReqstSttusCd("APPROVE");
			dtbxReqMstDto.setDeleteProcessDt(LocalDateTime.now().withNano(0).toString());
			dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
			dataBoxDao.updtDtbxReqMst(dtbxReqMstDto);
		}
		
		
		//승인처리.
		if( "approve".equals(dtbxReqMstDto.getStateCode()) ){
			String template = "mail/dataBoxApprove";
	        String subject = "USE".equals(searchDtbxReqMstDto.getDtbxReqTyCd()) ? "[서울아산병원] 데이터박스 사용 신청 승인 안내" : "[서울아산병원] 데이터박스 삭제 신청 승인 안내";
	        String[] to = {dtbxReqMstDto.getEmail()};
	        Context ctx = new Context();
	        ctx.setVariable("userName", dtbxReqMstDto.getChargerNm());
	        ctx.setVariable("dataBoxReqSeq", dtbxReqMstDto.getDtbxReqNo());
	        ctx.setVariable("dataBoxSeq", dtbxReqMstDto.getDtbxSn());
	        ctx.setVariable("dataBoxName", dtbxReqMstDto.getDtbxNm());
	        ctx.setVariable("dataBoxType", "USE".equals(searchDtbxReqMstDto.getDtbxReqTyCd()) ? "사용" : "삭제");
	        
	        try {
				customMailSender.sendMail(template, subject, to, ctx);
			} catch (Exception e) {
			}
		}
		
		
		//서버생성 처리
		else if( "create".equals(dtbxReqMstDto.getStateCode()) ){
			
			String template = "mail/dataBoxCreate";
	        String subject = "[서울아산병원] 데이터박스 서버생성 완료 안내";
	        String[] to = {dtbxReqMstDto.getEmail()};
	        Context ctx = new Context();
	        ctx.setVariable("userName", dtbxReqMstDto.getChargerNm());
	        ctx.setVariable("dataBoxReqSeq", dtbxReqMstDto.getDtbxReqNo());
	        ctx.setVariable("dataBoxSeq", dtbxReqMstDto.getDtbxSn());
	        ctx.setVariable("dataBoxName", dtbxReqMstDto.getDtbxNm());
	        ctx.setVariable("dataBoxType", "USE".equals(searchDtbxReqMstDto.getDtbxReqTyCd()) ? "사용" : "삭제");
	        
	        try {
				customMailSender.sendMail(template, subject, to, ctx);
			} catch (Exception e) {
			}
			
		}		
		
		
		return dtbxReqMstDto;
	}

	@Override
	public DtbxReqMstDto myBoxComplete(DtbxReqMstDto dtbxReqMstDto) {
		
		DtbxReqMstDto searchDtbxReqMstDto = Optional.ofNullable(dataBoxDao.inqDtbxReqMst(dtbxReqMstDto.getDtbxReqNo())).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보 입니다."));
		
		dtbxReqMstDto.setDtbxReqstSttusCd("COMPLETE");
		dtbxReqMstDto.setProcessDt(LocalDateTime.now().withNano(0).toString());
		dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		
		dataBoxDao.updtDtbxReqMst(dtbxReqMstDto);
		
		String template = "mail/dataBoxComplete";
        String subject = "[서울아산병원] 데이터박스 설치완료";
        String[] to = {adminMail};
        Context ctx = new Context();
        ctx.setVariable("userName", searchDtbxReqMstDto.getChargerNm());
        ctx.setVariable("dataBoxReqSeq", dtbxReqMstDto.getDtbxReqNo());
        ctx.setVariable("dataBoxSeq", dtbxReqMstDto.getDtbxSn());
        ctx.setVariable("dataBoxName", searchDtbxReqMstDto.getDtbxNm());
        try {
			customMailSender.sendMail(template, subject, to, ctx);
		} catch (Exception e) {
		}
		
		
		return null;
	}

	@Override
	public DtbxReqMstDto myBoxCancel(DtbxReqMstDto dtbxReqMstDto) {
		DtbxReqMstDto searchDtbxReqMstDto = Optional.ofNullable(dataBoxDao.inqDtbxReqMst(dtbxReqMstDto.getDtbxReqNo())).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보 입니다."));
		
		String subject = "[서울아산병원] 데이터박스 사용신청취소";
		String str = "사용";
		
		if("USE".equals(searchDtbxReqMstDto.getDtbxReqTyCd())) {
			
			dtbxReqMstDto.setDtbxReqstSttusCd("CANCEL");
			dtbxReqMstDto.setCanclDt(LocalDateTime.now().withNano(0).toString());
			dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
			
			dataBoxDao.updtDtbxReqMst(dtbxReqMstDto);
		} else {
			
			str = "삭제";
			subject = "[서울아산병원] 데이터박스 삭제신청취소";
			//삭제 취소
			//이전 상태코드
			dtbxReqMstDto.setDtbxReqTyCd("USE");
			dtbxReqMstDto.setDtbxReqstSttusCd(searchDtbxReqMstDto.getTransrDtbxReqstSttusCd());
			dtbxReqMstDto.setDeleteCanclDt(LocalDateTime.now().withNano(0).toString());
			dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
			
			dataBoxDao.updtDtbxReqMst(dtbxReqMstDto);
		}
		
		
		String template = "mail/dataBoxRequestCancel";
        String[] to = {adminMail};
        Context ctx = new Context();
        ctx.setVariable("str", str);
        ctx.setVariable("userName", dtbxReqMstDto.getChargerNm());
        ctx.setVariable("dataBoxReqSeq", dtbxReqMstDto.getDtbxReqNo());
        ctx.setVariable("dataBoxSeq", dtbxReqMstDto.getDtbxSn());
        ctx.setVariable("dataBoxName", dtbxReqMstDto.getDtbxNm());
        try {
			customMailSender.sendMail(template, subject, to, ctx);
		} catch (Exception e) {
		}
		
		
		
		return dtbxReqMstDto;
	}

	@Override
	public DtbxReqMstDto myBoxDelete(DtbxReqMstDto dtbxReqMstDto) {
		DtbxReqMstDto searchDtbxReqMstDto = Optional.ofNullable(dataBoxDao.inqDtbxReqMst(dtbxReqMstDto.getDtbxReqNo())).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보 입니다."));
		
		
		dtbxReqMstDto.setDtbxReqTyCd("DEL");
		dtbxReqMstDto.setDtbxReqstSttusCd("REQUEST");
		dtbxReqMstDto.setTransrDtbxReqstSttusCd(searchDtbxReqMstDto.getDtbxReqstSttusCd());
		dtbxReqMstDto.setDeleteReqstDt(LocalDateTime.now().withNano(0).toString()); //삭제요청일시
		dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		
		dataBoxDao.updtDtbxReqMstDel(dtbxReqMstDto); //삭제전용
		
		String template = "mail/dataBoxDelete";
        String subject = "[서울아산병원] 데이터박스 삭제 신청";
        String[] to = {adminMail};
        Context ctx = new Context();
        ctx.setVariable("userName", dtbxReqMstDto.getChargerNm());
        ctx.setVariable("dataBoxReqSeq", dtbxReqMstDto.getDtbxReqNo());
        ctx.setVariable("dataBoxSeq", dtbxReqMstDto.getDtbxSn());
        ctx.setVariable("dataBoxName", dtbxReqMstDto.getDtbxNm());;
        try {
			customMailSender.sendMail(template, subject, to, ctx);
		} catch (Exception e) {
		}		
		
		return dtbxReqMstDto;
	}

	@Override
	public DtbxReqMstDto myAdminReject(DtbxReqMstDto dtbxReqMstDto) {
		DtbxReqMstDto searchDtbxReqMstDto = Optional.ofNullable(dataBoxDao.inqDtbxReqMst(dtbxReqMstDto.getDtbxReqNo())).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 정보 입니다."));
		
		String subject = "[서울아산병원] 데이터박스 사용 신청 거절 안내";
		String str = "사용";
		
		if("USE".equals(searchDtbxReqMstDto.getDtbxReqTyCd())) {
			
			if("REQUEST".equals(searchDtbxReqMstDto.getDtbxReqstSttusCd())) { //신청 >> 거절
				
				dtbxReqMstDto.setDtbxReqstSttusCd("REJECT");
				dtbxReqMstDto.setRceptDt(LocalDateTime.now().withNano(0).toString());
				dtbxReqMstDto.setProcessDt(LocalDateTime.now().withNano(0).toString());
				dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
				
				dataBoxDao.updtDtbxReqMst(dtbxReqMstDto);
				
			} else if("ACCEPT".equals(searchDtbxReqMstDto.getDtbxReqstSttusCd())) { //접수 >> 거절
			
				dtbxReqMstDto.setDtbxReqstSttusCd("REJECT");
				dtbxReqMstDto.setRceptDt(LocalDateTime.now().withNano(0).toString());
				dtbxReqMstDto.setProcessDt(LocalDateTime.now().withNano(0).toString());
				dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
				
				dataBoxDao.updtDtbxReqMst(dtbxReqMstDto);
				
			}
			
			
		} else {
				
				str = "삭제";
				subject = "[서울아산병원] 데이터박스 삭제 신청 거절 안내";
				
				dtbxReqMstDto.setDtbxReqTyCd("USE");
				dtbxReqMstDto.setDtbxReqstSttusCd(searchDtbxReqMstDto.getTransrDtbxReqstSttusCd());
				dtbxReqMstDto.setRceptDt(LocalDateTime.now().withNano(0).toString());
				dtbxReqMstDto.setProcessDt(LocalDateTime.now().withNano(0).toString());
				dtbxReqMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
				
				dataBoxDao.updtDtbxReqMst(dtbxReqMstDto);
				
			}
			
			dataBoxDao.regDtbxHst(dtbxReqMstDto.getDtbxReqNo()); //히스토리
			
			
			String template = "mail/dataBoxReject";
	        String[] to = {dtbxReqMstDto.getEmail()};
	        Context ctx = new Context();
	        ctx.setVariable("userName", dtbxReqMstDto.getChargerNm());
	        ctx.setVariable("dataBoxReqSeq", dtbxReqMstDto.getDtbxReqNo());
	        ctx.setVariable("dataBoxSeq", dtbxReqMstDto.getDtbxSn());
	        ctx.setVariable("dataBoxName", dtbxReqMstDto.getDtbxNm());;
	        ctx.setVariable("rejectReason", dtbxReqMstDto.getRejectResn());
	        ctx.setVariable("str", str);
	        
	        try {
				customMailSender.sendMail(template, subject, to, ctx);
			} catch (Exception e) {
			}
			
			
		
		
		return dtbxReqMstDto;
	}



    
}
	

