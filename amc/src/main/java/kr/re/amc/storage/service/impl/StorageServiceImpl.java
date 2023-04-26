package kr.re.amc.storage.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.github.pagehelper.PageHelper;

import kr.re.amc.commons.CustomMailSender;
import kr.re.amc.etc.FileUploadResponse;
import kr.re.amc.etc.FileUploadResponse.FileObject;
import kr.re.amc.etc.RequestObjectDto;
import kr.re.amc.storage.dao.DtbxBucketMstDao;
import kr.re.amc.storage.dao.MyStorageInfoDao;
import kr.re.amc.storage.dao.StrgeOthbcReqstInfDao;
import kr.re.amc.storage.dao.StrgeReqInfDao;
import kr.re.amc.storage.dao.StrgeUseInfDao;
import kr.re.amc.storage.dto.DtbxBucketMstDto;
import kr.re.amc.storage.dto.StrgeOthbcReqstInfDto;
import kr.re.amc.storage.dto.StrgeReqInfDto;
import kr.re.amc.storage.dto.S3ObjectDto;
import kr.re.amc.storage.dto.StorageSearchDto;
import kr.re.amc.storage.dto.StrgeUseInfDto;
import kr.re.amc.storage.service.StorageService;
import kr.re.amc.users.dao.UserDao;
import kr.re.amc.users.dto.UserInfoDto;
import kr.re.amc.users.service.UserService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.util.CustomObjectStorage;

@Service("storageService")
public class StorageServiceImpl extends EgovAbstractServiceImpl implements StorageService {
	
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
    
    @Value("${ncp.amc.server-upload}")
    private String laifServerUpload;
    
	
	@Resource(name="strgeReqInfDao")
	private StrgeReqInfDao strgeReqInfDao;
	
	@Resource(name="strgeOthbcReqstInfDao")
	private StrgeOthbcReqstInfDao strgeOthbcReqstInfDao;
	
	@Resource(name="strgeUseInfDao")
	private StrgeUseInfDao strgeUseInfDao;
	
	@Resource(name="myStorageInfoDao")
	private MyStorageInfoDao myStorageInfoDao;
	
	@Resource(name="userDao")
    private UserDao userDao	;
	
	@Resource(name="dtbxBucketMstDao")
    private DtbxBucketMstDao dtbxBucketMstDao	;
	
    @Resource(name="userService")
    private UserService userService;
	
    @Autowired
	private  CustomMailSender customMailSender;
	
	@Autowired
	private  CustomObjectStorage customObjectStorage;
	
	
	@Override
	public List<StrgeReqInfDto> inqReqStorageList(StorageSearchDto storageSearchDto){
		
		if(storageSearchDto.getUserId() != null) {
            UserInfoDto userInfoDto = userService.getUserSeq(storageSearchDto.getUserId());
            
            if(userInfoDto.getUpperUserId() != null) {
            	storageSearchDto.setUserId(userInfoDto.getUpperUserId());
            }
        }
		
		
		PageHelper.startPage(storageSearchDto.getPage() , storageSearchDto.getSize());
		
		return strgeReqInfDao.inqReqStorageList(storageSearchDto);
	}

    /**
    *
    * @methodName : reqStorage
    * @date : 2021-06-24 오후 2:33
    * @author : xeroman.k
    * @param StrgeReqInfDto
    * @return : kr.re.Amc.storage.dto.StrgeReqInfDto
    * @throws
    * @modifyed :
    *
   **/
	@Override
	public int regReqStorage(StrgeReqInfDto strgeReqInfDto) {
		
		strgeReqInfDto.setStorgeReqstId(UUID.randomUUID().toString());
		strgeReqInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
		//저장신청
		strgeReqInfDto.setStrgeReqSttusCd("S_REQ");
		
		return strgeReqInfDao.regReqStorage(strgeReqInfDto);
	}

