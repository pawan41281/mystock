package org.mystock.service;

import org.mystock.vo.ContractorStockVo;

import java.util.List;
import java.util.Set;

public interface ContractorStockService {

	ContractorStockVo save(ContractorStockVo stockVo);

	ContractorStockVo getById(Long id);

	List<ContractorStockVo> getAll();

	List<ContractorStockVo> getAll(Long contractorId);

	List<ContractorStockVo> getAll(Long contractorId, Long designId);

	ContractorStockVo get(Long contractorId, Long designId, Long colorId);

	List<ContractorStockVo> getAllDesignAndColor(Long designId, Long colorId);

	int increaseBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

	int reduceBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

	ContractorStockVo addOpenningBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

	List<ContractorStockVo> addOpenningBalance(Set<ContractorStockVo> vos);
}