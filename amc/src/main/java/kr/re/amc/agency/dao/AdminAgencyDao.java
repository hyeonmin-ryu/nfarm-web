package kr.re.amc.agency.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import kr.re.amc.agency.dto.AgencySearchDto;
import kr.re.amc.cmm.EgovComAbstractDAO;
import kr.re.amc.users.dto.AgencyDto;

@Repository("adminAgencyDao")
public class AdminAgencyDao extends EgovComAbstractDAO{

	public List<AgencyDto> inqAgencyList(AgencySearchDto agencySearchDto){
		return selectList("adminAgencyDao.inqAgencyList", agencySearchDto);
	}
	
	public AgencyDto getAgencySeq(Long agencySeq) {
		return selectOne("adminAgencyDao.getAgencySeq", agencySeq);
	}
	
	public int regAgency(AgencyDto agencyDto) {
		return insert("adminAgencyDao.regAgency", agencyDto);
	}
	
	public int udptAgency(AgencyDto agencyDto) {
		return update("adminAgencyDao.updtAgency", agencyDto);
	}
	
	public int delAgency(Long agencySeq) {
		return delete("adminAgencyDao.delAgency", agencySeq);
	}
	
}
