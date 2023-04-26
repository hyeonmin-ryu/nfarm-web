package kr.re.amc.board.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.re.amc.board.dao.BoardDao;
import kr.re.amc.board.dto.BoardSearchDto;
import kr.re.amc.board.dto.FaqDto;
import kr.re.amc.board.dto.InquiryDto;
import kr.re.amc.board.dto.InquiryFileDto;
import kr.re.amc.board.dto.NoticeDto;
import kr.re.amc.board.dto.NoticeFileDto;
import kr.re.amc.board.service.BoardService;
import kr.re.amc.etc.FileUploadResponse;
import kr.re.amc.storage.service.StorageService;
import kr.re.amc.util.AmcUtil;
import kr.re.amc.util.CustomObjectStorage;

@Service("boardService")
public class BoardServiceImpl extends EgovAbstractServiceImpl implements BoardService {

	@Resource(name = "boardDao")
	private BoardDao boardDao;

	@Resource(name = "storageService")
	private StorageService storageService;

	@Autowired
	private  CustomObjectStorage customObjectStorage;
	
	@Value("${ncp.amc.end-point}")
    private String endPoint;
    @Value("${ncp.amc.region-name}")
    private String regionName;
    @Value("${ncp.amc.server-access-key}")
    private String laifServerAccessKey;
    @Value("${ncp.amc.server-secret-key}")
    private String laifServerSecretKey;
    
	@Override
	public List<NoticeDto> inqNoticeList(BoardSearchDto boardsearchDto) {

		return boardDao.inqNoticeList(boardsearchDto);
	}

	@Override
	public List<InquiryDto> inqInquiryList(BoardSearchDto boardsearchDto) {
		return boardDao.inqInquiryList(boardsearchDto);
	}

	@Override
	public List<FaqDto> inqFaqList(BoardSearchDto boardsearchDto) {
		return boardDao.inqFaqList(boardsearchDto);
	}

	@Override
	public NoticeDto inqNoticeSeq(Long noticeSeq) {

		NoticeDto noticeDto = boardDao.inqNoticeSeq(noticeSeq);

		NoticeDto prev = boardDao.NoticeDetailPrev(noticeSeq);
		NoticeDto next = boardDao.NoticeDetailNext(noticeSeq);

		noticeDto.incCnt();
		noticeDto.setPrevAndNext(prev, next);
		
		boardDao.NoticeCount(noticeDto);
		
		List<NoticeFileDto> noticeFileDto = boardDao.inqNoticeFileList(noticeSeq);
		noticeDto.setNoticeFiles(noticeFileDto);
		
		return noticeDto;
	}

	@Override
	public InquiryDto inqInquirySeq(Long inquirySeq) {

		InquiryDto inquiryDto = boardDao.inqInquirySeq(inquirySeq);
		List<InquiryFileDto> inquiryFileDto = boardDao.inqInquiryFileList(inquirySeq);
		inquiryDto.setInquiryFiles(inquiryFileDto);

		InquiryDto inquiryDto2 = boardDao.inqInquiryReplySeq(inquirySeq);
		if (inquiryDto2 != null) { // 답변이 있는경우

			List<InquiryDto> children = boardDao.inqInquiryReplyList(inquirySeq);

			List<InquiryFileDto> childInquiryFileDto = boardDao.inqInquiryFileList(inquiryDto2.getInqryNo());

			for (InquiryDto inqDto : children) {
				inqDto.setInquiryFiles(childInquiryFileDto);
			}

			inquiryDto.setChildren(children);
		}

		if (inquiryDto.getOrginlInqryNo() != null) { // 답글인 경우

			inqInquirySeq(inquiryDto.getOrginlInqryNo());
			/*InquiryDto childInquiryDto = inquiryDto;

			inquiryDto = boardDao.inqInquirySeq(inquiryDto.getOrgInquirySeq());

			inquiryFileDto = boardDao.inqInquiryFileList(inquiryDto.getInquirySeq());

			inquiryDto.setInquiryFiles(inquiryFileDto);

			inquiryDto.setChildren((List<InquiryDto>) childInquiryDto);*/
		}
		return inquiryDto;
	}

	@Override
	public FaqDto inqFaqSeq(Long faqSeq) {

		FaqDto faqDto = boardDao.inqFaqSeq(faqSeq);

		FaqDto prev = boardDao.FaqDetailPrev(faqSeq);
		FaqDto next = boardDao.FaqDetailNext(faqSeq);

		faqDto.setPrevAndNext(prev, next);

		return faqDto;
	}

