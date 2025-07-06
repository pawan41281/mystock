package org.mystock.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorChalaanService;
import org.mystock.vo.ContractorChalaanVo;
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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v2/contractorchalaans/")
@AllArgsConstructor
@Tag(name = "Contractor Chalaan Operations", description = "CRUD Operations for contractor chalaan record")
public class ContractorChalaanController {

	private final ContractorChalaanService service;

	@PostMapping
	@Operation(summary = "Create or update contractor chalaan", description = "Chalaan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<ContractorChalaanVo>> save(@Valid @RequestBody ContractorChalaanVo vo) {
		ContractorChalaanVo saved = service.save(vo);
		if (saved != null && saved.getId() != null) {
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, getMetadata(saved)));
		} else {
			return ResponseEntity.status(500)
					.body(ApiResponseVoWrapper.success("Record not saved", saved, getMetadata(saved)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Create or update multiple contractor chalaan", description = "Chalaan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<Set<ContractorChalaanVo>>> saveAll(@RequestBody Set<ContractorChalaanVo> vos) {
		Set<ContractorChalaanVo> saved = service.saveAll(vos);
		if (saved != null) {
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, getMetadata(saved)));
		} else {
			return ResponseEntity.status(500)
					.body(ApiResponseVoWrapper.success("Record not saved", saved, getMetadata(saved)));
		}
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Delete contractor chalaan")
	public ResponseEntity<ApiResponseVo<ContractorChalaanVo>> delete(@PathVariable Long id) {
		ContractorChalaanVo deleted = service.deleteById(id);
		if (deleted != null) {
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record deleted", deleted, getMetadata(deleted)));
		} else {
			return ResponseEntity.status(500)
					.body(ApiResponseVoWrapper.success("Record not deleted", deleted, getMetadata(deleted)));
		}
	}

	@GetMapping("{id}")
	@Operation(summary = "Get all chalaans by Id")
	public ResponseEntity<ApiResponseVo<ContractorChalaanVo>> findById(@PathVariable Long id) {
		ContractorChalaanVo found = service.findById(id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping
	@Operation(summary = "Get all chalaans by chalaan number and chalaan date range and chalaan type and contractor Id", description = "Chalaan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> find(
			@RequestParam(value = "chalaannumber", required = false) Integer chalaanNumber,
			@RequestParam(value = "contractorid", required = false) Long contractorId,
			@RequestParam(value = "fromchalaandate", required = false) Long fromChalaanDate,
			@RequestParam(value = "tochalaandate", required = false) Long toChalaanDate,
			@RequestParam(value = "chalaantype", required = false) String chalaanType) {
		List<ContractorChalaanVo> found = service.findAll(chalaanNumber, contractorId, toLocalDate(fromChalaanDate),
				toLocalDate(toChalaanDate), chalaanType);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	private LocalDate toLocalDate(Long epochMillis) {
		if (epochMillis == null)
			return null;
		return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDate();
	}

	private Map<String, String> getMetadata(ContractorChalaanVo vo) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(vo != null && vo.getId() != null ? 1 : 0));
		return metadata;
	}

	private Map<String, String> getMetadata(Collection<ContractorChalaanVo> collection) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount",
				String.valueOf(collection != null && !collection.isEmpty() ? collection.size() : 0));
		return metadata;
	}
}