	@Override
	public List<StrgeOthbcReqstInfDto> inqOpenStorageList(StorageSearchDto storageSearchDto) {

		if(storageSearchDto.getUserId() != null){
			UserInfoDto userInfoDto = userService.getUserSeq(storageSearchDto.getUserId());
			if(userInfoDto.getUpperUserId() != null) {
				storageSearchDto.setUserId(userInfoDto.getUpperUserId());
			}
		}
		
		
		PageHelper.startPage(storageSearchDto.getPage() , storageSearchDto.getSize());
		
		return strgeOthbcReqstInfDao.inqOpenStorageList(storageSearchDto);
	}

	
    /**
    *
    * @methodName : openStorage
    * @date : 2021-10-08 오후 2:30
    * @author : xeroman.k
    * @param StrgeOthbcReqstInfDto
    * @return : com.itsm.dranswer.storage.StrgeOthbcReqstInfDto
    * @throws
    * @modifyed :
    *
    **/	
	@Override
	public int regOpenStorage(StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto) {

		StrgeOthbcReqstInfDto.setOthbcReqstId(UUID.randomUUID().toString());
		StrgeOthbcReqstInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
		//저장신청
		StrgeOthbcReqstInfDto.setOthbcReqstSttuscd("O_REQ");
		
		return strgeOthbcReqstInfDao.regOpenStorage(StrgeOthbcReqstInfDto);
	}

	@Override
	public List<StrgeOthbcReqstInfDto> inqUseStorageList(StorageSearchDto storageSearchDto) {
		return strgeUseInfDao.inqUseStorageList(storageSearchDto);
	}

	@Override
	public StrgeOthbcReqstInfDto getUseStorageInfo(String othbcReqstId) {
		return strgeUseInfDao.getUseStorageInfo(othbcReqstId);
	}
	
	@Override
	public StrgeReqInfDto inqStorageSeq(String storgeReqstId) {
		StrgeReqInfDto strgeReqInfDto = strgeReqInfDao.inqStorageSeq(storgeReqstId);
		
		UserInfoDto userInfoDto = userDao.getUserSeq(strgeReqInfDto.getDissRspnberUserId());
		
		strgeReqInfDto.setDissRspnberUserNm(userInfoDto.getChargerNm());
		return strgeReqInfDto;
	}

	//질환데이터 적재현황 리스트
	@Override
	public List<StrgeReqInfDto> inqStorageUsedList(StorageSearchDto storageSearchDto) {
		
		return myStorageInfoDao.inqStorageUsedList(storageSearchDto);
	}

	@Override
	public int regReqUseStorage(StrgeUseInfDto strgeUseInfDto) {
		
		//openStorage정보조회
		StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto = strgeOthbcReqstInfDao.getOpenStorageInfo(strgeUseInfDto.getOthbcReqstId());
		
		if (ObjectUtils.isEmpty(StrgeOthbcReqstInfDto)) {
			throw new IllegalArgumentException("존재하지 않는 공개데이터 입니다.");
		}

		StrgeOthbcReqstInfDto.setUseStrgeId(UUID.randomUUID().toString());
		StrgeOthbcReqstInfDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
		//저장신청
		StrgeOthbcReqstInfDto.setUseReqstSttusSecd("U_REQ");
		StrgeOthbcReqstInfDto.setUseDaycnt(0);
		
		int regResult =  strgeUseInfDao.regReqUseStorage(StrgeOthbcReqstInfDto);
			
		
		String email = StrgeOthbcReqstInfDto.getEmail();
		String mailSubject = "[서울아산병원] 연구분석 데이터 사용 신청";
		String title = StrgeOthbcReqstInfDto.getOthbcDataNm();
		String agencyName = StrgeOthbcReqstInfDto.getInsttNm();
		String userName = StrgeOthbcReqstInfDto.getChargerNm();
		customMailSender.sendReqMail(email, mailSubject, title, agencyName, userName);
			
		return regResult;
	}
	

