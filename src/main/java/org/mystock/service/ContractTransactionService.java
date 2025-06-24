package org.mystock.service;

import java.util.List;

import org.mystock.vo.ContractTransactionVo;

public interface ContractTransactionService {

	public List<ContractTransactionVo> list();
	public ContractTransactionVo save(ContractTransactionVo contractTransactionVo);
}
