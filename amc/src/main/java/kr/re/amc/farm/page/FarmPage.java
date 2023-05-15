package kr.re.amc.farm.page;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.re.amc.util.AmcUtil;

@Controller
public class FarmPage {
	//리스트 기본 조

    // 농장 DB 리스트
    @GetMapping(value = "/farm/list")
    public String farmList() {

        return "pages/farm/farmList";
    }
    @GetMapping(value = "/grow/list")
    public String growList() {

        return "pages/farm/growList";
    }
    
    @GetMapping(value="/farm/reg")
	public String farmReg() {
		return "pages/farm/farmReg";
	}
    
    @GetMapping(value="/grow/reg")
	public String growReg() {
		return "pages/farm/growReg";
	}
    
    @GetMapping(value="/farm/agencyList")
	public String agencyUserList() {
		return "pages/farm/agencyUserList";
	}
    
    @GetMapping(value="/farm/growUserList")
	public String growUserList() {
		return "pages/farm/growUserList";
	}
}
