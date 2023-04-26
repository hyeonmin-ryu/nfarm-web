package kr.re.amc.util;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import kr.re.amc.users.dto.UserDetailDto;




public class AmcUtil {

    public static String getRemoteAddr(HttpServletRequest request) {

        String ip = null;

        ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("Proxy-Client-IP"); 
        } 

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("WL-Proxy-Client-IP"); 
        } 

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_CLIENT_IP"); 
        } 

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-Real-IP"); 
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("X-RealIP"); 
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getHeader("REMOTE_ADDR");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
            ip = request.getRemoteAddr(); 
        }

        return ip;
    }
    
    //principal 정보
    public static UserDetailDto getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return new UserDetailDto();
        }
        Object principal = authentication.getPrincipal();
        if (principal.equals("anonymousUser")) {
            return new UserDetailDto();
        }
        
        return (UserDetailDto) principal;
    }    
    
    
    //principal ROLE정보
    @SuppressWarnings("unchecked")
	public static String getRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> auth = (List<GrantedAuthority>) authentication.getAuthorities();
        
        return auth.get(0).getAuthority();
    }  
    
    
	public static boolean isAadmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		List<GrantedAuthority> auth = (List<GrantedAuthority>) authentication.getAuthorities();
        
		
		boolean isAdminYn = false ;
		if("ROLE_ADMIN".equals(auth.get(0).getAuthority())) {
			isAdminYn = true;
		}
        return isAdminYn;
    }    
    
    
    /**
     * 로그인여부
     * 
     * @return
     */
    public static boolean isLoginYn() {
        
        boolean loginYn = true;
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            loginYn = false;
        }
        Object principal = authentication.getPrincipal();
        if (principal.equals("anonymousUser")) {
            loginYn = false;
        }
        
        return loginYn;
    }    
}
