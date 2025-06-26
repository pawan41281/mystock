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
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/v1/contractors/")
@AllArgsConstructor
@Tag(name = "Contractor Operations")
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

	@GetMapping("id/{id}")
	@Operation(summary = "Find Operation", description = "Find contractors by id")
	public ResponseEntity<ApiResponseVo<ContractorVo>> getClientsById(@PathVariable Long id) {
		ContractorVo contractorVo = contractorService.findById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractorVo!=null?1:0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(contractorVo!=null?"Record found":"Record not found", contractorVo, metadata));
	}

	@GetMapping("email")
	@Operation(summary = "Find Operation", description = "Find contractors by email")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getClientsByEmail(@RequestParam String email) {
		List<ContractorVo> list = contractorService.findByEmailIgnoreCase(email);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("mobile/{mobile}")
	@Operation(summary = "Find Operation", description = "Find contractors by mobile")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getClientsByMobile(@PathVariable String mobile) {
		List<ContractorVo> list = contractorService.findByMobile(mobile);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("gstno/{gstNo}")
	@Operation(summary = "Find Operation", description = "Find contractors by GST Number")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getClientsByGstNo(@PathVariable String gstNo) {
		List<ContractorVo> list = contractorService.findByGstNoIgnoreCase(gstNo);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("status/{status}")
	@Operation(summary = "Find Operation", description = "Find contractors by status")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getClientsByStatus(@PathVariable boolean status) {
		List<ContractorVo> list = contractorService.findByStatus(status);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("email/mobile/gstno/status")
	@Operation(summary = "Find Operation", description = "Find contractors by email or mobile or gstno or status")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> findByEmailOrMobileOrGstNoOrStatus(
			@RequestParam(required = false) String email, 
			@RequestParam(required = false) String mobile, 
			@RequestParam(required = false) String gstNo,
			@RequestParam(required = false) boolean status) {
		List<ContractorVo> list = contractorService.findByEmailOrMobileOrGstNoOrStatus(email, mobile, gstNo, status);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@PatchMapping("{id}/{status}")
	@Operation(summary = "Update Operation", description = "Update status by id")
	public ResponseEntity<ApiResponseVo<ContractorVo>> updateStatus(@PathVariable Long id, @PathVariable boolean status) {
		ContractorVo contractorVo = contractorService.updateStatus(status, id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractorVo!=null?1:0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(contractorVo!=null?"Status updated successfully":"Record not found", contractorVo, metadata));
	}
}
