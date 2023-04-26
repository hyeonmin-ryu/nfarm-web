package kr.re.amc.commons;

import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.jcraft.jsch.Session;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class LoginUserResolver implements HandlerMethodArgumentResolver{

	
	private final HttpSession httpSession;
	
	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		boolean isLoginUserAnnotation = methodParameter.getParameterAnnotation(LoginUser.class)!=null;
		boolean isUserClass=Session.class.equals(methodParameter.getParameterType());
		return isLoginUserAnnotation && isUserClass;
		//return methodParameter.hasParameterAnnotation(LoginUser.class);
	}
	
	@Override
	public Object resolveArgument(MethodParameter param,
								  ModelAndViewContainer mvc,
								  NativeWebRequest nwReq,
								  WebDataBinderFactory dbf) throws Exception{
		LoginUserInfo user = new LoginUserInfo();

		//User의 id가 시작하는 값에 따라 UserType(Manager, VIPMemeber, Member)차등부여하고 리턴
        //String userId= nwReq.getParameter("userId");
        String userName=nwReq.getParameter("userName");
        

        return httpSession.getAttribute("user");
		
	}
}
