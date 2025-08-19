package org.mystock.service.impl;

import java.util.List;

import org.mystock.repository.ContractorStockRepository;
import org.mystock.service.ContractorStockReportService;
import org.mystock.vo.ContractorStockReportVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorStockReportServiceImpl implements ContractorStockReportService {

	private final ContractorStockRepository contractorStockRepository;

	@Override
	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName) {
		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		return contractorStockRepository.getContractorStockReport(contractorName, designName, colorName);
	}

	@Override
	public List<ContractorStockReportVo> getNonZeroStockReport(String contractorName, String designName, String colorName) {
		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		return contractorStockRepository.getContractorNonZeroStockReport(contractorName, designName, colorName);
	}

	@Override
	public int getStockCount(String contractorName, String designName, String colorName) {
		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		return contractorStockRepository.getContractorStockCount(contractorName, designName, colorName);
	}

	@Override
	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName,
			Integer pageSize, Integer pageCount) {
		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		return contractorStockRepository.getContractorStockReport(contractorName, designName, colorName, pageSize,
				(pageCount * pageSize));
	}

}
