package kr.re.amc.export.page;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.re.amc.util.AmcUtil;

@Controller
public class ExportPage {
	// 반출 리스트
    @GetMapping(value = "/data/out/main")
    public String storageList() {
        return "pages/export/exportList";
    }
    
    // 반출 사용신청
    @GetMapping(value = "/export/req")
    public String storageReq() {
        return "pages/export/exportReq";
    }
    
    // 마이페이지 반출 신청
    @GetMapping(value = "/my/export/list")
    public String myDataBoxList() {
        if(AmcUtil.getPrincipal().getUserRoleSecd() == "ROLE_MANAGER") {
        	return "pages/export/myManExportList";
    	} else {
    		return "pages/export/myExportList";
    	}
    }
    
    // 마이페이지 >  상세
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/mypage/exportView")
    public String myAdminDataBoxView() {
    	if(AmcUtil.getPrincipal().getUserRoleSecd().equals("ROLE_ADMIN")) {
    		return "pages/export/myAdmExportView";
    	} else if(AmcUtil.getPrincipal().getUserRoleSecd().equals("ROLE_MANAGER")) {
    		return "pages/export/myManExportView";
    	} else {
    		return "pages/export/myOrgExportView";
    	}
    }
}