	@Override
	public int approveReqStorageInfo(String storgeReqstId, DtbxBucketMstDto dtbxBucketMstDto) {

		
		//정보조회
		StrgeReqInfDto reqStorageInfo = inqStorageSeq(storgeReqstId);
        UserInfoDto managerInfo = userService.getUserSeq(reqStorageInfo.getDissRspnberUserId());
        
        // 버킷 명명규칙 : amc2 + 병원코드 + 질병코드 + 회원ID + UUID
        Long insttId = managerInfo.getInsttId();

        String bucketName = "amc2-"+insttId+"-"+
        					 managerInfo.getDissCd().replaceAll("_", "-")+"-"+
        					 reqStorageInfo.getDissRspnberUserId()+"-"+reqStorageInfo.getStorgeReqstId();
        
        bucketName = bucketName.toLowerCase();
        
        dtbxBucketMstDto.setStorgeReqstId(storgeReqstId);
        dtbxBucketMstDto.setBucketId(bucketName);
        dtbxBucketMstDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
        dtbxBucketMstDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
        dtbxBucketMstDto.setOthbcUserId(reqStorageInfo.getDissRspnberUserId());
        dtbxBucketMstDto.setStrgeReqSttusCd("S_ACC");
        
        //버킷생성
        makeBucket(bucketName);
        
        //버킷ACL설정
        setBucketACL(bucketName, managerInfo.getNcloudObjStrgeId());
        
        //버킷정보저장
        makeBucketInfo(dtbxBucketMstDto);
        
        //승인 상태코드 변경 
        int updtRtn = strgeReqInfDao.updtReqStorageApproveStat(dtbxBucketMstDto);
        
        String email = managerInfo.getEmail();
        String mailsubject = "[서울아산병원]저장신청 승인 안내";
        String title = "저장신청";
        String userName = managerInfo.getChargerNm();
        String subject = reqStorageInfo.getStorgeNm();
        customMailSender.sendAcceptMail(email, mailsubject, title, userName, subject);
        
		return updtRtn;
	}
	
	@Override
	public int rejectReqStorageInfo(String reqStorageId, StrgeReqInfDto paramReqStorageInfo) {
		
		//정보조회
		StrgeReqInfDto reqStorageInfo = inqStorageSeq(reqStorageId);
        UserInfoDto managerInfo = userService.getUserSeq(reqStorageInfo.getDissRspnberUserId());
		
		
		//상태코드 설정
		if("S_REQ".equals(reqStorageInfo.getStrgeReqSttusCd())) {
			paramReqStorageInfo.setStrgeReqSttusCd("S_REJ");
		} else if("D_REQ".equals(reqStorageInfo.getStrgeReqSttusCd())) {
			paramReqStorageInfo.setStrgeReqSttusCd("D_REJ");
		}
		
		paramReqStorageInfo.setStorgeReqstId(reqStorageId);
		paramReqStorageInfo.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		
        //승인 상태코드 변경 
        int updtRtn = strgeReqInfDao.updtReqStorageStat(paramReqStorageInfo);
		
		
		if("S_REJ".equals(paramReqStorageInfo.getStrgeReqSttusCd())) {
            String email = managerInfo.getEmail();
            String mailsubject = "[서울아산병원]저장신청 거절 안내";
            String title = "저장신청";
            String userName = managerInfo.getChargerNm();
            String subject = reqStorageInfo.getStorgeNm();
            String reject = reqStorageInfo.getRejectResn();
            customMailSender.sendRejectMail(email, mailsubject, title, userName, subject, reject);
        }

		if("D_REJ".equals(paramReqStorageInfo.getStrgeReqSttusCd())) {
            String email = managerInfo.getEmail();
            String mailsubject = "[서울아산병원]삭제신청 거절 안내";
            String title = "삭제신청";
            String userName = managerInfo.getChargerNm();
            String subject = reqStorageInfo.getStorgeNm();
            String reject = reqStorageInfo.getRejectResn();
            customMailSender.sendRejectMail(email, mailsubject, title, userName, subject, reject);
            System.out.println("");
        }
        
		return updtRtn;
	}	
	
	
	@Override
	public int cancelReqStorageInfo(String reqStorageId) {
		
		//정보조회
		StrgeReqInfDto reqStorageInfo = inqStorageSeq(reqStorageId);
		
		
        if(!AmcUtil.getPrincipal().getUserId().equals(reqStorageInfo.getCreatUserId())){
            throw new IllegalArgumentException("타인의 정보 입니다.");
        }
		
        StrgeReqInfDto paramReqStorageInfo = new StrgeReqInfDto();
		paramReqStorageInfo.setStorgeReqstId(reqStorageId);
		paramReqStorageInfo.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		paramReqStorageInfo.setStrgeReqSttusCd("S_CCL");
		
        //승인 상태코드 변경 
        int updtRtn = strgeReqInfDao.updtReqStorageStat(paramReqStorageInfo);
		
		return updtRtn;
	}	
	
