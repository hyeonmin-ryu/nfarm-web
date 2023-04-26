package kr.re.amc.cmm.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.cmm.dto.CodeDto;
import kr.re.amc.cmm.dto.MenuDto;

@Repository("menuDao")
public class MenuDao extends EgovComAbstractDAO{

	public List<MenuDto> inqMenulist(MenuDto menuDto) {
		return selectList("menuDao.inqMenuRootList" ,menuDto );
	}

	public List<MenuDto> inqSubMenulist(String menuId) {
		return selectList("menuDao.inqSubMenulist" ,menuId );
	}

	public MenuDto inqMenu(String menuId) {
		return selectOne("menuDao.inqMenu" ,menuId );
	}

	public int modMenu(MenuDto menuDto) {
		return update("menuDao.modMenu" ,menuDto );
		
	}

	public int regMenu(MenuDto menuDto) {
		return insert("menuDao.regMenu" ,menuDto );
		
	}

	public int deleteMenu(MenuDto menuDto) {
		return delete("menuDao.deleteMenu" ,menuDto );
		
	}

	public List<CodeDto> inqMenuRole(MenuDto menuDto) {
		return selectList("menuDao.inqMenuRole" ,menuDto );
	}

	public List<MenuDto> inqMenuAuthRootList(MenuDto menuDto) {
		return selectList("menuDao.inqMenuAuthRootList" ,menuDto );
	}


	public List<MenuDto> inqSubMenuAuthlist(MenuDto menuDto) {
		return selectList("menuDao.inqSubMenuAuthlist" ,menuDto );
	}

	public List<MenuDto> inqMenuAuthList(MenuDto menuDto) {
		return selectList("menuDao.inqMenuAuthList" ,menuDto );
	}

	public int deleteMenuAuth(MenuDto menuDto) {
		return delete("menuDao.deleteMenuAuth" ,menuDto );
		
	}

	public int regMenuAuth(MenuDto menuDto) {
		return insert("menuDao.regMenuAuth" ,menuDto );
		
	}

}
