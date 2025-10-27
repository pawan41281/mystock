package org.mystock.service;

import org.mystock.vo.StockVo;

import java.util.List;
import java.util.Set;

public interface StockService {

	public StockVo save(StockVo stockVo);

	public StockVo getById(Long id);

	public List<StockVo> getAll();
	
	public List<StockVo> getAllNonZero();

	public List<StockVo> getAll(Long designId);

	public StockVo get(Long designId, Long colorId, Long qualityId);

	public int increaseBalance(Long designId, Long colorId, Long qualityId, Integer quantity);

	public int reduceBalance(Long designId, Long colorId, Long qualityId, Integer quantity);

	public StockVo addOpenningBalance(Long designId, Long colorId, Long qualityId, Integer quantity);

	public List<StockVo> addOpenningBalance(Set<StockVo> vos);
}