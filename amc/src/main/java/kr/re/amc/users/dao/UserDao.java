package kr.re.amc.users.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jcraft.jsch.UserInfo;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.storage.dto.StrgeReqInfDto;
import kr.re.amc.users.dto.AgencyDto;
import kr.re.amc.users.dto.JoinRequest;
import kr.re.amc.users.dto.UserDetailDto;
import kr.re.amc.users.dto.UserInfoDto;
import kr.re.amc.users.dto.UserSearchDto;

/**
 * 사용자에 대한 DAO 클래스를 정의한다.
 * @author sjs
 * @since 2021.02.23
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *   
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2021.02.23  sjs          최초 생성
 *
 * </pre>
 */
 
@Repository("userDao")
public class UserDao extends EgovComAbstractDAO {
    
	public UserDetailDto inqUserId(String username) {
		return selectOne("userDao.inqUserId" ,username );
	}

	public int inqUserEmlYn(String email) {
		return selectOne("userDao.inqUserEmlYn" ,email );
	}
	
	public List<String> inqUserRole(String username) {
		return selectList("userDao.inqUserRole" ,username );
	}
	
	public List<UserInfoDto> inqUserList(UserSearchDto userSearchDto){
		return selectList("userDao.inqUserList", userSearchDto);
	}
	
	public List<AgencyDto> getAgencySeq(UserSearchDto userSearchDto){
		return selectList("userDao.getAgencySeq", userSearchDto);
	}
	
	public List<AgencyDto> getAgencyList(UserSearchDto userSearchDto){
		return selectList("userDao.getAgencyList", userSearchDto);
	}
	
	public UserInfoDto getUserSeq(Long userSeq) {
		return selectOne("userDao.getUserSeq", userSeq);
	}
	
	public UserInfoDto inqUserInfoSeq(Long userSeq) {
		return selectOne("userDao.inqUserInfoSeq", userSeq);
	}
	
	public int updtUserInfo(UserInfoDto userInfoDto) {
		return update("userDao.updtUserInfo", userInfoDto);
	}
	
	public int updtPassword(UserDetailDto userDetailDto) {
		return update("userDao.updtPassword", userDetailDto);
	}
	
	public List<StrgeReqInfDto> inqMyUploaderList(StrgeReqInfDto strgeReqInfDto){
		return selectList("userDao.inqMyUploaderList", strgeReqInfDto);
	}
	
	public int join(JoinRequest request) {
		return insert("userDao.join", request);
	}
	
	public int acceptUser(UserInfoDto userInfoDto) {
		return update("userDao.acceptUser", userInfoDto);
	}
	
	public int rejectUser(UserInfoDto userInfoDto) {
		return delete("userDao.rejectUser", userInfoDto);
	}
	
	public List<UserInfoDto> inqMyUploader(UserInfoDto userInfoDto){
		return selectList("userDao.inqMyUploader", userInfoDto);
	}
	
	public int saveUploader (UserInfoDto userInfoDto) {
		return update("userDao.saveUploader", userInfoDto);
	}
	
	public List<UserInfoDto> findUserInfo(UserInfoDto userInfoDto) {
		return selectList("userDao.findUserInfo", userInfoDto);
	}
	
	public int findEmail(String Email) {
		return selectOne("userDao.findEmail", Email);
	}
	
	public UserInfoDto setPw(UserInfoDto userInfoDto) {
		return selectOne("userDao.setPw", userInfoDto);
	}
}
