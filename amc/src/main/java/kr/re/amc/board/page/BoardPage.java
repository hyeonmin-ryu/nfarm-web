package kr.re.amc.board.page;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardPage {
	private final Logger log = LoggerFactory.getLogger(getClass());
	//공지사항 리스트
	@GetMapping(value="/board/notice/main")
	public String boardList() {
		return "pages/board/noticeList";
	}
	//공지사항 상세
	@GetMapping(value="/board/notice/view")
	public String boardView() {
		return "pages/board/noticeView";
	}
	//공지사항 등록
	@GetMapping(value="/board/notice/reg")
	public String noticeReg() {
		return "pages/board/noticeReg";
	}
	//문의하기 목록
	@GetMapping(value="/board/inquiry/main")
	public String inquiryList() {
		return "pages/board/inquiryList";
	}
	//문의하기 상세
	@GetMapping(value="/board/inquiry/view")
	public String inquiryView() {
		return "pages/board/inquiryView";
	}
	//문의하기 등록
	@GetMapping(value="/board/inquiry/reg")
	public String inquiryReg() {
		return "pages/board/inquiryReg";
	}
	@GetMapping(value = "board/inquiry/reply")
    public String inquiryReply() {

        return "pages/board/inquiryReply";
    }
	// FAQ 리스트
	@GetMapping(value="/board/faq/main")
	public String faqList() {
		return "pages/board/faqList";
	}
	// FAQ 상세
	@GetMapping(value="/board/faq/view")
	public String faqView() {
		return "pages/board/faqView";
	}
	// FAQ 등록
	@GetMapping(value="/board/faq/reg")
	public String faqReg() {
		return "pages/board/faqReg";
	}
}
