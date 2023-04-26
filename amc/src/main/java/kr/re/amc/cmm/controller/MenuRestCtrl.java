package kr.re.amc.cmm.controller;

import static kr.re.amc.utils.ApiUtils.success;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kr.re.amc.cmm.dto.CodeDto;
import kr.re.amc.cmm.dto.MenuDto;
import kr.re.amc.cmm.service.MenuService;
import kr.re.amc.utils.ApiUtils.ApiResult;

@RestController
public class MenuRestCtrl {
	
	@Resource(name="menuService")
	private MenuService menuService;
	
	
	@GetMapping(value="/my/management/menu/list")
	public ApiResult<List<HashMap<String, Object>>> getMenuList(MenuDto menuDto){
		
		List<HashMap<String, Object>> inqMenulist = menuService.inqMenulist(menuDto);
		return success(inqMenulist);
	}
	
    @GetMapping(value = "/my/management/menu/{menuId:.+(?<!\\.js)$}")
    public ApiResult<MenuDto> getMenu( @PathVariable String menuId){

        return success(menuService.getMenu(menuId));
    }
    
	@PostMapping(value = "/my/management/menu/mod")
	public ApiResult<List<HashMap<String, Object>>> modMenu(@RequestBody MenuDto menuDto) throws Exception {
		
		return success(menuService.modMenu(menuDto));
	}    
	
	@PostMapping(value = "/my/management/menu/reg")
	public ApiResult<List<HashMap<String, Object>>> regMenu(@RequestBody MenuDto menuDto) throws Exception {
		
		return success(menuService.regMenu(menuDto));
	} 
	
	@PostMapping(value = "/my/management/menu/delete")
	public ApiResult<List<HashMap<String, Object>>> deleteMenu(@RequestBody MenuDto menuDto) throws Exception {
		
		return success(menuService.deleteMenu(menuDto));
	}   
	
	
	@GetMapping(value="/my/management/menu/role/list")
	public ApiResult<List<CodeDto>> getMenuRoleList(MenuDto menuDto){
		
		List<CodeDto> inqMenulist = menuService.inqMenuRoleList(menuDto);
		return success(inqMenulist);
	}
	
	@GetMapping(value="/my/management/menu/auth/list")
	public ApiResult<List<HashMap<String, Object>>> getMenuAuthList(MenuDto menuDto){
		
		List<HashMap<String, Object>> inqMenulist = menuService.inqMenuAuthList(menuDto);
		return success(inqMenulist);
	}	
	
	
	@PostMapping(value = "/my/management/menu/auth/mod")
	public ApiResult<List<HashMap<String, Object>>> modMenuAuth(@RequestBody List<MenuDto> menuDto) throws Exception {
		
		
		return success(menuService.modMenuAuth(menuDto));
	} 	
	
	
}
