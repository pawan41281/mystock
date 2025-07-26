package org.mystock.service;

import java.util.List;

import org.mystock.vo.DesignStockReportVo;

public interface DesignStockReportService {

	public List<DesignStockReportVo> getDesignStockReport(String designName, String colorName);

}