	@Override
	public int regNotice(NoticeDto noticeDto) {

		List<NoticeFileDto> noticeFileDtos = noticeDto.getNoticeFiles();
		if (noticeDto.getNoticeNo() == null) {
			noticeDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			return boardDao.regNotice(noticeDto);
		} else {
			noticeDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());

			List<NoticeFileDto> noticeFiles = boardDao.inqNoticeFileList(noticeDto.getNoticeNo()); // 옛날거

			for (NoticeFileDto noticeFileDto : noticeFiles) {
				if (!noticeFileDtos.stream().anyMatch(e -> e.getFileSn().equals(noticeFileDto.getFileSn()))) {
					boardDao.delNoticeFile(noticeFileDto.getFileSn());
				}
			}

			return boardDao.updtNotice(noticeDto);
		}
	}

	@Override
	public InquiryDto regInquiry(InquiryDto inquiryDto) {

		List<InquiryFileDto> inquiryFileDtos = inquiryDto.getInquiryFiles();

		if (inquiryDto.getInqryNo() == null) { // create

			inquiryDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());

			if (inquiryDto.getOrginlInqryNo() != null) { // 댓글인 경우
				boardDao.updtAnswerY(inquiryDto);
			}
			
			boardDao.regInquiry(inquiryDto);
			return inquiryDto;
		} else { // update
			inquiryDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());

			List<InquiryFileDto> inquiryFiles = boardDao.inqInquiryFileList(inquiryDto.getInqryNo());

			for (InquiryFileDto inquiryFileDto : inquiryFiles) {
				if (!inquiryFileDtos.stream().anyMatch(e -> e.getFileSn().equals(inquiryFileDto.getFileSn()))) {
					boardDao.delInquiryFile(inquiryFileDto.getFileSn());
				}
			}
			boardDao.updtInquiry(inquiryDto);
			return inquiryDto;
		}
	}

	@Override
	public int regFaq(FaqDto faqDto) {
		if (faqDto.getQuestNo() == null) {
			faqDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			return boardDao.regFaq(faqDto);
		} else {
			faqDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
			return boardDao.updtFaq(faqDto);
		}
	}

	@Override
	public NoticeDto regNoticeFile(String sNoticeSeq, List<MultipartFile> multipartFiles) throws InterruptedException {
		Long noticeSeq = null;

		try {
			noticeSeq = Long.parseLong(sNoticeSeq);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("공지사항 번호의 형식이 올바르지 않습니다.");
		}
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
		String yyyyMMdd = fm.format(new Date());
		String folderName = "notice/" + yyyyMMdd + "/" + noticeSeq + "/" + AmcUtil.getPrincipal().getUserId() + "/";

		FileUploadResponse response = storageService.uploadBoardFile(folderName, multipartFiles);

		for (FileUploadResponse.FileObject object : response.getListObject()) {

			NoticeFileDto noticeFileDto = new NoticeFileDto(noticeSeq, object);
			boardDao.regNoticeFile(noticeFileDto);
		}
		
		NoticeDto noticeDto = boardDao.inqNoticeSeq(noticeSeq);

		return noticeDto;
	}

	@Override
	public InquiryDto regInquiryFile(String sInquirySeq, List<MultipartFile> multipartFiles)
			throws InterruptedException {
		Long inquirySeq = null;

		try {
			inquirySeq = Long.parseLong(sInquirySeq);
		} catch (NumberFormatException e) {
			throw new NumberFormatException("공지사항 번호의 형식이 올바르지 않습니다.");
		}

		SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
		String yyyyMMdd = fm.format(new Date());
		String folderName = "inquiry/" + yyyyMMdd + "/" + inquirySeq + "/" + AmcUtil.getPrincipal().getUserId()
				+ "/";

		FileUploadResponse response = storageService.uploadBoardFile(folderName, multipartFiles);

		for (FileUploadResponse.FileObject object : response.getListObject()) {

			InquiryFileDto inquiryFileDto = new InquiryFileDto(inquirySeq, object);
			boardDao.regInquiryFile(inquiryFileDto);
		}

		InquiryDto inquiryDto = boardDao.inqInquirySeq(inquirySeq);

		return inquiryDto;
	}

	@Override
	public int delNotice(Long noticeSeq) {
		boardDao.delNoticeFile(noticeSeq);
		return boardDao.delNotice(noticeSeq);
	}

	@Override
	public int delInquiry(Long inquirySeq) {
		InquiryDto inquiryDto = boardDao.inqInquirySeq(inquirySeq);
		
		InquiryDto reply = boardDao.inqInquiryReplySeq(inquirySeq);
		
		if(reply != null) { // 답변이 있을경우
			delInquiry(reply.getInqryNo());
		}
		
		if (inquiryDto.getOrginlInqryNo() != null) { // 답변인 경우
			boardDao.updtAnswerN(inquiryDto.getOrginlInqryNo());
		} 		
		
		boardDao.delInquiryFile(inquirySeq);
		return boardDao.delInquiry(inquirySeq);
	}

	@Override
	public int delFaq(Long faqSeq) {
		return boardDao.delFaq(faqSeq);
	}
	

	public void downloadFileBoard(String filePath, String FileName, HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException  {
		String bucketName = "amc-test-upload-files";
		String filePathReal = "/amc/temp"+"/"+ FileName;
		File targetFile = new File(filePathReal);
		//버킷 파일 다운로드
		
		customObjectStorage.getObject(endPoint, regionName, laifServerAccessKey, laifServerSecretKey, bucketName, filePath , targetFile );
	
        //파일다운로드 START
		response.setHeader("Content-Disposition", "attachment; filename=" + filePath);
        response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Expires", "-1");

		  // 다운로드 위해 추가.
		 FileInputStream fiss = new FileInputStream(filePathReal);
		 BufferedInputStream bis = new BufferedInputStream(fiss);
		 ServletOutputStream so = response.getOutputStream();
		 BufferedOutputStream bos = new BufferedOutputStream(so);
		  
		 byte[] data = new byte[(int)targetFile.length()];
		 int input = 0;
		 while((input = bis.read(data)) != -1) {
		    bos.write(data, 0, input);
		    bos.flush();
		  }
		  
		 if (bos != null) bos.close();
		 if (bis != null) bis.close();
		 if (so != null) so.close();
		 if (fiss != null) fiss.close();			
		
	}
}
