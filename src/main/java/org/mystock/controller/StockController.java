package org.mystock.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.UnableToProcessException;
import org.mystock.service.StockService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.StockBulkVo;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v2/stocks")
@AllArgsConstructor
@Tag(name = "Stock Operations", description = "CRUD Operations for stock record")
@Slf4j
public class StockController {

	private final StockService stockService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping("/{id}")
	@Operation(summary = "Get by ID", description = "Get a stock item by ID")
	public ResponseEntity<ApiResponseVo<StockVo>> getById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		StockVo found = stockService.getById(id);
		if (found != null) {
			log.info("Record found");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
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
	public ResponseEntity<ApiResponseVo<StockVo>> save(@Valid @RequestBody StockVo vo) {
		log.info("Received request for save :: {}", vo);
		if(vo.getId()!=null && vo.getId().equals(0L)) vo.setId(null);
		StockVo saved = stockService.addOpenningBalance(vo.getDesign().getId(), vo.getColor().getId(), vo.getOpeningBalance());
		if (saved != null && saved.getId() != null) {
			log.info("Record saved");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not saved", vo, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Save Operation", description = "Set opening balance of multiple stock items")
	public ResponseEntity<ApiResponseVo<StockBulkVo>> saveAll(@Valid @RequestBody StockBulkVo stockBulkVo) {
		log.info("Received request for bulk save :: {}", stockBulkVo);
		
		if(stockBulkVo.getStockVos().isEmpty())
			throw new UnableToProcessException("Empty payload");
		
		
		List<StockVo> saved = stockService.addOpenningBalance(stockBulkVo.getStockVos());
		
		if (saved != null && !saved.isEmpty()) {
			log.info("Record saved");
			StockBulkVo response = new StockBulkVo();
			response.setStockVos(saved.stream().collect(Collectors.toSet()));
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record updated successfully", response,
					metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not updated",
					stockBulkVo, metadataGenerator.getMetadata(saved)));
		}
	}

}
