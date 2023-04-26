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
public class InquiryFileDto {

	private Long fileSn;
	private Long inqryNo;
	private String fileNm;
	private String fileCours;
	private Long creatUserId;
	private String creatDt;
	private Long updtUserId;
	private String updtDt;

	
	public InquiryFileDto(Long inqryNo, FileUploadResponse.FileObject object) {
		
		this.inqryNo = inqryNo;
		
		this.fileNm = object.getOrgFileName();
		
		this.fileCours = object.getKeyName();
	}
}
