package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.UnableToProcessException;
import org.mystock.service.StockService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.StockBulkVo;
import org.mystock.vo.StockVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/stocks")
@AllArgsConstructor
@Tag(name = "Stock Operations", description = "CRUD Operations for stock records")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class StockController {

	private final StockService stockService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping("/{id}")
	@Operation(summary = "Get stock by ID", description = "Retrieve a stock item by its ID")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Stock found successfully"),
			@ApiResponse(responseCode = "404", description = "Stock not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<StockVo>> getById(@PathVariable Long id) {
		log.info("Received request for stock by ID: {}", id);
		StockVo found = stockService.getById(id);

		if (found != null) {
			log.info("Record found");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}

	@GetMapping
	@Operation(summary = "Get all stocks", description = "Retrieve all stock items")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Stocks fetched successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<StockVo>>> getAll() {
		log.info("Received request to fetch all stocks");
		List<StockVo> vos = stockService.getAll();
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", vos, metadataGenerator.getMetadata(vos)));
	}

	@PostMapping
	@Operation(summary = "Save stock", description = "Set opening balance of a stock item")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Stock saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid request payload"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<StockVo>> save(@Valid @RequestBody StockVo vo) {
		log.info("Received request to save stock: {}", vo);
		if (vo.getId() != null && vo.getId().equals(0L)) vo.setId(null);
		StockVo saved = stockService.addOpenningBalance(vo.getDesign().getId(), vo.getColor().getId(), vo.getOpeningBalance());

		if (saved != null && saved.getId() != null) {
			log.info("Record saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not saved", vo, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("/bulk")
	@Operation(summary = "Bulk save stocks", description = "Set opening balance of multiple stock items")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Stocks saved successfully"),
			@ApiResponse(responseCode = "400", description = "Empty or invalid payload"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<StockBulkVo>> saveAll(@Valid @RequestBody StockBulkVo stockBulkVo) {
		log.info("Received request for bulk save: {}", stockBulkVo);

		if (stockBulkVo.getStockVos().isEmpty())
			throw new UnableToProcessException("Empty payload");

		List<StockVo> saved = stockService.addOpenningBalance(stockBulkVo.getStockVos());

		if (saved != null && !saved.isEmpty()) {
			log.info("Records saved successfully");
			StockBulkVo response = new StockBulkVo();
			response.setStockVos(saved.stream().collect(Collectors.toSet()));
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Records updated successfully", response, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Records not saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Records not updated", stockBulkVo, metadataGenerator.getMetadata(saved)));
		}
	}
}