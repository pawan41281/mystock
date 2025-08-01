package org.mystock.service;

import java.util.List;

import org.mystock.vo.ContractorStockReportVo;

public interface ContractorStockReportService {

	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName);

	public int getStockCount(String contractorName, String designName, String colorName);

	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName,
			Integer pageSize, Integer pageCount);
}
