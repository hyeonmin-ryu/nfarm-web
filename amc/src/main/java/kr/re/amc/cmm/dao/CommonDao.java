package kr.re.amc.cmm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.cmm.dto.CodeDto;
import kr.re.amc.cmm.dto.MenuDto;

@Repository("commonDao")
public class CommonDao extends EgovComAbstractDAO{

	public List<MenuDto> inqMenuList(MenuDto menuDto) {
		return selectList("commonDao.inqMenuList" ,menuDto );
	}

	public List<CodeDto> inqCodeList(String code) {
		return selectList("commonDao.inqCodeList" ,code );
	}
	public List<CodeDto> inqCodeGroupList() {
		return selectList("commonDao.inqCodeGroupList");
	}
	
	public int delCode(String nm) {
		return delete("commonDao.delCode", nm);
	}
	
	public int regCode(CodeDto codeDto){
		return insert("commonDao.regCode", codeDto);
	}
	
	public Long inqmaxIndctOrdr(CodeDto codeDto) {
		return selectOne("commonDao.inqmaxIndctOrdr", codeDto); 
	}
	
	public int regCodeGroup(CodeDto codeDto) {
		return insert("commonDao.regCodeGroup", codeDto);
	}
	
	public String chkRegCode(CodeDto codeDto) {
		return selectOne("commonDao.chkRegCode", codeDto);
	}
	
	public String chkRegCodeGroup(CodeDto codeDto) {
		return selectOne("commonDao.chkRegCodeGroup", codeDto);
	}
	
	public int delCodeGroup(String codeGroup) {
		delete("commonDao.delCodeAll", codeGroup);
		return delete("commonDao.delCodeGroup", codeGroup);
	}
}
