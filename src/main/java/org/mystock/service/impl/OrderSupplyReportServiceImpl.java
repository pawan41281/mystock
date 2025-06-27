package org.mystock.service.impl;

import org.mystock.service.OrderService;
import org.mystock.service.OrderSupplyReportService;
import org.mystock.service.OrderTransactionService;
import org.mystock.vo.OrderSupplyReportVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderSupplyReportServiceImpl implements OrderSupplyReportService{

	private OrderService orderService;
	private OrderTransactionService orderTransactionService;
	@Override
	public OrderSupplyReportVo getReport(Integer orderNumber) {
		OrderSupplyReportVo orderSupplyReportVo = new OrderSupplyReportVo();
		orderSupplyReportVo.setOrderNumber(orderNumber);
		orderSupplyReportVo.setOrderVoList(orderService.findByOrderNumber(orderNumber));
		orderSupplyReportVo.setOrderTransactionVoList(orderTransactionService.findByOrderNumber(orderNumber));
		return orderSupplyReportVo;
	}
	
	
}