	@Override
	public int deleteReqStorageInfo(String reqStorageId, StrgeReqInfDto paramReqStorageInfo) {

		//정보조회
		StrgeReqInfDto reqStorageInfo = inqStorageSeq(reqStorageId);
		
        if(!AmcUtil.getPrincipal().getUserId().equals(reqStorageInfo.getCreatUserId())){
            throw new IllegalArgumentException("타인의 정보 입니다.");
        }
		
		paramReqStorageInfo.setStorgeReqstId(reqStorageId);
		paramReqStorageInfo.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		
		if("S_ACC".equals(reqStorageInfo.getStrgeReqSttusCd())) {
			paramReqStorageInfo.setStrgeReqSttusCd("D_REQ");
		}
		
        
        //승인 상태코드 변경 
        int updtRtn = strgeReqInfDao.updtReqStorageStat(paramReqStorageInfo);
		
		return updtRtn;
	}	
	
    /**
    *
    * @methodName : makeBucketInfo
    * @date : 2021-10-08 오후 2:29
    * @author : xeroman.k
    * @param reqStorageInfo
    * @param bucketInfoDto
    * @return : com.itsm.dranswer.storage.BucketInfo
    * @throws
    * @modifyed :
    *
   **/
   public int makeBucketInfo(DtbxBucketMstDto dtbxBucketMstDto){
       return dtbxBucketMstDao.regBucketInfo(dtbxBucketMstDto);
   }

   /**
    *
    * @methodName : makeBucket
    * @date : 2021-10-08 오후 2:30
    * @author : xeroman.k
    * @param bucketName
    * @return : void
    * @throws
    * @modifyed :
    *
   **/
   public void makeBucket(String bucketName){
       customObjectStorage.makeBucket(endPoint, regionName, laifAccessKey, laifSecretKey, bucketName);
   }

   /**
    *
    * @methodName : setBucketACL
    * @date : 2021-10-08 오후 2:30
    * @author : xeroman.k
    * @param bucketName
    * @param ncpObjStorageId
    * @return : void
    * @throws
    * @modifyed :
    *
   **/
   public void setBucketACL(String bucketName, String ncpObjStorageId){
       customObjectStorage.setBucketACL(endPoint, regionName, laifAccessKey, laifSecretKey, bucketName, ncpObjStorageId);
   }

   /**
    *
    * @methodName : deleteBucket
    * @date : 2021-10-08 오후 2:30
    * @author : xeroman.k
    * @param bucketName
    * @return : void
    * @throws
    * @modifyed :
    *
   **/
   public void deleteBucket(String bucketName){
       customObjectStorage.deleteBucket(endPoint, regionName, laifAccessKey, laifSecretKey, bucketName);
   }

