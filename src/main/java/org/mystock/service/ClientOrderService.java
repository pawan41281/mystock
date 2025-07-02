package org.mystock.service;

import java.sql.Date;
import java.util.List;

import org.mystock.vo.OrderVo;

public interface OrderService {

	public List<OrderVo> list();

	public OrderVo save(OrderVo orderVo);

	public OrderVo findById(Long id);

	public List<OrderVo> findByOrderNumber(Integer orderNumber);

	public List<OrderVo> findByClient(Long client);

	public List<OrderVo> findByDesign(String design);

	public List<OrderVo> findByOrderDateBetweenAndClient(Long fromDate, Long toDate, Long client);

	public List<OrderVo> findByOrderDateBetweenAndClientAndDesign(Long fromDate, Long toDate, Long client,
			String design);

	public List<OrderVo> findByOrderDateBetweenAndClientAndDesignAndColor(Long fromDate, Long toDate, Long client,
			String design, String color);

	public List<OrderVo> findByOrderDateBetweenAndDesign(Long fromDate, Long toDate, String design);

	public List<OrderVo> findByOrderDateBetween(Date fromDate, Date toDate);

	public List<OrderVo> findByOrderDateBetweenAndDesignAndColor(Long fromDate, Long toDate, String design,
			String color);
	
	public OrderVo updateStatus(boolean status, Long id);
}
