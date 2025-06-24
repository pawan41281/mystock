package org.mystock.service;

import java.util.List;

import org.mystock.vo.OrderTransactionVo;

public interface OrderTransactionService {

	public List<OrderTransactionVo> list();
	public OrderTransactionVo save(OrderTransactionVo orderTransactionVo);
}
