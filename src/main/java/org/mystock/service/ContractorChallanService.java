package org.mystock.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.mystock.vo.ContractorChallanVo;

public interface ContractorChallanService {

	public ContractorChallanVo save(ContractorChallanVo vo);

	public Set<ContractorChallanVo> saveAll(Set<ContractorChallanVo> vos);

	public ContractorChallanVo findById(Long id);

	public ContractorChallanVo deleteById(Long id);

	public List<ContractorChallanVo> findAll(Integer challanNumber, Long contractorId, LocalDate fromChallanDate,
			LocalDate toChallanDate, String challanType);

	public List<ContractorChallanVo> getRecentChallans(String challanType);

}
