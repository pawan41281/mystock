package org.mystock.service;

import org.mystock.vo.OrderSupplyReportVo;

public interface OrderSupplyReportService {

	public OrderSupplyReportVo getReport(Integer orderNumber);
}