    /**
    *
    * @methodName : deleteReqStorageInfo
    * @date : 2021-07-08 오전 10:46
    * @author : xeroman.k
    * @param reqStorageId
    * @return : com.itsm.dranswer.storage.StrgeReqInfDto
    * @throws
    * @modifyed :
    *
   **/  
	@Override
	public int deleteReqStorageInfo(String reqStorageId) {
		
		//정보조회
		StrgeReqInfDto reqStorageInfo = inqStorageSeq(reqStorageId);
        UserInfoDto managerInfo = userService.getUserSeq(reqStorageInfo.getDissRspnberUserId());
        
        reqStorageInfo.setStrgeReqSttusCd("D_ACC");
        reqStorageInfo.setStorgeReqstId(reqStorageId);
        reqStorageInfo.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
        
        //승인 상태코드 변경 
        int updtRtn = strgeReqInfDao.updtReqStorageStat(reqStorageInfo);
        
        deleteBucket(reqStorageInfo.getBucketId());
		
        String email = managerInfo.getEmail();
        String mailsubject = "[서울아산병원]삭제신청 승인 안내";
        String title = "삭제신청";
        String userName = managerInfo.getChargerNm();
        String subject = reqStorageInfo.getStorgeNm();
        customMailSender.sendAcceptMail(email, mailsubject, title, userName, subject);
        
		
		return updtRtn;
	}

	@Override
	public StrgeOthbcReqstInfDto getOpenStorageInfo(String othbcReqstId) {
		return strgeOthbcReqstInfDao.getOpenStorageInfo(othbcReqstId);
	}

	@Override
	public int approveOpenStorageInfo(String othbcReqstId) {

		StrgeOthbcReqstInfDto openStorageInfo = getOpenStorageInfo(othbcReqstId);
        UserInfoDto managerInfo = userService.getUserSeq(openStorageInfo.getDissRspnberUserId());
        
        
		//상태코드 설정
		if("O_REQ".equals(openStorageInfo.getOthbcReqstSttuscd())) {
			openStorageInfo.setOthbcReqstSttuscd("O_ACC");
		} else if("C_REQ".equals(openStorageInfo.getOthbcReqstSttuscd())) {
			openStorageInfo.setOthbcReqstSttuscd("C_ACC");
		}
		
		openStorageInfo.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		
        //승인 상태코드 변경 
        int updtRtn = strgeOthbcReqstInfDao.updtOpenStorageStat(openStorageInfo);
        
        
        if("O_ACC".equals(openStorageInfo.getOthbcReqstSttuscd())){
            String email = managerInfo.getEmail();
            String mailsubject = "[서울아산병원]공개신청 승인 안내";
            String title = "공개신청";
            String userName = managerInfo.getChargerNm();
            String subject = openStorageInfo.getOthbcDataNm();
            customMailSender.sendAcceptMail(email, mailsubject, title, userName, subject);
        }

        if("C_ACC".equals(openStorageInfo.getOthbcReqstSttuscd())){
            String email = managerInfo.getEmail();
            String mailsubject = "[서울아산병원]공개취소신청 승인 안내";
            String title = "공개취소신청";
            String userName = managerInfo.getChargerNm();
            String subject = openStorageInfo.getOthbcDataNm();
            customMailSender.sendAcceptMail(email, mailsubject, title, userName, subject);
        }       
        return updtRtn;
	}

