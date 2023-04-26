package kr.re.amc.board.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.board.dto.BoardSearchDto;
import kr.re.amc.board.dto.FaqDto;
import kr.re.amc.board.dto.InquiryDto;
import kr.re.amc.board.dto.InquiryFileDto;
import kr.re.amc.board.dto.NoticeDto;
import kr.re.amc.board.dto.NoticeFileDto;
import kr.re.amc.cmm.EgovComAbstractDAO;

@Repository("boardDao")
public class BoardDao extends EgovComAbstractDAO{

	public List<NoticeDto> inqNoticeList(BoardSearchDto boardSearchDto) {
		return selectList("boardDao.inqNoticeList" ,boardSearchDto );
	}
	
	public List<InquiryDto> inqInquiryList(BoardSearchDto boardSearchDto){
		return selectList("boardDao.inqInquiryList", boardSearchDto);
	}
	
	public List<FaqDto> inqFaqList(BoardSearchDto boardSearchDto){
		return selectList("boardDao.inqFaqList", boardSearchDto);
	}

	public NoticeDto inqNoticeSeq(Long noticeSeq) {
		return selectOne("boardDao.inqNoticeSeq", noticeSeq);
	}
	
	public List<NoticeFileDto> inqNoticeFileList(Long noticeSeq) {
		return selectList("boardDao.inqNoticeFileList", noticeSeq);
	}
	
	public List<InquiryFileDto> inqInquiryFileList(Long inquirySeq) {
		return selectList("boardDao.inqInquiryFileList", inquirySeq);
	}
	
	public InquiryDto inqInquirySeq(Long inquirySeq) {
		return selectOne("boardDao.inqInquirySeq", inquirySeq);
	}
	
	public FaqDto inqFaqSeq(Long faqSeq) {
		return selectOne("boardDao.inqFaqSeq", faqSeq);
	}
	
	public int regNotice(NoticeDto noticeDto) {
		return insert("boardDao.regNotice", noticeDto);
	}
	
	public int regInquiry(InquiryDto inquiryDto) {
		return insert("boardDao.regInquiry", inquiryDto);
	}
	
	public List<InquiryDto> inqInquiryReplyList(Long inquirySeq) {
		return selectList("boardDao.inqInquiryReplyList", inquirySeq);
	}
	
	public InquiryDto inqInquiryReplySeq(Long inquirySeq) {
		return selectOne("boardDao.inqInquiryReplySeq", inquirySeq);
	}
	
	public int regFaq(FaqDto faqDto) {
		return insert("boardDao.regFaq", faqDto);
	}
	
	public int regNoticeFile(NoticeFileDto noticeFileDto) {
		return insert("boardDao.regNoticeFile", noticeFileDto);
	}
	
	public int regInquiryFile(InquiryFileDto inquiryFileDto) {
		return insert("boardDao.regInquiryFile", inquiryFileDto);
	}
	
	public int NoticeCount(NoticeDto noticeDto) {
		return update("boardDao.NoticeCount", noticeDto);
	}
	public int updtNotice(NoticeDto noticeDto) {
		return update("boardDao.updtNotice", noticeDto);
	}
	
	public int updtInquiry(InquiryDto inquiryDto) {
		return update("boardDao.updtInquiry", inquiryDto);
	}
	
	public int updtAnswerY(InquiryDto inquiryDto) {
		return update("boardDao.updtAnswerY", inquiryDto);
	}
	public int updtAnswerN(Long inquirySeq) {
		return update("boardDao.updtAnswerN", inquirySeq);
	}
	public int updtFaq(FaqDto faqDto) {
		return update("boardDao.updtFaq", faqDto);
	}
	
	public int delNotice(Long noticeSeq) {
		return delete("boardDao.delNotice", noticeSeq);
	}
	
	public int delNoticeFile(Long noticeSeq) {
		return delete("boardDao.delNoticeFile", noticeSeq);
	}
	
	public int delInquiryFile(Long inquirySeq) {
		return delete("boardDao.delInquiryFile", inquirySeq);
	}
	
	public int delInquiry(Long inquirySeq) {
		return delete("boardDao.delInquiry", inquirySeq);
	}
	
	public int delFaq(Long faqSeq) {
		return delete("boardDao.delFaq", faqSeq);
	}
	
	public NoticeDto NoticeDetailPrev(Long noticeSeq) {
		return selectOne("boardDao.NoticeDetailPrev", noticeSeq);
	}
	
	public NoticeDto NoticeDetailNext(Long noticeSeq) {
		return selectOne("boardDao.NoticeDetailNext", noticeSeq);
	}
	
	public FaqDto FaqDetailPrev(Long faqSeq) {
		return selectOne("boardDao.FaqDetailPrev", faqSeq);
	}
	
	public FaqDto FaqDetailNext(Long faqSeq) {
		return selectOne("boardDao.FaqDetailNext", faqSeq);
	}
}
