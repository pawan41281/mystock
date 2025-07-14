package org.mystock.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorStockService;
import org.mystock.util.MetadataGenerator;
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
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/contractorstocks/")
@AllArgsConstructor
@Tag(name = "Contractor Stock Operations", description = "CRUD Operations for contractor stock record")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorStockController {

	private final ContractorStockService contractorStockService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping("/{id}")
	@Operation(summary = "Get by ID", description = "Get a contractor stock item by ID")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorStockVo>> getById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ContractorStockVo found = contractorStockService.getById(id);
		if (found != null) {
			log.info("Record found :: {}", found);
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found :: {}", found);
			return ResponseEntity.status(201).body(
					ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}

	@GetMapping
	@Operation(summary = "Get All", description = "Get all contractor stock item balance")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorStockVo>>> getAll() {
		List<ContractorStockVo> vos = contractorStockService.getAll();
		return ResponseEntity.status(201)
				.body(ApiResponseVoWrapper.success("Records fetched", vos, metadataGenerator.getMetadata(vos)));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Set opening balance of contractor stock item")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorStockVo>> save(@Valid @RequestBody ContractorStockVo vo) {
		log.info("Received request for save :: {}", vo);
		ContractorStockVo saved = contractorStockService.addOpenningBalance(vo.getContractor().getId(),
				vo.getDesign().getId(), vo.getColor().getId(), vo.getBalance());
		if (saved != null && saved.getId() != null) {
			log.info("Record saved :: {}", saved);
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved :: {}", vo);
			return ResponseEntity.status(500)
					.body(ApiResponseVoWrapper.success("Record not saved", vo, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Save Operation", description = "Set opening balance of multiple contractor stock items")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorStockVo>>> saveAll(
			@Valid @RequestBody Set<ContractorStockVo> vos) {
		log.info("Received request for bulk save :: {}", vos);
		List<ContractorStockVo> saved = contractorStockService.addOpenningBalance(vos);
		if (saved != null && !saved.isEmpty()) {
			log.info("Record saved :: {}", saved);
			return ResponseEntity.status(201).body(ApiResponseVoWrapper.success("Record updated successfully", saved,
					metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved :: {}", vos);
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.success("Record not updated",
					vos.stream().collect(Collectors.toList()), metadataGenerator.getMetadata(saved)));
		}
	}

}
