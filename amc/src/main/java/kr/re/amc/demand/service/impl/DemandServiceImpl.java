package kr.re.amc.demand.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.re.amc.demand.dao.DemandDao;
import kr.re.amc.demand.dto.DemandCostDto;
import kr.re.amc.demand.dto.DemandSearchDto;
import kr.re.amc.demand.dto.ProductDemandCostDto;
import kr.re.amc.demand.dto.ProductDemandCostPopDto;
import kr.re.amc.demand.service.DemandService;
import kr.re.amc.users.dao.UserDao;
import kr.re.amc.users.dto.UserInfoDto;
import kr.re.amc.util.AmcUtil;

@Service("demandService")
public class DemandServiceImpl extends EgovAbstractServiceImpl implements DemandService{

	@Resource(name="demandDao")
	private DemandDao demandDao;
	
	@Resource(name="userDao")
	private UserDao userDao;
	
	@Value("${ORGNAME}")
    private String ORGNAME;
	
	@Value("${ORGCEO}")
    private String ORGCEO;
	
	@Value("${ORGBLNUM}")
    private String ORGBLNUM;
	
	public List <DemandCostDto> getAgencyDemandList (DemandSearchDto demandSearchDto) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMM");
		String yyyyMM = fm.format(new Date());
		
		demandSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
		
		/*if(ObjectUtils.isEmpty(demandSearchDto.getEndMonth())) {
			demandSearchDto.setEndMonth(yyyyMM);
		}*/
		demandSearchDto.setEndMonth("202211");
		List <DemandCostDto> selList = demandDao.inqAgencyDemandList(demandSearchDto);
		return selList;
	}
	
	
	public List<DemandCostDto> getAgencyDemandChart(DemandSearchDto demandSearchDto) {
		demandSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
		List<DemandCostDto> selList = demandDao.inqAgencyDemandChart(demandSearchDto);
		
		

		return selList;
	}
	
	public List <DemandCostDto> getAgencyDemandView(DemandSearchDto demandSearchDto){
		List <DemandCostDto> selList = demandDao.getAgencyDemandView(demandSearchDto);
		
		return selList;
	}
	
	
	public ProductDemandCostPopDto getAgencyDemandPop(DemandSearchDto demandSearchDto) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMM");
		String yyyyMM = fm.format(new Date());
		
		if(ObjectUtils.isEmpty(demandSearchDto.getEndMonth())) {
			demandSearchDto.setEndMonth(yyyyMM);
		}
		ProductDemandCostPopDto productDemandCostPopDto = new ProductDemandCostPopDto();
		
		productDemandCostPopDto.setSupOrgName(ORGNAME);
		productDemandCostPopDto.setSupOrgCeo(ORGCEO);
		productDemandCostPopDto.setSupOrgBlNumber(ORGBLNUM);
		
		UserInfoDto userInfoDto =userDao.getUserSeq(AmcUtil.getPrincipal().getUserId());
		
		if(ObjectUtils.isNotEmpty(userInfoDto)) {
			productDemandCostPopDto.setDemOrgName(userInfoDto.getInsttNm());
			productDemandCostPopDto.setDemOrgCeo(userInfoDto.getRprsntvNm());
			productDemandCostPopDto.setDemOrgBlNumber(userInfoDto.getBsnmNO());
		}
		
		List<ProductDemandCostDto> selList = demandDao.getAgencyDemandPop(demandSearchDto);
		
		productDemandCostPopDto.setProductDemandCostList(selList);
		return productDemandCostPopDto;
	}
	
	public ProductDemandCostPopDto getAgencyDemandExcel(DemandSearchDto demandSearchDto) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMM");
		String yyyyMM = fm.format(new Date());
		
		if(ObjectUtils.isEmpty(demandSearchDto.getDemandMonth())) {
			demandSearchDto.setDemandMonth(yyyyMM);
		}
		
		ProductDemandCostPopDto productDemandCostPopDto = new ProductDemandCostPopDto();
		
		productDemandCostPopDto.setSupOrgName(ORGNAME);
		productDemandCostPopDto.setSupOrgCeo(ORGCEO);
		productDemandCostPopDto.setSupOrgBlNumber(ORGBLNUM);
		
		UserInfoDto userInfoDto = userDao.getUserSeq(AmcUtil.getPrincipal().getUserId());
		
		if(ObjectUtils.isNotEmpty(userInfoDto)) {
			productDemandCostPopDto.setDemOrgName(userInfoDto.getInsttNm());
			productDemandCostPopDto.setDemOrgCeo(userInfoDto.getRprsntvNm());
			productDemandCostPopDto.setDemOrgBlNumber(userInfoDto.getBsnmNO());
		}
		
		
		List<ProductDemandCostDto> celList = demandDao.getAgencyDemandExcel(demandSearchDto);
		
		productDemandCostPopDto.setProductDemandCostList(celList);
		
		return productDemandCostPopDto;
	}
	
	public ArrayList<ProductDemandCostDto> getAgencyDemandListExcel(DemandSearchDto demandSearchDto) {
		SimpleDateFormat fm = new SimpleDateFormat("yyyyMM");
		String yyyyMM = fm.format(new Date());
	
		/*if(ObjectUtils.isEmpty(demandSearchDto.getEndMonth())) {
			demandSearchDto.setEndMonth(yyyyMM);
		}*/
		demandSearchDto.setEndMonth("202211");
		demandSearchDto.setUserId(AmcUtil.getPrincipal().getUserId());
		
		List<ProductDemandCostDto> pagerr = demandDao.demandAgencyListBatchExcel(demandSearchDto);
		ArrayList<ProductDemandCostDto> arrayList = new ArrayList<ProductDemandCostDto>();
		arrayList.addAll(pagerr);
		
		return arrayList;
	
	}
}
