package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractTransactionService;
import org.mystock.vo.ContractTransactionVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/contracttransactions/")
@AllArgsConstructor
@Tag(name = "Contract Transaction Operations")
public class ContractTransactionController {

	private final ContractTransactionService contractTransactionService;

	@GetMapping
	@Operation(summary = "List Operation", description = "Fetch contract transaction list")
	public ResponseEntity<ApiResponseVo<List<ContractTransactionVo>>> getContracts() {
		List<ContractTransactionVo> list = contractTransactionService.list();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Save contract record")
	public ResponseEntity<ApiResponseVo<ContractTransactionVo>> save(
			@RequestBody ContractTransactionVo contractTransactionVo) {
		contractTransactionVo = contractTransactionService.save(contractTransactionVo);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, contractTransactionVo, null));
	}

	@GetMapping("{id}")
	@Operation(summary = "Find Operation", description = "Find contract transaction by Id")
	public ResponseEntity<ApiResponseVo<ContractTransactionVo>> findById(@PathVariable Long id) {
		ContractTransactionVo contractTransactionVo = contractTransactionService.findById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", contractTransactionVo != null ? "1" : "0");
		return ResponseEntity
				.ok(ApiResponseVoWrapper.success(contractTransactionVo != null ? "Record exists" : "Recoud not exists",
						contractTransactionVo, metadata));
	}

	@GetMapping("/chalaannumber/{chalaanNumber}")
	@Operation(summary = "Find Operation", description = "Find contract transaction by chalaan number")
	public ResponseEntity<ApiResponseVo<List<ContractTransactionVo>>> findByChalaanNumber(
			@PathVariable Integer chalaanNumber) {
		List<ContractTransactionVo> list = contractTransactionService.findByChalaanNumber(chalaanNumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("{fromDate}/{toDate}")
	@Operation(summary = "Find Operation", description = "Find contract transaction by chalaan date")
	public ResponseEntity<ApiResponseVo<List<ContractTransactionVo>>> findByChalaanDateBetween(
			@PathVariable Long fromDate, @PathVariable Long toDate) {
		List<ContractTransactionVo> list = contractTransactionService.findByChalaanDateBetween(fromDate, toDate);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/contractor")
	@Operation(summary = "Find Operation", description = "Find contract transaction by chalaan date and contratcor")
	public ResponseEntity<ApiResponseVo<List<ContractTransactionVo>>> findByChalaanDateBetweenAndContractor(
			@RequestParam Long fromDate, @RequestParam Long toDate, @RequestParam Long contractor) {
		List<ContractTransactionVo> list = contractTransactionService.findByChalaanDateBetweenAndContractor(fromDate,
				toDate, contractor);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/contractor/design")
	@Operation(summary = "Find Operation", description = "Find contract transaction by chalaan date and contratcor and design")
	public ResponseEntity<ApiResponseVo<List<ContractTransactionVo>>> findByChalaanDateBetweenAndContractorAndDesign(
			@RequestParam Long fromDate, @RequestParam Long toDate, @RequestParam Long contractor,
			@RequestParam String design) {
		List<ContractTransactionVo> list = contractTransactionService
				.findByChalaanDateBetweenAndContractorAndDesign(fromDate, toDate, contractor, design);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/contractor/design/color")
	@Operation(summary = "Find Operation", description = "Find contract transaction by chalaan date and contratcor and design and color")
	public ResponseEntity<ApiResponseVo<List<ContractTransactionVo>>> findByChalaanDateBetweenAndContractorAndDesignAndColor(
			@RequestParam Long fromDate, @RequestParam Long toDate, @RequestParam Long contractor,
			@RequestParam String design, @RequestParam String color) {
		List<ContractTransactionVo> list = contractTransactionService
				.findByChalaanDateBetweenAndContractorAndDesignAndColor(fromDate, toDate, contractor, design, color);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}
	
	@DeleteMapping("{id}")
	@Operation(summary = "Delete Operation", description = "Delete contract transaction by Id")
	public ResponseEntity<ApiResponseVo<ContractTransactionVo>> deleteById(@PathVariable Long id) {
		ContractTransactionVo contractTransactionVo = contractTransactionService.delete(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", contractTransactionVo != null ? "1" : "0");
		return ResponseEntity
				.ok(ApiResponseVoWrapper.success(contractTransactionVo != null ? "Record deleted" : "Recoud not exists",
						contractTransactionVo, metadata));
	}

	@DeleteMapping("/chalaannumber/{chalaanNumber}")
	@Operation(summary = "Delete Operation", description = "Delete contract transaction by chalaan number")
	public ResponseEntity<ApiResponseVo<List<ContractTransactionVo>>> deleteByChalaanNumber(
			@PathVariable Integer chalaanNumber) {
		List<ContractTransactionVo> list = contractTransactionService.deleteByChalaanNumber(chalaanNumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record deleted" : "Record not exists", list, metadata));
	}
	
}
