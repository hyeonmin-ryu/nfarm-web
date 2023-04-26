package kr.re.amc.cmm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import kr.re.amc.cmm.dao.CommonDao;
import kr.re.amc.cmm.dto.CodeDto;
import kr.re.amc.cmm.dto.MenuDto;
import kr.re.amc.cmm.service.CommonService;
import kr.re.amc.exception.NotFoundException;
import kr.re.amc.util.AmcUtil;

@Service("commonService")
public class CommonServiceImpl extends EgovAbstractServiceImpl implements CommonService {

	@Resource(name ="commonDao")
	private CommonDao commonDao;
	
	
	/**
	 * ROLE별 메뉴 캐싱
	 */
	@Override
	@Cacheable(value = "getMenu", key = "#menuDto.getUserRoleSecd()")
	public List<MenuDto> MenuList(MenuDto menuDto) {
		
		return commonDao.inqMenuList(menuDto);
	}

	/**
	 * 코드조회
	 */
	@Override
	public List<CodeDto> inqCodeList(String code) {
		return commonDao.inqCodeList(code);
	}
	
	@Override
	public List<CodeDto> inqCodeGroupList(){
		return commonDao.inqCodeGroupList();
	}
	
	@Override
	public int delCode(String nm){
		return commonDao.delCode(nm);
	}
	
	@Override
	public int delCodeGroup(String codeGroup) {
		return commonDao.delCodeGroup(codeGroup);
	}
	
	@Override
	public CodeDto regCode(CodeDto codeDto){
		codeDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
		
		Long indctOrdr = commonDao.inqmaxIndctOrdr(codeDto);
		if(indctOrdr == null) 
			codeDto.setIndctOrdr((long) 1);
		else {
			codeDto.setIndctOrdr(indctOrdr + 1);
		}
		
		if(commonDao.chkRegCode(codeDto) ==null) {
			commonDao.regCode(codeDto);
		}else {
			throw new NotFoundException("중복된 코드입니다.");
		}
			
		return codeDto;
	}
	
	@Override
	public CodeDto regCodeGroup(CodeDto codeDto) {
		codeDto.setCreatUserId(AmcUtil.getPrincipal().getUserId());
		
		if(commonDao.chkRegCodeGroup(codeDto) == null) {
			commonDao.regCodeGroup(codeDto);
		}else {
			throw new NotFoundException("중복된 코드입니다.");
		}

		return codeDto;
	}
}
