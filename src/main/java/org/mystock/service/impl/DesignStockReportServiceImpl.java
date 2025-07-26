package org.mystock.service.impl;

import java.util.List;

import org.mystock.repository.StockRepository;
import org.mystock.service.DesignStockReportService;
import org.mystock.vo.DesignStockReportVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DesignStockReportServiceImpl implements DesignStockReportService {

	private final StockRepository stockRepository;

	@Override
	public List<DesignStockReportVo> getDesignStockReport(String designName, String colorName) {
		designName = designName != null && !designName.isEmpty() ? "%" + designName + "%" : "%";
		colorName = colorName != null && !colorName.isEmpty() ? "%" + colorName + "%" : "%";
		return stockRepository.getDesignStockReport(designName, colorName);
	}

}