	@Override
	public int rejectOpenStorageInfo(String othbcReqstId, StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto) {
		StrgeOthbcReqstInfDto openStorageInfo = getOpenStorageInfo(othbcReqstId);
        UserInfoDto managerInfo = userService.getUserSeq(openStorageInfo.getDissRspnberUserId());
        
        
		//상태코드 설정
		if("O_REQ".equals(openStorageInfo.getOthbcReqstSttuscd())) {
			openStorageInfo.setOthbcReqstSttuscd("O_REJ");
		} else if("C_REQ".equals(openStorageInfo.getOthbcReqstSttuscd())) {
			openStorageInfo.setOthbcReqstSttuscd("C_REJ");
		}
		
		openStorageInfo.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		openStorageInfo.setRejectResn(StrgeOthbcReqstInfDto.getRejectResn());
		
		
        //승인 상태코드 변경 
        int updtRtn = strgeOthbcReqstInfDao.updtOpenStorageStat(openStorageInfo);
        
        
        if("O_REJ".equals(openStorageInfo.getOthbcReqstSttuscd())){
            String email = managerInfo.getEmail();
            String mailsubject = "[서울아산병원]공개신청 거절 안내";
            String title = "공개신청";
            String userName = managerInfo.getChargerNm();
            String subject = openStorageInfo.getOthbcDataNm();
            String reject = openStorageInfo.getRejectResn();
            customMailSender.sendRejectMail(email, mailsubject, title, userName, subject, reject);
        }

        if("C_REJ".equals(openStorageInfo.getOthbcReqstSttuscd())){
            String email = managerInfo.getEmail();
            String mailsubject = "[서울아산병원]공개취소신청 거절 안내";
            String title = "공개취소신청";
            String userName = managerInfo.getChargerNm();
            String subject = openStorageInfo.getOthbcDataNm();
            String reject = openStorageInfo.getRejectResn();
            customMailSender.sendRejectMail(email, mailsubject, title, userName, subject, reject);
        }       
        return updtRtn;
	}

	@Override
	public int cancelOpenStorageInfo(String othbcReqstId, StrgeOthbcReqstInfDto StrgeOthbcReqstInfDto) {
		StrgeOthbcReqstInfDto openStorageInfo = getOpenStorageInfo(othbcReqstId);
		
        if(!AmcUtil.getPrincipal().getUserId().equals(openStorageInfo.getCreatUserId())){
            throw new IllegalArgumentException("타인의 정보 입니다.");
        }
        
		openStorageInfo.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		openStorageInfo.setCanclResn(StrgeOthbcReqstInfDto.getCanclResn());
		openStorageInfo.setOthbcReqstSttuscd("C_REQ");
		
        //승인 상태코드 변경 
        int updtRtn = strgeOthbcReqstInfDao.updtOpenStorageStat(openStorageInfo);
		
        
		return updtRtn;
	}

	@Override
	public List<StrgeUseInfDto> inqMyUseStorageList(StorageSearchDto storageSearchDto) {
		
		return strgeUseInfDao.inqMyUseStorageList(storageSearchDto);
	}

	@Override
	public StrgeUseInfDto getMyUseStorageInfo(String useStrgeId) {
		return strgeUseInfDao.getMyUseStorageInfo(useStrgeId);
	}

	@Override
	public int approveUseStorage(String useStrgeId) {
		StrgeUseInfDto useStorageInfo = strgeUseInfDao.getMyUseStorageInfo(useStrgeId);
		
		
		useStorageInfo.setUseStrgeId(useStrgeId);
		useStorageInfo.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		useStorageInfo.setUseReqstSttusSecd("U_ACC");
		
        //승인 상태코드 변경 
        int updtRtn = strgeUseInfDao.updtUseStorageApproveStat(useStorageInfo);
		
        String email = useStorageInfo.getReqEmail();
        String mailSubject = "[서울아산병원] 연구분석 데이터 사용 신청 승인 안내";
        String title = "연구분석 데이터 사용 신청";
        String userName = useStorageInfo.getReqChargerNm();
        String dataName = useStorageInfo.getOthbcDataNm();
        customMailSender.sendAcceptMail(email, mailSubject, title, userName, dataName);
		
		return updtRtn;
	}

