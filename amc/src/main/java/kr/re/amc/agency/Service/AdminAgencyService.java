package kr.re.amc.agency.Service;

import java.util.List;

import kr.re.amc.agency.dto.AgencySearchDto;
import kr.re.amc.users.dto.AgencyDto;

public interface AdminAgencyService {
	public List<AgencyDto> inqAgencyList(AgencySearchDto agencySearchDto);
	
	public AgencyDto getAgencySeq(Long AgencyDto);

	int regAgency(AgencyDto agencyDto);
	
	int delAgency(AgencyDto agencyDto);
}
