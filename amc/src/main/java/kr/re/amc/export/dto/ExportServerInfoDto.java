package kr.re.amc.export.dto;

import static org.springframework.beans.BeanUtils.copyProperties;
import lombok.Data;
@Data
public class ExportServerInfoDto {
	
	private Long tkoutReqstNo;
	private String tkoutSvrIpAdres;
	private String tkoutSvrUserId;
	private String tkoutSvrUserPassword;
	private String tkoutTrgetCours;
	private Long creatUserId;
	private String creatDt;
	private String creatAppId;
	private Long updtUserId;
	private String updtDt;
	private String updtAppId;

	public ExportServerInfoDto(ExportReqDto exportReqDto) {
		copyProperties(exportReqDto, this);
	}
}
