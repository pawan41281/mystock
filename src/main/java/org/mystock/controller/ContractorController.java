package org.mystock.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
@RequestMapping("/v2/contractors/")
@AllArgsConstructor
@Tag(name = "Contractor Operations", description = "CRUD Operations for contractor record")
public class ContractorController {

	private final ContractorService contractorService;

	@PostMapping
	@Operation(summary = "Create or update contractor")
	public ResponseEntity<ApiResponseVo<ContractorVo>> save(@RequestBody ContractorVo contractorVo) {
		ContractorVo vo = contractorService.save(contractorVo);
		return ResponseEntity.ok(ApiResponseVoWrapper
				.success(vo != null && vo.getId() != null ? "Record saved" : "Record not saved", vo, getMetadata(vo)));
	}

	@PostMapping("bulk")
	@Operation(summary = "Create or update multiple contractors")
	public ResponseEntity<ApiResponseVo<Set<ContractorVo>>> saveAll(@RequestBody Set<ContractorVo> contractorVos) {
		Set<ContractorVo> vos = contractorService.saveAll(contractorVos);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(vos != null ? "Record saved" : "Record not saved",
				contractorVos, getMetadata(vos)));
	}

	@GetMapping("{id}")
	@Operation(summary = "Get contractor by ID")
	public ResponseEntity<ApiResponseVo<ContractorVo>> getById(@PathVariable Long id) {
		ContractorVo vo = contractorService.getById(id);
		if (vo != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", vo, getMetadata(vo)));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", null, getMetadata(vo)));
		}
	}

	@PatchMapping("{id}/{status}")
	@Operation(summary = "Update status by ID", description = "Update contractor status by ID")
	public ResponseEntity<ApiResponseVo<ContractorVo>> update(@PathVariable Long id, @PathVariable boolean status) {
		ContractorVo vo = contractorService.updateStatus(id, status);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				(vo != null && vo.getId() != null) ? "Status updated" : "Status not updated", vo, getMetadata(vo)));
	}

	@GetMapping
	@Operation(summary = "Get contractor by Name and City and State and Email and Mobile and GST Number and Status")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> find(@RequestParam(required = false) String contractorName,
			@RequestParam(required = false) String city, @RequestParam(required = false) String state,
			@RequestParam(required = false) String mobile, @RequestParam(required = false) String email,
			@RequestParam(required = false) String gstNo, @RequestParam(required = false) Boolean active) {
		List<ContractorVo> vos = contractorService.find(contractorName, city, state, mobile, email, gstNo, active);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", vos, getMetadata(vos)));
	}

	private Map<String, String> getMetadata(ContractorVo vo) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(vo != null && vo.getId() != null ? 1 : 0));
		return metadata;
	}

	private Map<String, String> getMetadata(Collection<ContractorVo> collection) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount",
				String.valueOf(collection != null && !collection.isEmpty() ? collection.size() : 0));
		return metadata;
	}

}
