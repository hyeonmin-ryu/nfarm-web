package kr.re.amc.cmm.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CodePage {

	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping(value="/my/code/list")
	public String codeList() {
		return "pages/code/codeList";
	}
}
