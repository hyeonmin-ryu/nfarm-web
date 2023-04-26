package kr.re.amc.cmm.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @Class Name : SearchVO.java
 * @Description : SearchVO class
 * @Modification Information
 * @
 * @  수정일         수정자                   수정내용
 * @ -------    --------    ---------------------------
 * @ 2009.02.01    조재영         최초 생성
 *
 *  @author 공통서비스 개발팀 조재영
 *  @since 2009.02.01
 *  @version 1.0
 *  @see 
 *  
 */

@Getter
@Setter
@SuppressWarnings("serial")
public class SearchDto implements Serializable {
	
	/** 현재페이지 */
	private int page;
	
	/** 페이지사이즈 */
	private int size;
	
	/** 검색조건 */
    private String searchCondition = "";
    
    /** 검색Keyword */
    private String keyword = "";
    private String dataName = "";
    private String userName = "";
    
    private String questionType = "";
    private String UserType = "";
    private String joinStat ="";
    private String Disease="";
    private String agencySeq="";
   
}
