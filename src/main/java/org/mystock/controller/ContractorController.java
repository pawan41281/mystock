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
@RequestMapping("/v2/contractors/")
@AllArgsConstructor
@Tag(name = "Contractor Operations", description = "CRUD Operations for contractor record")
public class ContractorController {

	private final ContractorService contractorService;

	@PostMapping
	@Operation(summary = "Create or update contractor")
	public ResponseEntity<ApiResponseVo<ContractorVo>> save(@RequestBody ContractorVo contractorVo) {
		ContractorVo saved = contractorService.save(contractorVo);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", saved != null && saved.getId() != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				saved != null && saved.getId() != null ? "Record saved" : "Record not saved", saved, metadata));
	}

	@GetMapping
	@Operation(summary = "Get all contractors")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getAll() {
		List<ContractorVo> contractors = contractorService.getAll();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractors.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", contractors, metadata));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get contractor by ID")
	public ResponseEntity<ApiResponseVo<ContractorVo>> getById(@PathVariable Long id) {
		ContractorVo contractor = contractorService.getById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", contractor != null ? "1" : "0");
		if (contractor != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", contractor, metadata));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", null, metadata));
		}
	}

//	@PatchMapping("/{id}/{status}")
//	@Operation(summary = "Update status by ID", description = "Update contractor status by ID")
//	public ResponseEntity<ApiResponseVo<ContractorVo>> update(@PathVariable Long id, @PathVariable boolean status) {
//		ContractorVo updatedVo = contractorService.updateStatus(id, status);
//		Map<String, String> metadata = new HashMap<>();
//		metadata.put("recordcount", (updatedVo != null && updatedVo.getId() != null) ? "1" : "0");
//		return ResponseEntity.ok(ApiResponseVoWrapper.success(
//				(updatedVo != null && updatedVo.getId() != null) ? "Status updated" : "Status not updated", updatedVo,
//				metadata));
//	}

	@GetMapping("name")
	@Operation(summary = "Get contractor by Name")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getByName(@RequestParam String name) {
		List<ContractorVo> contractors = contractorService.findByContractorNameIgnoreCase(name);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractors.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", contractors, metadata));
	}

	@GetMapping("city")
	@Operation(summary = "Get contractor by City")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getByCity(@RequestParam String city) {
		List<ContractorVo> contractors = contractorService.findByCityIgnoreCase(city);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractors.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", contractors, metadata));
	}

	@GetMapping("state")
	@Operation(summary = "Get contractor by State")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getByState(@RequestParam String state) {
		List<ContractorVo> contractors = contractorService.findByStateIgnoreCase(state);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractors.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", contractors, metadata));
	}

	@GetMapping("country")
	@Operation(summary = "Get contractor by Country")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getByCountry(@RequestParam String country) {
		List<ContractorVo> contractors = contractorService.findByCountryIgnoreCase(country);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractors.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", contractors, metadata));
	}

	@GetMapping("email")
	@Operation(summary = "Get contractor by Email")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getByEmail(@RequestParam String email) {
		List<ContractorVo> contractors = contractorService.findByEmailIgnoreCase(email);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractors.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", contractors, metadata));
	}

	@GetMapping("mobile")
	@Operation(summary = "Get contractor by Mobile")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getByMobile(@RequestParam String mobile) {
		List<ContractorVo> contractors = contractorService.findByMobile(mobile);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractors.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", contractors, metadata));
	}

	@GetMapping("gstno")
	@Operation(summary = "Get contractor by GST Number")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getByGst(@RequestParam String gst) {
		List<ContractorVo> contractors = contractorService.findByGstNoIgnoreCase(gst);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractors.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", contractors, metadata));
	}

	@GetMapping("status")
	@Operation(summary = "Get contractor by Status")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> getByActive(@RequestParam boolean active) {
		List<ContractorVo> contractors = contractorService.findByActive(active);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(contractors.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", contractors, metadata));
	}

}
