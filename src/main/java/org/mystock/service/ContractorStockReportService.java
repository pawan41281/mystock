package org.mystock.service;

import java.util.List;

import org.mystock.vo.ContractorStockReportVo;

public interface ContractorStockReportService {

	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName);

}
