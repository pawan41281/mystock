package org.mystock.service;

import java.util.List;
import java.util.Set;

import org.mystock.vo.ContractorStockVo;

public interface ContractorStockService {

	public ContractorStockVo save(ContractorStockVo stockVo);

	public ContractorStockVo getById(Long id);

	public List<ContractorStockVo> getAll();

	public List<ContractorStockVo> getAll(Long contractorId);

	public List<ContractorStockVo> getAll(Long contractorId, Long designId);

	public ContractorStockVo get(Long contractorId, Long designId, Long colorId);

	public List<ContractorStockVo> getAllDesignAndColor(Long designId, Long colorId);

	public int increaseBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

	public int reduceBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

	public ContractorStockVo addOpenningBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

	public List<ContractorStockVo> addOpenningBalance(Set<ContractorStockVo> vos);
}
