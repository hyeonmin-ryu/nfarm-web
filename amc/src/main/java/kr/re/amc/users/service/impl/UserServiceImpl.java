package kr.re.amc.users.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.mail.MessagingException;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import kr.re.amc.commons.CustomMailSender;
import kr.re.amc.users.dao.UserDao;
import kr.re.amc.users.dto.AgencyDto;
import kr.re.amc.users.dto.CertDto;
import kr.re.amc.users.dto.JoinRequest;
import kr.re.amc.users.dto.UserDetailDto;
import kr.re.amc.users.dto.UserInfoDto;
import kr.re.amc.users.dto.UserSearchDto;
import kr.re.amc.users.service.CustomUserDetailService;
import kr.re.amc.users.service.UserService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.util.EgovMessageSource;

import kr.re.amc.exception.NotFoundException;
@Service("userService")
public class UserServiceImpl extends EgovAbstractServiceImpl implements UserService {

	@Inject
    private CustomUserDetailService userDetailService;
	
	@Autowired
	private CustomMailSender customMailSender;

	@Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;

	@Resource(name = "userDao")
	private UserDao userDao;

	@Override
	public void checkMailAndSendCertMailForSignup(CertDto certDto) throws MessagingException, IOException {

		int chkEmail = userDao.inqUserEmlYn(certDto.getEmail());

		if (chkEmail > 0) {
			throw new IllegalArgumentException("사용중인 이메일 입니다.");
		}

		String template = "mail/cert";
		String subject = "[서울아산병원]이메일 인증을 위한 인증번호가 발급되었습니다.";
		String[] to = { certDto.getEmail() };
		Context ctx = new Context();
		ctx.setVariable("certNumber", certDto.getCertNumber());

		customMailSender.sendMail(template, subject, to, ctx);

	}

	@Override
	public List<UserInfoDto> inqUserList(UserSearchDto userSearchDto) {

		return userDao.inqUserList(userSearchDto);
	}

	@Override
	public List<AgencyDto> getAgencySeq(UserSearchDto userSearchDto) {
		return userDao.getAgencySeq(userSearchDto);
	}
	
	@Override
	public List<AgencyDto> getAgencyList(UserSearchDto userSearchDto) {
		return userDao.getAgencyList(userSearchDto);
	}

	@Override
	public UserInfoDto getUserSeq(Long userSeq) { //
		//return userDao.getUserSeq(AmcUtil.getPrincipal().getUpperUserId() == null ? userSeq
			//	: AmcUtil.getPrincipal().getUpperUserId());
		
		//return userDao.getUserSeq(AmcUtil.getPrincipal().getUserId());
		UserInfoDto userInfoDto = userDao.getUserSeq(userSeq);
		
		if(AmcUtil.getPrincipal().getUpperUserId() != null)
		userInfoDto.setDissRspnberUserNm(userDao.getUserSeq(AmcUtil.getPrincipal().getUpperUserId()).getChargerNm());
		
		return userInfoDto;
	}

	@Override
	public UserInfoDto getReqStorageUserInfo(Long userSeq) {
		return userDao.inqUserInfoSeq(userSeq);
	}

	@Override
	public UserInfoDto getOriginUser(Long userId) {

		UserInfoDto userInfo = userDao.inqUserInfoSeq(userId);
		
		if(userInfo.getUpperUserId() == null)
			return userInfo;
			else {
				return userInfo = userDao.inqUserInfoSeq(userInfo.getUpperUserId());
			}
	}

	@Override
	public int updtUserInfo(UserInfoDto userInfoDto) {

		return userDao.updtUserInfo(userInfoDto);
	}

	@Override
	public int updtPassword(UserInfoDto userInfoDto)  throws AuthenticationException{
		
        String password = (String) userInfoDto.getInputOldPw();
        String newpassword = (String) userInfoDto.getInputNewPw();
        
        UserDetailDto user = userDetailService.loadUserByUsername(AmcUtil.getPrincipal().getEmail());
		
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder(14));
        DelegatingPasswordEncoder passEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        boolean encryptPass = passEncoder.matches(password, user.getPassword());
                        
        if (!encryptPass) {
          throw new UsernameNotFoundException(egovMessageSource.getMessage("E_GP_COM_LGN_FAIL"));
        }
        
