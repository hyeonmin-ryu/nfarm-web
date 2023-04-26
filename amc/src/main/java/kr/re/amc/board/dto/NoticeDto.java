package kr.re.amc.board.dto;

import java.util.List;

/*
 * @package : com.itsm.dranswer.board
 * @name : NoticeDto.java
 * @date : 2021-10-08 오후 1:47
 * @author : xeroman.k
 * @version : 1.0.0
 * @modifyed :
 */
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
public class NoticeDto  {

	private Long noticeNo;
	
	private String noticeSj;
	
	private String imprtncYn;
	
	private Integer inqireCo;
	
	private String noticeCn;
	
	private Long creatUserId;
	
	private String creatDt;
	
	private String creatAppId;
	
	private Long updtUserId;
	
	private String updtDt;
	
	private String updtAppId;
    
    private NoticeDto prev;
    private NoticeDto next;

    private List<NoticeFileDto> noticeFiles;
    
    public void setPrevAndNext(NoticeDto prev, NoticeDto next) {
        this.prev = prev;
        this.next = next;
    }
    
    public void incCnt() {
    	this.inqireCo++;
    }
}

