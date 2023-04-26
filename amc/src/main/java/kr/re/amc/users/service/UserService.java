package kr.re.amc.users.service;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import kr.re.amc.users.dto.AgencyDto;
import kr.re.amc.users.dto.CertDto;
import kr.re.amc.users.dto.JoinRequest;
import kr.re.amc.users.dto.UserInfoDto;
import kr.re.amc.users.dto.UserSearchDto;

public interface UserService {
		
	/**
	 * @param certDto
	 * @throws IOException 
	 * @throws MessagingException 
	 */
	void checkMailAndSendCertMailForSignup(CertDto certDto) throws MessagingException, IOException;

	List<UserInfoDto> inqUserList(UserSearchDto userSearchDto);
	
	List<AgencyDto> getAgencySeq (UserSearchDto userSearchDto);
	
	List<AgencyDto> getAgencyList (UserSearchDto userSearchDto);
	
	UserInfoDto getUserSeq(Long userSeq);

	UserInfoDto getReqStorageUserInfo(Long userSeq);
	
	UserInfoDto getOriginUser(Long userId);
	
	int updtUserInfo(UserInfoDto userInfoDto);

	int updtPassword(UserInfoDto userInfoDto);
	
	int join(JoinRequest request);
	
	UserInfoDto acceptUser(UserInfoDto userInfoDto);
	
	int rejectUser(UserInfoDto userInfoDto);
	
	List<UserInfoDto> inqMyUploader(); 
	
	List<UserInfoDto> saveMyUploader(List<UserInfoDto> userInfoDtos);
	
	List<UserInfoDto> findByPhoneNumber(UserInfoDto userInfoDto);
	
	void findPw(CertDto certDto) throws MessagingException, IOException;
	
	UserInfoDto changePassword(UserInfoDto userInfoDto);
	
}
