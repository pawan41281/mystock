package org.mystock.service;

import java.util.List;

import org.mystock.vo.OrderTransactionVo;

public interface OrderTransactionService {

	public List<OrderTransactionVo> list();

	public OrderTransactionVo save(OrderTransactionVo orderTransactionVo);

	public OrderTransactionVo findById(Long id);

	public List<OrderTransactionVo> findByChalaanNumber(Integer chalaanNumber);

	public List<OrderTransactionVo> findByOrderNumber(Integer orderNumber);

	public List<OrderTransactionVo> findByChalaanDateBetween(Long fromDate, Long toDate);

	public List<OrderTransactionVo> findByChalaanDateBetweenAndOrderNumber(Long fromDate, Long toDate,
			Integer orderNumber);

	public List<OrderTransactionVo> findByChalaanDateBetweenAndClient(Long fromChalaanDate, Long toChalaanDate,
			Long client);

	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndDesign(Long fromChalaanDate, Long toChalaanDate,
			Long client, String design);

	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndDesignAndColor(Long fromChalaanDate,
			Long toChalaanDate, Long client, String design, String color);

	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndOrderNumber(Long fromChalaanDate,
			Long toChalaanDate, Long client, Integer orderNumber);

	public List<OrderTransactionVo> findByChalaanDateBetweenAndOrderNumberAndDesign(Long fromChalaanDate,
			Long toChalaanDate, Integer orderNumber, String design);

	public List<OrderTransactionVo> findByChalaanDateBetweenAndOrderNumberAndDesignAndColor(Long fromChalaanDate,
			Long toChalaanDate, Integer orderNumber, String design, String color);

	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndOrderNumberAndDesign(Long fromChalaanDate,
			Long toChalaanDate, Long client, Integer orderNumber, String design);

	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndOrderNumberAndDesignAndColor(
			Long fromChalaanDate, Long toChalaanDate, Long client, Integer orderNumber, String design, String color);

	public OrderTransactionVo delete(Long id);
	
	public List<OrderTransactionVo> delete(List<Long> ids);

	public List<OrderTransactionVo> deleteByOrderNumber(Integer orderNumber);

	public List<OrderTransactionVo> deleteByChalaanNumber(Integer chalaanNumber);

	public List<OrderTransactionVo> deleteByDesign(String design);

}
