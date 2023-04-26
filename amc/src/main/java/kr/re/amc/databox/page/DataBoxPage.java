package kr.re.amc.databox.page;

import java.util.Arrays;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.re.amc.commons.LoginUser;
import kr.re.amc.commons.LoginUserInfo;
import kr.re.amc.util.AmcUtil;

@Controller
public class DataBoxPage {

	
    // 데이터박스 리스트
    @GetMapping(value = "/data/box/main")
    public String storageList() {

        return "pages/databox/dataBoxList";
    }
    
    // 데이터박스 사용신청
    @GetMapping(value = "/data/box/req")
    public String storageReq() throws Exception {
        return "pages/databox/dataBoxReq";
    }
    
    // 관리자 마이페이지 데이터박스 사용신청
    @GetMapping(value = "/my/databox/list")
    public String myDataBoxList() {

        return "pages/databox/myDataBoxList";
    }
    
    // 마이페이지(관리자)  > 데이터박스 신청 상세
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/my/admin/databoxView")
    public String myAdminDataBoxView() {
    	
    	if("ROLE_ADMIN".equals(AmcUtil.getPrincipal().getUserRoleSecd())){
    		return "pages/databox/myAdmDataBoxView";
    	} else if("ROLE_USER".equals(AmcUtil.getPrincipal().getUserRoleSecd())){
    		return "pages/databox/myDataBoxView";
    	} else {
    		return "redirect:/";
    	}
    }
}
