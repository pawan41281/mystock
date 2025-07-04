package org.mystock.controller;

import java.sql.Date;
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
	@Operation(summary = "Get all chalaans")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> getAll() {
		List<ContractorChalaanVo> found = service.findAll();
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaannumber/{chalaannumber}")
	@Operation(summary = "Get all chalaans by chalaan number")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> getAllByChalaanNumber(
			@PathVariable Integer chalaannumber) {
		List<ContractorChalaanVo> found = service.findByChalaanNumber(chalaannumber);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaandate/{chalaandate}")
	@Operation(summary = "Get all chalaans by chalaan date")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> getAllByChalaanDate(
			@PathVariable Long chalaandate) {
		List<ContractorChalaanVo> found = service.findByChalaanDate(new Date(chalaandate));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaandate/{fromchalaandate}/{tochalaandate}")
	@Operation(summary = "Get all chalaans by chalaan dates")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanDateBetween(
			@PathVariable Long fromchalaandate, @PathVariable Long tochalaandate) {
		List<ContractorChalaanVo> found = service.findByChalaanDateBetween(new Date(fromchalaandate),
				new Date(tochalaandate));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("contractorid/{id}")
	@Operation(summary = "Get all chalaans by contractor Id")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByContractorId(@PathVariable Long id) {
		List<ContractorChalaanVo> found = service.findByContractor_Id(id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaantype/{chalaantype}")
	@Operation(summary = "Get all chalaans by chalaan type", description = "I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanType(
			@PathVariable(name = "chalaantype") String chalaanType) {
		List<ContractorChalaanVo> found = service.findByChalaanType(chalaanType);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaandate/contractorid")
	@Operation(summary = "Get all chalaans by chalaan date and contractor Id")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanDateBetweenAndContractor_Id(
			@RequestParam(value = "fromchalaandate") Long fromChalaanDate,
			@RequestParam(value = "tochalaandate") Long toChalaanDate, @RequestParam Long id) {
		List<ContractorChalaanVo> found = service.findByChalaanDateBetweenAndContractor_Id(new Date(fromChalaanDate),
				new Date(toChalaanDate), id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaantype/contractorid")
	@Operation(summary = "Get all chalaans by chalaan type and contractor Id")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanTypeAndContractor_Id(
			@RequestParam(value = "chalaantype") String chalaanType, @RequestParam Long id) {
		List<ContractorChalaanVo> found = service.findByChalaanTypeAndContractor_Id(chalaanType, id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaandate/chalaantype/contractorid")
	@Operation(summary = "Get all chalaans by chalaan date range and chalaan type and contractor Id")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(
			@RequestParam(value = "fromchalaandate") Long fromChalaanDate,
			@RequestParam(value = "tochalaandate") Long toChalaanDate,
			@RequestParam(value = "chalaantype") String chalaanType, @RequestParam Long id) {
		List<ContractorChalaanVo> found = service.findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(
				new Date(fromChalaanDate), new Date(toChalaanDate), chalaanType, id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
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
