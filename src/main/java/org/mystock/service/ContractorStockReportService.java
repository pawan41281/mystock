package org.mystock.service;

import org.mystock.vo.ContractorStockReportVo;

import java.util.List;

public interface ContractorStockReportService {

	//List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName);

	List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName, String qualityName);
	
	//List<ContractorStockReportVo> getNonZeroStockReport(String contractorName, String designName, String colorName);

	List<ContractorStockReportVo> getNonZeroStockReport(String contractorName, String designName, String colorName, String qualityName);

	//int getStockCount(String contractorName, String designName, String colorName);

	int getStockCount(String contractorName, String designName, String colorName, String qualityName);

	//List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName, Integer pageSize, Integer pageCount);

	List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName, String qualityName, Integer pageSize, Integer pageCount);
}