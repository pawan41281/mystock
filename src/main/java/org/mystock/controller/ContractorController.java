package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorService;
import org.mystock.vo.ContractorVo;
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
@RequestMapping("/v1/contractors/")
@AllArgsConstructor
@Tag(name="Contractor Operations")
public class ContractorController {

	private final ContractorService contractorService;

	@GetMapping
	@Operation(summary = "List Operation", description = "Fetch contractor list")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getContractors() {
		List<ContractorVo> list = contractorService.list();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Save contractor record")
	public ResponseEntity<ApiResponseVo<ContractorVo>> save(@RequestBody ContractorVo contractorVo) {
		contractorVo = contractorService.save(contractorVo);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, contractorVo, null));
	}
}
