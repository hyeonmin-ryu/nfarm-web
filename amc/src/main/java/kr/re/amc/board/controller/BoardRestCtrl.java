package kr.re.amc.board.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import kr.re.amc.board.dto.BoardSearchDto;
import kr.re.amc.board.dto.FaqDto;
import kr.re.amc.board.dto.InquiryDto;
import kr.re.amc.board.dto.NoticeDto;
import kr.re.amc.board.service.BoardService;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class BoardRestCtrl {

	@Resource(name = "boardService")
	private BoardService boardService;

	@GetMapping(value = "/board/notice/list")
	public ApiResult<PageInfo<NoticeDto>> inqNoticeList(BoardSearchDto boardsearchDto) {

		// PAGEING처리
		PageHelper.startPage(boardsearchDto.getPage(), boardsearchDto.getSize());
		List<NoticeDto> noticelist = boardService.inqNoticeList(boardsearchDto);
		PageInfo<NoticeDto> pageData = new PageInfo<>(noticelist);
		
		return success(pageData);
	}

	@GetMapping(value = "/board/inquiry/list")
	public ApiResult<PageInfo<InquiryDto>> inqInquiryList(BoardSearchDto boardsearchDto) {

		PageHelper.startPage(boardsearchDto.getPage(), boardsearchDto.getSize());
		List<InquiryDto> inquirylist = boardService.inqInquiryList(boardsearchDto);
		PageInfo<InquiryDto> pageData = new PageInfo<>(inquirylist);

		return success(pageData);
	}

	@GetMapping(value = "/board/faq/list")
	public ApiResult<PageInfo<FaqDto>> inqFaqList(BoardSearchDto boardsearchDto) {

		PageHelper.startPage(boardsearchDto.getPage(), boardsearchDto.getSize());
		List<FaqDto> faqlist = boardService.inqFaqList(boardsearchDto);
		PageInfo<FaqDto> pageData = new PageInfo<>(faqlist);
		return success(pageData);
	}

	@GetMapping(value = "/board/notice/get/{noticeNo:.+(?<!\\.js)$}")
	public ApiResult<NoticeDto> inqNoticeSeq(@PathVariable Long noticeNo) {

		return success(boardService.inqNoticeSeq(noticeNo));
	}

	@GetMapping(value = "/board/inquiry/get/{inqryNo:.+(?<!\\.js)$}")
	public ApiResult<InquiryDto> inqInquirySeq(@PathVariable Long inqryNo) {
		InquiryDto inquiryDto = boardService.inqInquirySeq(inqryNo);
		return success(inquiryDto);
	}

	@GetMapping(value = "/board/faq/get/{questNo:.+(?<!\\.js)$}")
	public ApiResult<FaqDto> inqFaqSeq(@PathVariable Long questNo) {

		FaqDto faqDto = boardService.inqFaqSeq(questNo);
		return success(faqDto);
	}

	@Secured(value = {"ROLE_ADMIN"})
	@PostMapping(path = "/board/notice/save")
	public ApiResult<NoticeDto> regNotice(@RequestBody NoticeDto noticeDto) {
		boardService.regNotice(noticeDto);
		return success(boardService.inqNoticeSeq(noticeDto.getNoticeNo()));

	}

	@PostMapping(path = "/board/inquiry/save")
	public ApiResult<InquiryDto> regInquiry(@RequestBody InquiryDto inquiryDto) {
		return success(boardService.regInquiry(inquiryDto));
	}

	@Secured(value = {"ROLE_ADMIN"})
	@PostMapping(path = "/board/faq/save")
	public ApiResult<FaqDto> regFaq(@RequestBody FaqDto faqDto) {
		boardService.regFaq(faqDto);
		
		return success(boardService.inqFaqSeq(faqDto.getQuestNo()));
	}

	
	@PostMapping(path = "/board/notice/file/upload") 
	public ApiResult<NoticeDto>	noticeFileUpload(MultipartHttpServletRequest request) throws InterruptedException{
		
		List<MultipartFile>	multipartFiles = request.getFiles("multipartFile");
		String sNoticeSeq = request.getParameter("noticeNo");
	 
		return success(boardService.regNoticeFile(sNoticeSeq, multipartFiles)); 
	}
	 
	@PostMapping(path = "/board/inquiry/file/upload") 
	public ApiResult<InquiryDto> InquiryFileUpload(MultipartHttpServletRequest request) throws InterruptedException{
		
		List<MultipartFile>	multipartFiles = request.getFiles("multipartFile");
		String sInquirySeq = request.getParameter("inqryNo");
	 
		return success(boardService.regInquiryFile(sInquirySeq, multipartFiles)); 
	}
	
	@Secured(value = {"ROLE_ADMIN"})
	@PostMapping(value = "/board/notice/delete/{noticeSeq:.+(?<!\\.js)$}")
	public ApiResult<NoticeDto> delNotice(@PathVariable Long noticeSeq) {
		boardService.delNotice(noticeSeq);

		return success(null);
	}

	@PostMapping(value = "/board/inquiry/delete/{inqryNo:.+(?<!\\.js)$}")
	public ApiResult<NoticeDto> delInquiry(@PathVariable Long inqryNo) {
		boardService.delInquiry(inqryNo);

		return success(null);
	}
	@Secured(value = {"ROLE_ADMIN"})
	@PostMapping(value = "/board/faq/delete/{questNo:.+(?<!\\.js)$}")
	public ApiResult<NoticeDto> delFaq(@PathVariable Long questNo) {
		boardService.delFaq(questNo);

		return success(null);
	}
	
	@GetMapping(value = "/board/downloadFile")
    public void  downloadFileBoard(
            String fileCours,
            String fileNm,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Exception{
    	System.out.println("RESTCTRLLLLLLL" + fileCours + fileNm);
    	boardService.downloadFileBoard(fileCours, fileNm,  request, response);
    }    
    
    
    
}
