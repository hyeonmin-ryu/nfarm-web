package kr.re.amc.cmm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import kr.re.amc.cmm.dao.MenuDao;
import kr.re.amc.cmm.dto.CodeDto;
import kr.re.amc.cmm.dto.MenuDto;
import kr.re.amc.cmm.service.MenuService;
import kr.re.amc.util.AmcUtil;

@Service("menuService")
public class MenuServiceImpl extends EgovAbstractServiceImpl implements MenuService {

	@Resource(name ="menuDao")
	private MenuDao menuDao;

	@Override
	public ArrayList<HashMap<String, Object>> inqMenulist(MenuDto menuDto) {
		
		
		List<MenuDto> dto = menuDao.inqMenulist(menuDto);
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		ArrayList<HashMap<String, Object>> innerList = new ArrayList<>();
		HashMap<String, Object> mainMap = new HashMap<String,Object>();
		for (MenuDto mainMenuList : dto) {
			
			HashMap<String, Object> map = new HashMap<String,Object>();
			
			map.put("id", mainMenuList.getMenuId());
			map.put("label", mainMenuList.getMenuNm());
			innerList.add(map);
			
			
			mainMap.put("children", innerList);
			
			recurs(mainMenuList.getMenuId(), map  );
			
			
		}
		list.add(mainMap);
		
		
		return list ;
	}
	
	public HashMap<String, Object> recurs(String menuId, HashMap<String, Object> map    ) {
		
		
		ArrayList<HashMap<String, Object>> subList = new ArrayList<>();
		
		List<MenuDto> subMenu = menuDao.inqSubMenulist(menuId);
		
		for (MenuDto subMenuList : subMenu) {
			
			HashMap<String, Object> subMap = new HashMap<>();
	
			subMap.put("id", subMenuList.getMenuId());
			subMap.put("label", subMenuList.getMenuNm());
			subList.add(subMap);
			map.put("children", subList);
			
			//재귀호출
			recurs(subMenuList.getMenuId(), subMap  );
		}
		
		return map;
		
	}

	@Override
	public MenuDto getMenu(String menuId) {
		return menuDao.inqMenu(menuId);
	}

	@Override
	@CacheEvict(value = "getMenu", allEntries = true)
	public ArrayList<HashMap<String, Object>> modMenu(MenuDto menuDto) {

		menuDto.setUpdtUserId(AmcUtil.getPrincipal().getUserId());
		menuDao.modMenu(menuDto);
		
		return inqMenulist(menuDto);
	}

	@Override
	@CacheEvict(value = "getMenu", allEntries = true)
	public ArrayList<HashMap<String, Object>> regMenu(MenuDto menuDto) {
		
		menuDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
		menuDao.regMenu(menuDto);
		
		return inqMenulist(menuDto);
	}

	@Override
	@CacheEvict(value = "getMenu", allEntries = true)
	public ArrayList<HashMap<String, Object>> deleteMenu(MenuDto menuDto) {
		
		menuDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
		menuDao.deleteMenu(menuDto);
		
		return inqMenulist(menuDto);
	}

	@Override
	public List<CodeDto> inqMenuRoleList(MenuDto menuDto) {

		return menuDao.inqMenuRole(menuDto);
	}

	@Override
	public List<HashMap<String, Object>> inqMenuAuthList(MenuDto menuDto) {
		List<MenuDto> dto = menuDao.inqMenuAuthRootList(menuDto);
		
		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
		ArrayList<HashMap<String, Object>> innerList = new ArrayList<>();
		HashMap<String, Object> mainMap = new HashMap<String,Object>();
		for (MenuDto mainMenuList : dto) {
			
			HashMap<String, Object> map = new HashMap<String,Object>();
			
			map.put("id", mainMenuList.getMenuId());
			map.put("label", mainMenuList.getMenuNm());
			map.put("checkId", mainMenuList.getCheckId());
			
			
			innerList.add(map);
			
			
			mainMap.put("children", innerList);
			
			recursRoleMenu(mainMenuList, map  );
			
			
		}
		
		//현재 등록된메뉴 조회
		List<MenuDto> chkdto = menuDao.inqMenuAuthList(menuDto);
		
		
		HashMap<String, Object> suMap = new HashMap<String,Object>();
		
		suMap.put("chkList", chkdto);
		
		list.add(mainMap);
		list.add(suMap);
		
		return list ;
	}
	
	
	public HashMap<String, Object> recursRoleMenu(MenuDto menuDto, HashMap<String, Object> map    ) {
		
		
		ArrayList<HashMap<String, Object>> subList = new ArrayList<>();
		
		List<MenuDto> subMenu = menuDao.inqSubMenuAuthlist(menuDto);
		
		for (MenuDto subMenuList : subMenu) {
			
			HashMap<String, Object> subMap = new HashMap<>();
	
			subMap.put("id", subMenuList.getMenuId());
			subMap.put("label", subMenuList.getMenuNm());
			subMap.put("checkId", subMenuList.getCheckId());
			subList.add(subMap);
			map.put("children", subList);
			
			//재귀호출
			recursRoleMenu(subMenuList, subMap  );
		}
		
		return map;
		
	}

	@Override
	@CacheEvict(value = "getMenu",  allEntries = true)
	public List<HashMap<String, Object>> modMenuAuth(List<MenuDto> menuListt) {

		menuDao.deleteMenuAuth(menuListt.get(0));
		for (MenuDto menuDto : menuListt) {
			menuDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
			menuDao.regMenuAuth(menuDto);
		}
		
		
		
		return inqMenuAuthList(menuListt.get(0));
	}	
	
}
