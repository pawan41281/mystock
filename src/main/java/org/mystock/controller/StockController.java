package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.StockService;
import org.mystock.vo.StockVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v2/stocks/")
@AllArgsConstructor
@Tag(name = "Stock Operations", description = "CRUD Operations for stock record")
public class StockController {

	private final StockService stockService;

	@GetMapping("/{id}")
	@Operation(summary = "Get by ID", description = "Get a stock item by ID")
	public ResponseEntity<ApiResponseVo<StockVo>> getById(@PathVariable Long id) {
		StockVo stockVo = stockService.getById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", stockVo != null ? "1" : "0");
		if (stockVo != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", stockVo, metadata));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", null, metadata));
		}
	}

	@GetMapping
	@Operation(summary = "Get All", description = "Get all stock item balance")
	public ResponseEntity<ApiResponseVo<List<StockVo>>> getAll() {
		List<StockVo> list = stockService.getAll();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", list, metadata));
	}
	
	@PostMapping
	@Operation(summary = "Save Operation", description = "Set opening balance of stock item")
	public ResponseEntity<ApiResponseVo<StockVo>> save(@Valid @RequestBody StockVo stockVo) {
		stockVo = stockService.addOpenningBalance(stockVo.getDesign().getId(), stockVo.getColor().getId(), stockVo.getBalance());
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", stockVo != null ? "1" : "0");
		if (stockVo != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record updated successfully", stockVo, metadata));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not updated", null, metadata));
		}
	}
	
	@PostMapping("bulk")
	@Operation(summary = "Save Operation", description = "Set opening balance of multiple stock items")
	public ResponseEntity<ApiResponseVo<List<StockVo>>> saveAll(@Valid @RequestBody Set<StockVo> stockVos) {
		List<StockVo> vos = stockService.addOpenningBalance(stockVos);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(vos != null ? vos.size() : 0));
		if (vos != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record updated successfully", vos, metadata));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not updated", vos, metadata));
		}
	}

}
