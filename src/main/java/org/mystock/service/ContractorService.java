package org.mystock.service;

import org.mystock.vo.ContractorVo;

import java.util.List;
import java.util.Set;

public interface ContractorService {

	ContractorVo save(ContractorVo contractorVo);
	
	Set<ContractorVo> saveAll(Set<ContractorVo> contractorVos);

	List<ContractorVo> getAll();

	ContractorVo getById(Long id);

	ContractorVo updateStatus(Long id, boolean status);

	List<ContractorVo> findByContractorNameIgnoreCase(String contractorName);

	List<ContractorVo> findByCityIgnoreCase(String city);

	List<ContractorVo> findByStateIgnoreCase(String state);

	List<ContractorVo> findByCountryIgnoreCase(String country);

	List<ContractorVo> findByEmailIgnoreCase(String email);

	List<ContractorVo> findByMobile(String mobile);

	List<ContractorVo> findByGstNoIgnoreCase(String gstNo);

	List<ContractorVo> findByActive(boolean active);
	
	List<ContractorVo> find(String clientName, String city, String state, String mobile, String email, String gstNo, Boolean active);


}