        String encodingId2 = "bcrypt";
        Map<String, PasswordEncoder> encoders2 = new HashMap<>();
        encoders2.put(encodingId2, new BCryptPasswordEncoder(14));
        DelegatingPasswordEncoder passEncoder2 = new DelegatingPasswordEncoder(encodingId2, encoders2);
        
        String securePw = passEncoder2.encode(newpassword);
        user.setLoginPassword(securePw);
        return userDao.updtPassword(user);
	}
	
	@Override
	public int join(JoinRequest request) {
		
		String encodingId = "bcrypt";
		String password = (String) request.getInputPw();
		
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder(14));
		DelegatingPasswordEncoder passEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
		
		String securePw = passEncoder.encode(password);

		request.setInputPw(securePw);
		
		return userDao.join(request);
	}
	
	@Override
	public UserInfoDto acceptUser(UserInfoDto userInfoDto) {
		userInfoDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		
		userDao.acceptUser(userInfoDto);
		
		////
		String email = userInfoDto.getEmail();
		String mailsubject = "[서울아산병원]가입이 승인 되었습니다.";
		String userName = userInfoDto.getChargerNm();
		String title = "가입 신청";
		String subject = "가입";
		
		customMailSender.sendAcceptMail(email, mailsubject, title, userName, subject);
		
		return null;
	}
	
	@Override
	public int rejectUser(UserInfoDto userInfoDto) {
		
		String email = userInfoDto.getEmail();
		String mailsubject = "[서울아산병원]가입이 거절 되었습니다.";
		String userName = userInfoDto.getChargerNm();
		String title = "가입 신청";
		String subject = "가입";
		String reject = "reject";
		
		customMailSender.sendRejectMail(email, mailsubject, title, userName, subject, reject);
		
		return userDao.rejectUser(userInfoDto);
	}
	
	@Override
	public List<UserInfoDto> inqMyUploader(){
		
		UserInfoDto userInfoDto = getUserSeq(AmcUtil.getPrincipal().getUserId());
		
		return userDao.inqMyUploader(userInfoDto);
	}
	
	@Override
	public List<UserInfoDto> saveMyUploader(List<UserInfoDto> uploaders){
		UserInfoDto parentInfo = getUserSeq(AmcUtil.getPrincipal().getUserId());
		
		for(UserInfoDto userInfoDto: uploaders) {
			
			//userInfoDto.setUpperUserId(parentInfo.getUserId());
			
			userDao.saveUploader(userInfoDto);
			
		}
		
		return userDao.inqMyUploader(parentInfo);
	}
	
	@Override
	public List<UserInfoDto> findByPhoneNumber(UserInfoDto userInfoDto) {

        List<UserInfoDto> userInfos = userDao.findUserInfo(userInfoDto);

        if(userInfos.size() == 0) {
        	throw new IllegalArgumentException("회원정보가 존재하지 않습니다.");
        }

        return userInfos;

    }
	
	@Override
	public void findPw(CertDto certDto)throws MessagingException, IOException {
		
		//UserInfoDto userInfoDto = userDao.findEmail(certDto.getEmail());
		int checkEmail = userDao.findEmail(certDto.getEmail());
		if(checkEmail  == 0) {
			throw new NotFoundException("등록되지 않은 이메일 입니다. " + certDto.getEmail());
		}
		
		String template = "mail/pwdCert";
        String subject = "[서울아산병원]이메일 인증을 위한 인증번호가 발급되었습니다.";
        String[] to = {certDto.getEmail()};
        Context ctx = new Context();
        ctx.setVariable("certNumber", certDto.getCertNumber());


        customMailSender.sendMail(template, subject, to, ctx);
	}
	
	@Override
	public UserInfoDto changePassword(UserInfoDto userInfoDto) {
		int checkEmail = userDao.findEmail(userInfoDto.getEmail());
		if(checkEmail  == 0) {
			throw new NotFoundException("등록되지 않은 이메일 입니다." + userInfoDto.getEmail());
		}
		
		String encodingId = "bcrypt";
		String password = (String) userInfoDto.getInputNewPw();
		
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(encodingId, new BCryptPasswordEncoder(14));
		DelegatingPasswordEncoder passEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
		
		String securePw = passEncoder.encode(password);

		userInfoDto.setInputNewPw(securePw);
		
		userDao.setPw(userInfoDto);
		
		return userInfoDto;
	}
}
