package kr.re.amc.board.dto;

import javax.persistence.Entity;

import kr.re.amc.etc.FileUploadResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class NoticeFileDto {
	private Long noticeNo;
	private Long fileSn;
	private String fileNm;
	private String fileCours;
	private Long creatUserId;
	private String creatDt;
	private String creatAppId;
	private Long updtUserId;
	private String updtDt;
	private String updtAppId;
	
	
	/*private NoticeDto noticeDto;
	
	public NoticeFileDto(NoticeFileDto noticeFileDto, Long noticeSeq) {
		this.noticeSeq = noticeSeq;
		this.fileName = noticeFileDto.getFileName();
		this.filePath = noticeFileDto.getFilePath();
	}*/
	
	public NoticeFileDto(Long noticeNo, FileUploadResponse.FileObject object) {
		this.noticeNo = noticeNo;
		this.fileNm = object.getOrgFileName();
		this.fileCours = object.getKeyName();
	}
}
