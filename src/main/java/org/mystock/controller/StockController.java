package org.mystock.controller;

import java.util.List;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.StockService;
import org.mystock.util.MetadataGenerator;
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
	private final MetadataGenerator metadataGenerator;

	@GetMapping("/{id}")
	@Operation(summary = "Get by ID", description = "Get a stock item by ID")
	public ResponseEntity<ApiResponseVo<StockVo>> getById(@PathVariable Long id) {
		StockVo vo = stockService.getById(id);
		if (vo != null) {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record found", vo, metadataGenerator.getMetadata(vo)));
		} else {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not found", vo, metadataGenerator.getMetadata(vo)));
		}
	}

	@GetMapping
	@Operation(summary = "Get All", description = "Get all stock item balance")
	public ResponseEntity<ApiResponseVo<List<StockVo>>> getAll() {
		List<StockVo> vos = stockService.getAll();
		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Records fetched", vos, metadataGenerator.getMetadata(vos)));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Set opening balance of stock item")
	public ResponseEntity<ApiResponseVo<StockVo>> save(@Valid @RequestBody StockVo stockVo) {
		StockVo vo = stockService.addOpenningBalance(stockVo.getDesign().getId(), stockVo.getColor().getId(),
				stockVo.getBalance());
		if (vo != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record updated successfully", stockVo,
					metadataGenerator.getMetadata(vo)));
		} else {
			return ResponseEntity.ok(
					ApiResponseVoWrapper.success("Record not updated", null, metadataGenerator.getMetadata(stockVo)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Save Operation", description = "Set opening balance of multiple stock items")
	public ResponseEntity<ApiResponseVo<List<StockVo>>> saveAll(@Valid @RequestBody Set<StockVo> stockVos) {
		List<StockVo> vos = stockService.addOpenningBalance(stockVos);
		if (vos != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record updated successfully", vos,
					metadataGenerator.getMetadata(vos)));
		} else {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not updated", vos, metadataGenerator.getMetadata(vos)));
		}
	}

}
