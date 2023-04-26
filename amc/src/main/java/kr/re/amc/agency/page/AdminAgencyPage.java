package kr.re.amc.agency.page;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminAgencyPage {

	@GetMapping(value="/my/agency")
	public String myDemandList() {
		return "pages/agency/agencyList";
	}
	
	
	@GetMapping(value = "/my/agency/view")
    public String myAgencyDataBoxView() {

        return "pages/agency/agencyView";
    }
}
