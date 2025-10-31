package org.mystock.service;

import org.mystock.vo.ContractorChallanVo;
import org.mystock.vo.DashboardCurrentMonthContractorCardVo;
import org.mystock.vo.DashboardPreviousDayContractorCardVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ContractorChallanService {

	ContractorChallanVo save(ContractorChallanVo vo);

	Set<ContractorChallanVo> saveAll(Set<ContractorChallanVo> vos);

	ContractorChallanVo findById(Long id);

	ContractorChallanVo deleteById(Long id);

	List<ContractorChallanVo> findAll(Integer challanNumber, Long contractorId, LocalDate fromChallanDate,
			LocalDate toChallanDate, String challanType);

	List<ContractorChallanVo> getRecentChallans(String challanType);
	
	List<DashboardCurrentMonthContractorCardVo> getCurrentMonthChallanCount();
	
	List<DashboardPreviousDayContractorCardVo> getPreviousDayChallanCount();

}