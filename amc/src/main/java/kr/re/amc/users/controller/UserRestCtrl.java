package kr.re.amc.users.controller;


import static kr.re.amc.utils.ApiUtils.success;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.commons.CustomMailSender;
import kr.re.amc.users.dto.AgencyDto;
import kr.re.amc.users.dto.CertDto;
import kr.re.amc.users.dto.JoinRequest;
import kr.re.amc.users.dto.UserInfoDto;
import kr.re.amc.users.dto.UserSearchDto;
import kr.re.amc.users.service.UserService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class UserRestCtrl {

	
    @Resource(name = "userService") 
    private UserService userService;
    
    
	/**
	 * @param certDto
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	@PostMapping(value = "/user/checkAndCert/mail")
	public ApiResult<CertDto> checkMail(@RequestBody @Valid CertDto certDto) throws MessagingException, IOException {

		userService.checkMailAndSendCertMailForSignup(certDto);
		
		return success(certDto);
	}

	@GetMapping(value="/user/list")
	public ApiResult<PageInfo<UserInfoDto>> getUserInfo(UserSearchDto userSearchDto){
		PageHelper.startPage(userSearchDto.getPage() , userSearchDto.getSize());
		List<UserInfoDto> userInfo = userService.inqUserList(userSearchDto);
		PageInfo<UserInfoDto> pageData = new PageInfo<>(userInfo);
		
		return success(pageData);
	}
	
	@GetMapping(value="/agency/list")
	public ApiResult<List<AgencyDto>> getAgencySeq(UserSearchDto userSearchDto){
		return success(userService.getAgencySeq(userSearchDto));
	}
	
	@GetMapping(value="join/agency/list")
	public ApiResult<PageInfo<AgencyDto>> getAgencyList(UserSearchDto userSearchDto){
		PageHelper.startPage(userSearchDto.getPage() , userSearchDto.getSize());
		List<AgencyDto> agencyInfo = userService.getAgencyList(userSearchDto);
		PageInfo<AgencyDto> pageData = new PageInfo<>(agencyInfo);
		
		return success(pageData);
	}
	
	@GetMapping(value = "/user/my/info")
    public ApiResult<UserInfoDto> getUserSeq(UserInfoDto userDto){
        return success(userService.getUserSeq(AmcUtil.getPrincipal().getUserId()));
    }
	
	
	@GetMapping(value = "/user/info/{userSeq:.+(?<!\\.js)$}")
    public ApiResult<UserInfoDto> getUserSeq(@PathVariable Long userSeq){
        return success(userService.getUserSeq(userSeq));
    }
	
    /**
    *
    * @methodName : getReqUser
    * @date : 2021-06-25 오전 10:53
    * @author : xeroman.k
    * @param loginUserInfo
    * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.users.ReqUserDto>
    * @throws
    * @modifyed :
    *
   **/
   @GetMapping(value = "/user/req/storage")
   public ApiResult<UserInfoDto> getReqUser(){

	   UserInfoDto reqUserDto = userService.getReqStorageUserInfo(AmcUtil.getPrincipal().getUserId());

       return success(reqUserDto);
   }		
	
   @PostMapping(value = "/user/my/info")
   public ApiResult<UserInfoDto> updtUserInfo(@RequestBody UserInfoDto userInfoDto){
	   
	   userService.updtUserInfo(userInfoDto);
	   
       return success(userService.getUserSeq(userInfoDto.getUserId()));
   }
   
   @PostMapping(value = "/user/my/pw")
   public ApiResult<UserInfoDto> updtPassword(@RequestBody UserInfoDto userInfoDto){
	   userService.updtPassword(userInfoDto);
       return success(null);
   }
   
   /**
    * 회원가입처리
    * @methodName : join
    * @date : 2021-06-23 오후 2:05
    * @author : xeroman.k
    * @param request
    * @return : com.itsm.dranswer.utils.ApiUtils.ApiResult<com.itsm.dranswer.users.UserInfoDto>
    * @throws
    * @modifyed :
    *
   **/
   @PostMapping(value = "/user/join")
   public ApiResult<UserInfoDto> join(@RequestBody @Valid JoinRequest request){
       userService.join(request);
       return success(null);
   }
   
   @Secured(value = {"ROLE_ADMIN"})
   @PostMapping(value = "/user/accept")
   public ApiResult<UserInfoDto> acceptUser(@RequestBody UserInfoDto userInfoDto){

       return success(userService.acceptUser(userInfoDto));
   }
   
   @Secured(value = {"ROLE_ADMIN"})
   @PostMapping(value = "/user/reject")
   public ApiResult<UserInfoDto> rejectUser(@RequestBody UserInfoDto userInfoDto){

	   userService.rejectUser(userInfoDto);
   	
       return success(null);
   }    
   
   @Secured(value = {"ROLE_MANAGER"})
   @GetMapping(value = "/user/my/uploader")
   public ApiResult<List<UserInfoDto>> getMyUploader(){
	   
       return success(userService.inqMyUploader());
   }

   @Secured(value = {"ROLE_MANAGER"})
   @PostMapping(value = "/user/my/uploader")
   public ApiResult<List<UserInfoDto>> saveMyUploader(@RequestBody List<UserInfoDto> userInfoDtos){
       return success(userService.saveMyUploader(userInfoDtos));
   }
   
   @PostMapping(value = "/user/find/mail")
   public ApiResult<List<UserInfoDto>> findMail(@RequestBody @Valid UserInfoDto userInfoDto) {
       return success(userService.findByPhoneNumber(userInfoDto));
   }

   @PostMapping(value = "/user/find/pw/cert/mail")
   public ApiResult<CertDto> findPwCertMail(@RequestBody @Valid CertDto certDto) throws MessagingException, IOException {

       userService.findPw(certDto);

       return success(certDto);
   }
   
   @PostMapping(value = "/user/change/pw")
   public ApiResult<UserInfoDto> changePw(@RequestBody @Valid UserInfoDto userInfoDto) {

       return success(userService.changePassword(userInfoDto));
   }
}