	@Override
	public int cancelUseStorage(String useStrgeId, StrgeUseInfDto strgeUseInfDto) {
		
		strgeUseInfDto.setUseStrgeId(useStrgeId);
		strgeUseInfDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		strgeUseInfDto.setUseReqstSttusSecd("U_CCL");
        //승인 상태코드 변경 
        int updtRtn = strgeUseInfDao.updtUseStorageStat(strgeUseInfDto);
        
		return updtRtn;
	}

	@Override
	public int rejectUseStorage(String useStrgeId, StrgeUseInfDto strgeUseInfDto) {

		StrgeUseInfDto useStorageInfo = strgeUseInfDao.getMyUseStorageInfo(useStrgeId);
		
		
		strgeUseInfDto.setUseStrgeId(useStrgeId);
		strgeUseInfDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		strgeUseInfDto.setUseReqstSttusSecd("U_REJ");
		
        String email = useStorageInfo.getReqEmail();
        String mailSubject = "[서울아산병원] 연구분석 데이터 사용 신청 거절 안내";
        String title = "연구분석 데이터 사용 신청";
        String userName = useStorageInfo.getReqChargerNm();
        String dataName = useStorageInfo.getOthbcDataNm();
        String reject = useStorageInfo.getRejectResn();
        customMailSender.sendRejectMail(email, mailSubject, title, userName, dataName, reject);
		
        //승인 상태코드 변경 
        int updtRtn = strgeUseInfDao.updtUseStorageStat(strgeUseInfDto);
        
		return updtRtn;
	}

   
   public List<StrgeReqInfDto> getReqStorageAuthList(String reqStorageId){
	   StrgeReqInfDto strgeReqInfDto = inqStorageSeq(reqStorageId);
      
	   return userDao.inqMyUploaderList(strgeReqInfDto);
   }
   
   public List<StrgeReqInfDto> inqStorageUsedSummary(){
	   return myStorageInfoDao.inqStorageUsedSummary();
   }

	@Override
	public List<StrgeReqInfDto> inqMyStorageBucketList() {
		
		
        StorageSearchDto storageSearchDto  = new StorageSearchDto();
            
            if(AmcUtil.getPrincipal().getUpperUserId() != null) {
            	storageSearchDto.setUserId(AmcUtil.getPrincipal().getUpperUserId());
            }
            else {
            	storageSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
            }
        storageSearchDto.setStrgeReqSttusCd("S_ACC");
        
        List<StrgeReqInfDto> inqMyStorageBucketList = strgeReqInfDao.inqMyStorageBucketList(storageSearchDto);
        
		return inqMyStorageBucketList;
	}

	@Override
	public List<S3ObjectDto> getObjectList(String bucketName, String folderName) {
		UserInfoDto userInfoDto = userService.getOriginUser(AmcUtil.getPrincipal().getUserId() );

        String accessKey = userInfoDto.getNcloudAccesKey();
        String secretKey = userInfoDto.getNcloudScrtyKey();
        if(userInfoDto.getUserRoleSecd().equals("ROLE_ADMIN")){
            accessKey = laifAccessKey;
            secretKey = laifSecretKey;
        }

        return customObjectStorage.getObjectList(endPoint, regionName, accessKey, secretKey, bucketName, folderName);
	}

	@Override
	public FileUploadResponse uploadFile(String bucketName, String folderName, List<MultipartFile> multipartFiles) throws InterruptedException {
        int fileCnt = 0;
        long fileSize = 0L;

        UserInfoDto userInfoDto = userService.getOriginUser(AmcUtil.getPrincipal().getUserId());

        List<FileObject> listObject = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFiles){
            fileCnt++;
            fileSize += multipartFile.getSize();
            File targetFile = new File("/amc/temp/" + multipartFile.getOriginalFilename());
            try {
                InputStream fileStream = multipartFile.getInputStream();
                FileUtils.copyInputStreamToFile(fileStream, targetFile);
            } catch (IOException e) {
                FileUtils.deleteQuietly(targetFile);
                e.printStackTrace();
            }

            String objectName = multipartFile.getOriginalFilename();

//        String accessKey = laifAccessKey;
//        String secretKey = laifSecretKey;
            String accessKey = userInfoDto.getNcloudAccesKey();
            String secretKey = userInfoDto.getNcloudScrtyKey();

            String keyName = customObjectStorage.uploadObject(endPoint, regionName,
                    accessKey, secretKey, bucketName, folderName, objectName, targetFile, CannedAccessControlList.Private);

            FileObject fileObject = new FileObject();
            fileObject.setOrgFileName(multipartFile.getOriginalFilename());
            fileObject.setKeyName(keyName);

            listObject.add(fileObject);

            targetFile.delete();
        }

