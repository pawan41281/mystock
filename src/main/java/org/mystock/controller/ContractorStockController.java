package org.mystock.controller;

import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorStockService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ContractorStockBulkVo;
import org.mystock.vo.ContractorStockVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/v1/contractorstocks")
@AllArgsConstructor
@Tag(name = "Contractor Stock Operations", description = "CRUD Operations for contractor stock record")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class ContractorStockController {

	private final ContractorStockService contractorStockService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping("/{id}")
	@Operation(summary = "Get by ID", description = "Get a contractor stock item by ID")
	public ResponseEntity<ApiResponseVo<ContractorStockVo>> getById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ContractorStockVo found = contractorStockService.getById(id);
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
	@Operation(summary = "Get All", description = "Get all contractor stock item balance")
	public ResponseEntity<ApiResponseVo<List<ContractorStockVo>>> getAll() {
		List<ContractorStockVo> vos = contractorStockService.getAll();
		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Records fetched", vos, metadataGenerator.getMetadata(vos)));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Set opening balance of contractor stock item")
	public ResponseEntity<ApiResponseVo<ContractorStockVo>> save(@Valid @RequestBody ContractorStockVo vo) {
		log.info("Received request for save :: {}", vo);
		if(vo.getId()!=null && vo.getId().equals(0L)) vo.setId(null);
		ContractorStockVo saved = contractorStockService.addOpenningBalance(vo.getContractor().getId(),
				vo.getDesign().getId(), vo.getColor().getId(), vo.getOpeningBalance());
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

	@PostMapping("/bulk")
	@Operation(summary = "Save Operation", description = "Set opening balance of multiple contractor stock items")
	public ResponseEntity<ApiResponseVo<ContractorStockBulkVo>> saveAll(
			@Valid @RequestBody ContractorStockBulkVo bulkVo) {
		log.info("Received request for bulk save :: {}", bulkVo);
		List<ContractorStockVo> saved = contractorStockService.addOpenningBalance(bulkVo.getContractorStockVos());
		if (saved != null && !saved.isEmpty()) {
			bulkVo.setContractorStockVos(saved.stream().collect(Collectors.toSet()));
			log.info("Record saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record updated successfully", bulkVo,
					metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not updated",
					bulkVo, metadataGenerator.getMetadata(saved)));
		}
	}

}
