package org.mystock.service;

import java.util.List;
import java.util.Set;

import org.mystock.vo.StockVo;

public interface StockService {

	public StockVo save(StockVo stockVo);

	public StockVo getById(Long id);

	public List<StockVo> getAll();
	
	public List<StockVo> getAllNonZero();

	public List<StockVo> getAll(Long designId);

	public StockVo get(Long designId, Long colorId);

	public int increaseBalance(Long designId, Long colorId, Integer quantity);

	public int reduceBalance(Long designId, Long colorId, Integer quantity);

	public StockVo addOpenningBalance(Long designId, Long colorId, Integer quantity);

	public List<StockVo> addOpenningBalance(Set<StockVo> vos);
}
