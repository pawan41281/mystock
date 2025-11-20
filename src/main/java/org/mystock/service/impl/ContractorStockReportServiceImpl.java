package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import org.mystock.repository.ContractorStockRepository;
import org.mystock.service.ContractorStockReportService;
import org.mystock.vo.ContractorStockReportVo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ContractorStockReportServiceImpl implements ContractorStockReportService {

	private final ContractorStockRepository contractorStockRepository;

//	@Override
//	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName) {
//		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
//		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
//		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
//		return contractorStockRepository.getContractorStockReport(contractorName, designName, colorName);
//	}

	@Override
	public List<ContractorStockReportVo> getStockReport(Long contractorId, Long designId, Long colorId, Long qualityId) {
		contractorId=contractorId.equals(0L)?null:contractorId;
		designId=designId.equals(0L)?null:designId;
		colorId=colorId.equals(0L)?null:colorId;
		qualityId=qualityId.equals(0L)?null:qualityId;

		if(contractorId==null && designId==null && qualityId==null && contractorId==null)
			return Collections.emptyList();

		return contractorStockRepository.getContractorStockReport(contractorId, designId, colorId, qualityId);
	}

	@Override
	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName, String qualityName) {
		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		qualityName = qualityName != null && !qualityName.isEmpty() ? "%" + qualityName + "%" : "%";
		return contractorStockRepository.getContractorStockReport(contractorName, designName, colorName, qualityName);
	}

//	@Override
//	public List<ContractorStockReportVo> getNonZeroStockReport(String contractorName, String designName, String colorName) {
//		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
//		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
//		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
//		return contractorStockRepository.getContractorNonZeroStockReport(contractorName, designName, colorName);
//	}

	@Override
	public List<ContractorStockReportVo> getNonZeroStockReport(String contractorName, String designName, String colorName, String qualityName) {
		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		qualityName = qualityName != null && !qualityName.isEmpty() ? "%" + qualityName + "%" : "%";
		return contractorStockRepository.getContractorNonZeroStockReport(contractorName, designName, colorName, qualityName);
	}

//	@Override
//	public int getStockCount(String contractorName, String designName, String colorName) {
//		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
//		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
//		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
//		return contractorStockRepository.getContractorStockCount(contractorName, designName, colorName);
//	}

	@Override
	public int getStockCount(String contractorName, String designName, String colorName, String qualityName) {
		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		qualityName = qualityName != null && !qualityName.isEmpty() ? "%" + qualityName + "%" : "%";
		return contractorStockRepository.getContractorStockCount(contractorName, designName, colorName, qualityName);
	}

//	@Override
//	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName, Integer pageSize, Integer pageCount) {
//		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
//		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
//		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
//		return contractorStockRepository.getContractorStockReport(contractorName, designName, colorName, pageSize, (pageCount * pageSize));
//	}

	@Override
	public List<ContractorStockReportVo> getStockReport(String contractorName, String designName, String colorName, String qualityName, Integer pageSize, Integer pageCount) {
		contractorName = contractorName != null && !contractorName.isEmpty() ? "%" + contractorName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		qualityName = qualityName != null && !qualityName.isEmpty() ? "%" + qualityName + "%" : "%";
		return contractorStockRepository.getContractorStockReport(contractorName, designName, colorName, qualityName, pageSize, (pageCount * pageSize));
	}

}