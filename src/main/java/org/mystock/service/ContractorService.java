package org.mystock.service;

import java.util.List;
import java.util.Set;

import org.mystock.vo.ContractorVo;

public interface ContractorService {

	public ContractorVo save(ContractorVo contractorVo);
	
	public Set<ContractorVo> saveAll(Set<ContractorVo> contractorVos);

	public List<ContractorVo> getAll();

	public ContractorVo getById(Long id);

	public ContractorVo updateStatus(Long id, boolean status);

	List<ContractorVo> findByContractorNameIgnoreCase(String contractorName);

	List<ContractorVo> findByCityIgnoreCase(String city);

	List<ContractorVo> findByStateIgnoreCase(String state);

	List<ContractorVo> findByCountryIgnoreCase(String country);

	List<ContractorVo> findByEmailIgnoreCase(String email);

	List<ContractorVo> findByMobile(String mobile);

	List<ContractorVo> findByGstNoIgnoreCase(String gstNo);

	List<ContractorVo> findByActive(boolean active);
	
	public List<ContractorVo> find(String clientName, String city, String state, String mobile, String email, String gstNo, Boolean active);


}
