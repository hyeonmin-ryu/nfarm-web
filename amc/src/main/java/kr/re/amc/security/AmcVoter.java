package kr.re.amc.security;

import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import kr.re.amc.cmm.dto.MenuDto;
import kr.re.amc.cmm.service.CommonService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AmcVoter implements AccessDecisionVoter<Object>   {
	 
     
    @Resource(name = "commonService")
    private CommonService commonService;
    
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return true;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
		
		log.info("voter check");
		log.info("attributes:: "+attributes);
		log.info("authentication:: "+authentication.getAuthorities());
		
		/**상세화면 URL mapping 
		 * 예) lndata/store/main == 메인
		 *     lndata/store/req == 메인의 상세
		 *     lndata/store/** 까지 허용 처리
		 *     메인화면에 포함된 상세의 경우, lndata/store/** URL 2번째 까지 매치 되어야 함
		 * **/
		FilterInvocation fi = (FilterInvocation) object;
		
		System.out.println(fi.getRequestUrl());
		
		String[] arSplitUrl = fi.getRequestUrl().split("/"); 
		String url = "";
		for (int i=0;i < arSplitUrl.length;i++ ) {
			
			if( i < 3) {
				url+= arSplitUrl[i]+"/";
			}
		}
		

		url= StringUtils.removeEnd(url, "/");
		
		List<GrantedAuthority> auth = (List<GrantedAuthority>) authentication.getAuthorities();
		String role = auth.get(0).getAuthority();
        ;
		
        boolean flag = true;
        
		//permitAll 
        for (ConfigAttribute attribute : attributes) {
        	
        	System.out.println("attributes::" + attributes);
        	
            if (attribute.toString().equalsIgnoreCase("urlCheck")) {
                
            	MenuDto menuDto = new MenuDto();
            	menuDto.setUserRoleSecd(role);
            	menuDto.setMenuType("root");
        		List<MenuDto> menulist = commonService.MenuList(menuDto);
        		
                for (MenuDto menu : menulist) {
                    
                	if(StringUtils.isEmpty(menu.getMenuUrl())) {
                		continue;
                	}
                	
//                	String[] arSplitUrl = menu.getMenuUrl().split("/"); 
//                	String url = "";
//                	
//                	for (int i=0;i < arSplitUrl.length;i++ ) {
//                		if(i==arSplitUrl.length ) break;
//                		url+= "/"+arSplitUrl[i];
//                	}
                
                	
                	
                	
                	log.info("URL::::::::"+url);
                	
                    if ( menu.getMenuUrl().contains(url))  {
                        
                        log.info("URL MATCHED:::" + menu.getMenuUrl());
                        log.info("URL MATCHED!");
                        log.info("URL MATCHED:::" + object.toString());
                        
                        return ACCESS_GRANTED;
                    } 
                }   
                return ACCESS_DENIED;
                
            } else if (attribute.toString().equalsIgnoreCase("myPageCheck")) {
            	//:TODO 동적 메뉴 구성
            	
            	//마이페이지 비로그인 유저 접근제한
            	if (authentication instanceof AnonymousAuthenticationToken) {
            		return ACCESS_DENIED;
            	} else {
            		return ACCESS_GRANTED;
            	}
            	
            	
            } else if (attribute.toString().equalsIgnoreCase("commonCheck")) {
            	MenuDto menuDto = new MenuDto();
            	menuDto.setUserRoleSecd(role);
            	menuDto.setMenuType("root");
        		List<MenuDto> menulist = commonService.MenuList(menuDto);
        		
        		for (MenuDto menu : menulist) {
                    
                	if(StringUtils.isEmpty(menu.getMenuUrl())) {
                		continue;
                	}
    
                    if (url.contains(menu.getMenuUrl()))  {
                        
                        log.info("URL MATCHED:::" + menu.getMenuUrl());
                        log.info("URL MATCHED!");
                        log.info("URL MATCHED:::" + object.toString());
                        
                        return ACCESS_GRANTED;
                    } 
                }   

            	return ACCESS_GRANTED;
            }
        }
        
		return ACCESS_GRANTED;
	}
	
}
