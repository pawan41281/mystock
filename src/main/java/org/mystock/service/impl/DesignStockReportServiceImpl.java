package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import org.mystock.repository.StockRepository;
import org.mystock.service.DesignStockReportService;
import org.mystock.vo.DesignStockReportVo;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class DesignStockReportServiceImpl implements DesignStockReportService {

	private final StockRepository stockRepository;

//	@Override
//	public List<DesignStockReportVo> getDesignStockReport(String designName, String colorName) {
//		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
//		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
//		List<DesignStockReportVo> found = stockRepository.getDesignStockReport(designName, colorName);
//		return found != null ? found : Collections.emptyList();
//	}

	@Override
	public List<DesignStockReportVo> getDesignStockReport(String qualityName, String designName, String colorName) {
		qualityName = qualityName != null && !qualityName.isEmpty() ? "%" + qualityName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		List<DesignStockReportVo> found = stockRepository.getDesignStockReport(qualityName, designName, colorName);
		return found != null ? found : Collections.emptyList();
	}

//	@Override
//	public List<DesignStockReportVo> getDesignStockNonZeroReport(String designName, String colorName) {
//		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
//		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
//		List<DesignStockReportVo> found = stockRepository.getDesignStockNonZeroReport(designName, colorName);
//		return found != null ? found : Collections.emptyList();
//	}

	@Override
	public List<DesignStockReportVo> getDesignStockNonZeroReport(String qualityName, String designName, String colorName) {
		qualityName = qualityName != null && !qualityName.isEmpty() ? "%" + qualityName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		List<DesignStockReportVo> found = stockRepository.getDesignStockNonZeroReport(qualityName, designName, colorName);
		return found != null ? found : Collections.emptyList();
	}

	@Override
	public List<DesignStockReportVo> getDesignStockReport(String qualityName, String designName, String colorName, Integer pageSize,
			Integer pageCount) {
		qualityName = qualityName != null && !qualityName.isEmpty() ? "%" + qualityName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		List<DesignStockReportVo> found = stockRepository.getDesignStockReport(qualityName, designName, colorName, pageSize, (pageCount * pageSize));
		return found != null ? found : Collections.emptyList();
	}

	@Override
	public int getDesignStockCount(String qualityName, String designName, String colorName) {
		qualityName = qualityName != null && !qualityName.isEmpty() ? "%" + qualityName + "%" : "%";
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		return stockRepository.getDesignStockCount(qualityName, designName, colorName);
	}

}