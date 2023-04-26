package kr.re.amc.cmm.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuPage {
	
	@GetMapping(value="/my/menu/list")
	public String menuListPage() {
		return "pages/menu/menuList";
	}
	
	@GetMapping(value="/my/menu/auth/list")
	public String menuAuthListPage() {
		return "pages/menu/menuAuthList";
	}	
	
}
