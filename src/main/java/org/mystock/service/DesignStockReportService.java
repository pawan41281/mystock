package org.mystock.service;

import org.mystock.vo.DesignStockReportVo;

import java.util.List;

public interface DesignStockReportService {

	List<DesignStockReportVo> getDesignStockReport(String designName, String colorName);
	
	List<DesignStockReportVo> getDesignStockNonZeroReport(String designName, String colorName);

	List<DesignStockReportVo> getDesignStockReport(String designName, String colorName, Integer pageSize, Integer pageCount);
	int getDesignStockCount(String designName, String colorName);
}