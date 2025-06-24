package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractTransactionService;
import org.mystock.vo.ContractTransactionVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/contracttransactions/")
@AllArgsConstructor
@Tag(name="Contract Transaction Operations")
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
	public ResponseEntity<ApiResponseVo<ContractTransactionVo>> save(@RequestBody ContractTransactionVo contractTransactionVo) {
		contractTransactionVo = contractTransactionService.save(contractTransactionVo);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, contractTransactionVo, null));
	}
}
