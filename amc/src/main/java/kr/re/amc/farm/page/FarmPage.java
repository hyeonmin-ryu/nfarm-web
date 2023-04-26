package kr.re.amc.farm.page;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import kr.re.amc.util.AmcUtil;

@Controller
public class FarmPage {
	//리스트 기본 조
    @GetMapping(value = "/farm/listPage")
    public String farmList() {
        return "pages/farm/farmList";
    }
}
