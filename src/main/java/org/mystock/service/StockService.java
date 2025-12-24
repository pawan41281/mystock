package org.mystock.service;

import org.mystock.vo.StockVo;

import java.util.List;
import java.util.Set;

public interface StockService {

	StockVo save(StockVo stockVo);

	StockVo getById(Long id);

	List<StockVo> getAll();
	
	List<StockVo> getAllNonZero();

	List<StockVo> getAll(Long designId);

	StockVo get(Long designId, Long colorId, Long qualityId);

	int increaseBalance(Long designId, Long colorId, Long qualityId, Integer quantity);

	int reduceBalance(Long designId, Long colorId, Long qualityId, Integer quantity);

	StockVo addOpenningBalance(Long designId, Long colorId, Long qualityId, Integer quantity);

	List<StockVo> addOpenningBalance(Set<StockVo> vos);
}