package org.mystock.service;

import java.util.List;

import org.mystock.vo.ContractorVo;

public interface ContractorService {

	public List<ContractorVo> list();

	public ContractorVo save(ContractorVo contractorVo);

	public List<ContractorVo> findByContractorNameIgnoreCase(String name);

	public List<ContractorVo> findByEmailIgnoreCase(String email);

	public List<ContractorVo> findByMobile(String mobile);

	public List<ContractorVo> findByGstNoIgnoreCase(String gstNo);

	public List<ContractorVo> findByStatus(boolean active);

	public List<ContractorVo> findByEmailOrMobileOrGstNoOrStatus(String email, String mobile, String gstNo,
			boolean active);

	public ContractorVo findById(Long id);
	
	public ContractorVo updateStatus(boolean status, Long id);
}
