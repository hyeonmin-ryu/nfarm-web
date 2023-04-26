package kr.re.amc.agency.Service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import kr.re.amc.agency.Service.AdminAgencyService;
import kr.re.amc.agency.dao.AdminAgencyDao;
import kr.re.amc.agency.dto.AgencySearchDto;
import kr.re.amc.users.dto.AgencyDto;
import lombok.extern.slf4j.Slf4j;

@Service("adminAgencyService")
@Slf4j
public class AdminAgencyServiceImpl extends EgovAbstractServiceImpl implements AdminAgencyService {

	@Resource(name = "adminAgencyDao")
	private AdminAgencyDao agencyDao;

	@Override
	public List<AgencyDto> inqAgencyList(AgencySearchDto agencySearchDto) {
		return agencyDao.inqAgencyList(agencySearchDto);
	}

	@Override
	public AgencyDto getAgencySeq(Long agencySeq) {
		return agencyDao.getAgencySeq(agencySeq);
	}

	@Override
	public int regAgency(AgencyDto agencyDto) {
		if(agencyDto.getInsttId() == null) {
			return agencyDao.regAgency(agencyDto);
		}else {
			return agencyDao.udptAgency(agencyDto);
		}

	}

	@Override
	public int delAgency(AgencyDto agencyDto) {
		return agencyDao.delAgency(agencyDto.getInsttId());
	}
}
