package kr.re.amc.demand.page;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemandPage {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping(value = "/my/demand/list")
    public String myDemandList() {
        return "pages/demand/demandList";
    }
	
	@PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/my/agency/demandView")
    public String myAgencyDataBoxView() {
        return "pages/demand/myAgencyView";
    }
	
	@GetMapping(value = "/my/admin/demand/list")
    public String admDemandList() {
        return "pages/demand/adminDemandList";
    }
    
	@PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/my/admin/demandView")
    public String myAdminDataBoxView() {
        return "pages/demand/adminDemandView";
    }
}
