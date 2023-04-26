package kr.re.amc.board.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FaqDto {
	private Long questNo;

	private String questRspnsSj;

	private String questRspnsCn;

	private String questRspnsTycd;

	private Date creatDt;

	private Long creatUserId;

	private Date updtDt;

	private Long updtUserId;
	
	private String questionTypeCode;

	private FaqDto prev;
	private FaqDto next;

	public void setPrevAndNext(FaqDto prev, FaqDto next) {
		this.prev = prev;
		this.next = next;
	}
}
