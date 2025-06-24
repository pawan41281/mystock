package org.mystock.service;

import java.util.List;

import org.mystock.vo.ContractorVo;

public interface ContractorService {

	public List<ContractorVo> list();
	public ContractorVo save(ContractorVo contractorVo);
}
