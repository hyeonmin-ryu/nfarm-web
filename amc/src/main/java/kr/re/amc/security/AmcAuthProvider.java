package kr.re.amc.security;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.re.amc.users.dto.UserDetailDto;
import kr.re.amc.users.service.CustomUserDetailService;
import kr.re.amc.util.EgovMessageSource;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class AmcAuthProvider implements AuthenticationProvider {

    @Inject
    private CustomUserDetailService userDetailService;

    @Resource(name="egovMessageSource")
    EgovMessageSource egovMessageSource;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        //*****************************삭제***********************/
        String encodingId2 = "bcrypt";
        Map<String, PasswordEncoder> encoders2 = new HashMap<>();
        encoders2.put(encodingId2, new BCryptPasswordEncoder(14));
        DelegatingPasswordEncoder passEncoder2 = new DelegatingPasswordEncoder(encodingId2, encoders2);
        log.info("임시"+passEncoder2.encode(password));
        //*****************************삭제***********************/
        
        //USER조회
        UserDetailDto user = userDetailService.loadUserByUsername(username);


        //PASSWORD 비교
        String encodingId = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder(14));
        DelegatingPasswordEncoder passEncoder = new DelegatingPasswordEncoder(encodingId, encoders);
        boolean encryptPass = passEncoder.matches(password, user.getPassword());

        if (!encryptPass) {
            throw new UsernameNotFoundException(egovMessageSource.getMessage("E_GP_COM_LGN_FAIL"));
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
