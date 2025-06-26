package org.mystock.service;

import java.util.List;

import org.mystock.vo.ContractTransactionVo;

public interface ContractTransactionService {

	public List<ContractTransactionVo> list();

	public ContractTransactionVo save(ContractTransactionVo contractTransactionVo);

	public ContractTransactionVo findById(Long id);

	public List<ContractTransactionVo> findByChalaanNumber(Integer chalaanNumber);

	public List<ContractTransactionVo> findByChalaanDateBetween(Long fromDate, Long toDate);

	public List<ContractTransactionVo> findByChalaanDateBetweenAndContractor(Long fromChalaanDate, Long toChalaanDate,
			Long contractor);

	public List<ContractTransactionVo> findByChalaanDateBetweenAndContractorAndDesign(Long fromChalaanDate,
			Long toChalaanDate, Long contractor, String design);

	public List<ContractTransactionVo> findByChalaanDateBetweenAndContractorAndDesignAndColor(Long fromChalaanDate,
			Long toChalaanDate, Long contractor, String design, String color);
	
	public ContractTransactionVo delete(Long id);
	
	public List<ContractTransactionVo> deleteByChalaanNumber(Integer chalaanNumber);
}