        return new FileUploadResponse(fileCnt, fileSize, listObject);
	}
	
	@Override
	public FileUploadResponse uploadBoardFile(String folderName, List<MultipartFile> multipartFiles) throws InterruptedException{
		
		int fileCnt = 0;
		long fileSize = 0L;

		String bucketName = laifServerUpload;
		List<FileObject> listObject = new ArrayList<>();
		
		for(MultipartFile multipartFile : multipartFiles) {
			fileCnt ++;
			fileSize += multipartFile.getSize();
			File targetFile = new File("/amc/temp/" + multipartFile.getOriginalFilename());

			try {
				InputStream fileStream = multipartFile.getInputStream();
				FileUtils.copyInputStreamToFile(fileStream, targetFile);
			}catch(IOException e) {
				FileUtils.deleteQuietly(targetFile);
				e.printStackTrace();
			}
			String objectName = new Date().getTime() + "_" + multipartFile.getOriginalFilename();
			
			String accessKey = laifServerAccessKey;
			String secretKey = laifServerSecretKey;
			
			String keyName = customObjectStorage.uploadObject(endPoint, regionName, accessKey, secretKey, bucketName, folderName, objectName, targetFile, CannedAccessControlList.PublicRead);
			
			FileObject fileObject = new FileObject();
			fileObject.setOrgFileName(multipartFile.getOriginalFilename());
			fileObject.setKeyName(keyName);
			
			listObject.add(fileObject);
			
			targetFile.delete();
		}
		return new FileUploadResponse(fileCnt, fileSize, listObject);
	}

	@Override
	public void deleteObjects(List<RequestObjectDto> requestObjectDtos) {
		
        for(RequestObjectDto requestObjectDto : requestObjectDtos){
        	
        	if("Y".equals(requestObjectDto.getFolderYn())){
        		deleteFolder(requestObjectDto.getBucketName(), requestObjectDto.getObjectName());
        	} else {
        		deleteObject(requestObjectDto.getBucketName(), requestObjectDto.getObjectName());
        	}
		
        }
	}
	
    public void deleteFolder(String bucketName, String objectName){

        UserInfoDto userInfoDto = userService.getOriginUser(AmcUtil.getPrincipal().getUserId());

        String accessKey = userInfoDto.getNcloudAccesKey();
        String secretKey = userInfoDto.getNcloudScrtyKey();

        customObjectStorage.deleteFolder(endPoint, regionName, accessKey, secretKey, bucketName, objectName);

    }	
    
    /**
    *
    * @methodName : deleteObject
    * @date : 2021-10-08 오후 2:30
    * @author : xeroman.k
    * @param bucketName
    * @param objectName
    * @param loginUserInfo
    * @return : void
    * @throws
    * @modifyed :
    *
   **/
   public void deleteObject(String bucketName, String objectName ){

       UserInfoDto userInfoDto = userService.getOriginUser(AmcUtil.getPrincipal().getUserId());

       String accessKey = userInfoDto.getNcloudAccesKey();
       String secretKey = userInfoDto.getNcloudScrtyKey();

       customObjectStorage.deleteObject(endPoint, regionName, accessKey, secretKey, bucketName, objectName);

   }    
	
}
