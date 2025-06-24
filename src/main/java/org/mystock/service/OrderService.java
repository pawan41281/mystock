package org.mystock.service;

import java.util.List;

import org.mystock.vo.OrderVo;

public interface OrderService {

	public List<OrderVo> list();
	public OrderVo save(OrderVo orderVo);
}
