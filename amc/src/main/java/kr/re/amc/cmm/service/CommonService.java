package kr.re.amc.cmm.service;

import java.util.List;

import kr.re.amc.cmm.dto.CodeDto;
import kr.re.amc.cmm.dto.MenuDto;

public interface CommonService {
	
	List<MenuDto> MenuList(MenuDto menuDto);

	List<CodeDto> inqCodeList(String code);
	
	List<CodeDto> inqCodeGroupList();
	
	int delCode(String nm);
	
	int delCodeGroup(String codeGroup);
	
	CodeDto regCode(CodeDto codeDto);
	
	CodeDto regCodeGroup(CodeDto codeDto);
}
