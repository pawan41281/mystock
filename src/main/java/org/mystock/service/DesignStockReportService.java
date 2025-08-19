package org.mystock.service;

import java.util.List;

import org.mystock.vo.DesignStockReportVo;

public interface DesignStockReportService {

	public List<DesignStockReportVo> getDesignStockReport(String designName, String colorName);
	
	public List<DesignStockReportVo> getDesignStockNonZeroReport(String designName, String colorName);

	public List<DesignStockReportVo> getDesignStockReport(String designName, String colorName, Integer pageSize, Integer pageCount);
	public int getDesignStockCount(String designName, String colorName);
}
