package kr.re.amc.cmm.dto;

/*
 * @package : com.itsm.dranswer.board
 * @name : NoticeDto.java
 * @date : 2021-10-08 오후 1:47
 * @author : xeroman.k
 * @version : 1.0.0
 * @modifyed :
 */

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
public class CodeDto  {
	
	private String codeGroup;
	
	private Long indctOrdr;
	
	private Long creatUserId;
	
	private String creatDt;
	
    private String nm;
    private String dc;
}

