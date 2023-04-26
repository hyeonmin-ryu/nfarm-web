package kr.re.amc.security;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.re.amc.utils.ApiUtils.ApiResult;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class AmcAuthenticationHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
    
    
	//로그인 성공 후 부가작업
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		
        HashMap<String, Object> responseMap = new HashMap<>();
        log.debug("onAuthenticationSuccess");

        request.getSession().setMaxInactiveInterval(3600);
        
        
        responseMap.put("data", "ok");
        responseMap.put("error", "false");
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseMap));
    }
	
	//로그인 실패 후 부가작업
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
        HashMap<String, Object> responseMap = new HashMap<>();
        log.debug("onAuthenticationFailure");
        log.debug("exception:::::::" + exception.getMessage());
        
        responseMap.put("error", exception.getMessage());
        response.setStatus(500);
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE+";" + "charset=UTF-8");
        
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseMap));
	}


}
