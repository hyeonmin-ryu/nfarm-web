package kr.re.amc.cmm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.re.amc.cmm.dto.CodeDto;
import kr.re.amc.cmm.dto.MenuDto;

public interface MenuService {

	ArrayList<HashMap<String, Object>> inqMenulist(MenuDto menuDto);

	MenuDto getMenu(String menuId);

	ArrayList<HashMap<String, Object>> modMenu(MenuDto menuDto);

	ArrayList<HashMap<String, Object>> regMenu(MenuDto menuDto);

	ArrayList<HashMap<String, Object>> deleteMenu(MenuDto menuDto);

	List<CodeDto> inqMenuRoleList(MenuDto menuDto);

	List<HashMap<String, Object>> inqMenuAuthList(MenuDto menuDto);

	List<HashMap<String, Object>> modMenuAuth(List<MenuDto> menuDto);

}
