package kr.re.amc.board.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import kr.re.amc.board.dto.BoardSearchDto;
import kr.re.amc.board.dto.FaqDto;
import kr.re.amc.board.dto.InquiryDto;
import kr.re.amc.board.dto.NoticeDto;


public interface BoardService {
	
	List<NoticeDto> inqNoticeList(BoardSearchDto boardsearchDto);
	
	List<InquiryDto> inqInquiryList(BoardSearchDto boardsearchDto);
	
	List<FaqDto> inqFaqList(BoardSearchDto boardsearchDto);
	
	NoticeDto inqNoticeSeq(Long noticeSeq);

	InquiryDto inqInquirySeq(Long inquirySeq);
	
	FaqDto inqFaqSeq(Long faqSeq);

	int regNotice(NoticeDto noticeDto);
	
	InquiryDto regInquiry(InquiryDto inquiryDto);
	
	int regFaq(FaqDto faqDto);
	
	int delNotice(Long noticeSeq);
	
	int delInquiry(Long inquirySeq);
	
	int delFaq(Long faqSeq);
	
	NoticeDto regNoticeFile(String sNoticeSeq, List<MultipartFile> multipartFiles) throws InterruptedException;
	
	InquiryDto regInquiryFile(String sNoticeSeq, List<MultipartFile> multipartFiles) throws InterruptedException;

	void downloadFileBoard(String filePath, String fileName, HttpServletRequest request, HttpServletResponse response) throws InterruptedException, FileNotFoundException, IOException;
}
