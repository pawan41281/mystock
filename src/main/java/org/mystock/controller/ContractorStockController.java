package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorStockService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ContractorStockBulkVo;
import org.mystock.vo.ContractorStockVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/contractorstocks")
@AllArgsConstructor
@Slf4j
@Tag(
		name = "Contractor Stock Operations",
		description = "API endpoints for managing contractor stock items including create, read, and bulk operations."
)
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorStockController {

	private final ContractorStockService contractorStockService;
	private final MetadataGenerator metadataGenerator;

	// ----------------------------- GET BY ID -----------------------------
	@GetMapping("/{id}")
	@Operation(
			summary = "Get Contractor Stock by ID",
			description = "Retrieve details of a specific contractor stock item by its unique ID.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Contractor stock item retrieved successfully",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					),
					@ApiResponse(
							responseCode = "404",
							description = "Contractor stock item not found",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorStockVo>> getById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ContractorStockVo found = contractorStockService.getById(id);
		if (found != null) {
			log.info("Record found");
			return ResponseEntity.ok(ApiResponseVoWrapper.success(
					"Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found");
			return ResponseEntity.ok(ApiResponseVoWrapper.success(
					"Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}

	// ----------------------------- GET ALL -----------------------------
	@GetMapping
	@Operation(
			summary = "Get All Contractor Stocks",
			description = "Retrieve a list of all contractor stock item balances.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "List of contractor stock items retrieved successfully",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorStockVo>>> getAll() {
		List<ContractorStockVo> vos = contractorStockService.getAll();
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				"Records fetched", vos, metadataGenerator.getMetadata(vos)));
	}

	// ----------------------------- SAVE SINGLE -----------------------------
	@PostMapping
	@Operation(
			summary = "Create Contractor Stock Opening Balance",
			description = "Create or update an opening balance record for a contractor’s stock item.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Contractor stock opening balance saved successfully",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid request payload",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorStockVo>> save(@Valid @RequestBody ContractorStockVo vo) {
		log.info("Received request for save :: {}", vo);
		if (vo.getId() != null && vo.getId().equals(0L)) vo.setId(null);

		ContractorStockVo saved = contractorStockService.addOpenningBalance(
				vo.getContractor().getId(),
				vo.getDesign().getId(),
				vo.getColor().getId(),
				vo.getOpeningBalance()
		);

		if (saved != null && saved.getId() != null) {
			log.info("Record saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success(
					"Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success(
					"Record not saved", vo, metadataGenerator.getMetadata(saved)));
		}
	}

	// ----------------------------- SAVE BULK -----------------------------
	@PostMapping("/bulk")
	@Operation(
			summary = "Bulk Save Contractor Stock Opening Balances",
			description = "Create or update multiple contractor stock opening balances in one request.",
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Contractor stock records processed successfully",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid bulk payload",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorStockBulkVo>> saveAll(
			@Valid @RequestBody ContractorStockBulkVo bulkVo) {

		log.info("Received request for bulk save :: {}", bulkVo);
		List<ContractorStockVo> saved = contractorStockService.addOpenningBalance(bulkVo.getContractorStockVos());

		if (saved != null && !saved.isEmpty()) {
			bulkVo.setContractorStockVos(saved.stream().collect(Collectors.toSet()));
			log.info("Bulk records saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success(
					"Record updated successfully", bulkVo, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Bulk record save failed");
			return ResponseEntity.ok(ApiResponseVoWrapper.success(
					"Record not updated", bulkVo, metadataGenerator.getMetadata(saved)));
		}
	}
}