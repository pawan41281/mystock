package org.mystock.service.impl;

import java.util.List;

import org.mystock.repository.StockRepository;
import org.mystock.service.ContractorStockReportService;
import org.mystock.vo.ContractorStockReportVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorStockReportServiceImpl implements ContractorStockReportService {

	private final StockRepository stockRepository;

	@Override
	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName) {
		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		return stockRepository.getContractorStockReport(contractorName, designName, colorName);
	}

}
