package org.mystock.controller;

import java.util.List;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorStockService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ContractorStockVo;
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
@RequestMapping("/v2/contractorstocks/")
@AllArgsConstructor
@Tag(name = "Contractor Stock Operations", description = "CRUD Operations for contractor stock record")
public class ContractorStockController {

	private final ContractorStockService contractorStockService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping("/{id}")
	@Operation(summary = "Get by ID", description = "Get a contractor stock item by ID")
	public ResponseEntity<ApiResponseVo<ContractorStockVo>> getById(@PathVariable Long id) {
		ContractorStockVo vo = contractorStockService.getById(id);
		if (vo != null) {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record found", vo, metadataGenerator.getMetadata(vo)));
		} else {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not found", vo, metadataGenerator.getMetadata(vo)));
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
	public ResponseEntity<ApiResponseVo<ContractorStockVo>> save(
			@Valid @RequestBody ContractorStockVo contractorStockVo) {
		ContractorStockVo vo = contractorStockService.addOpenningBalance(contractorStockVo.getContractor().getId(),
				contractorStockVo.getDesign().getId(), contractorStockVo.getColor().getId(),
				contractorStockVo.getBalance());
		if (vo != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record updated successfully", contractorStockVo,
					metadataGenerator.getMetadata(vo)));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not updated", contractorStockVo,
					metadataGenerator.getMetadata(vo)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Save Operation", description = "Set opening balance of multiple contractor stock items")
	public ResponseEntity<ApiResponseVo<List<ContractorStockVo>>> saveAll(
			@Valid @RequestBody Set<ContractorStockVo> contractorStockVos) {
		List<ContractorStockVo> vos = contractorStockService.addOpenningBalance(contractorStockVos);
		if (vos != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record updated successfully", vos,
					metadataGenerator.getMetadata(vos)));
		} else {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not updated", vos, metadataGenerator.getMetadata(vos)));
		}
	}

}
