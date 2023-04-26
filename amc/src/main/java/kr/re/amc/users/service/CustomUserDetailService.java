package kr.re.amc.users.service;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.re.amc.users.dao.UserDao;
import kr.re.amc.users.dto.UserDetailDto;
import kr.re.amc.util.EgovMessageSource;
import lombok.extern.slf4j.Slf4j;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
	@Resource(name="userDao")
    private UserDao userDao;
	
	@Override
	public UserDetailDto loadUserByUsername(String username) throws UsernameNotFoundException {
		
		
		//사용자 principal
		UserDetailDto userDetailDto = userDao.inqUserId(username);
		
		if (userDetailDto == null) {
		    throw new UsernameNotFoundException(egovMessageSource.getMessage("E_GP_COM_LGN_FAIL"));
		}
		
		//사용자 MAIN ROLE
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(userDetailDto.getUserRoleSecd()));
		
        
        
        //사용자 MAIN ROLE
        userDetailDto.setAuthorities(authorities);
        
		return userDetailDto;
	}
 
}
