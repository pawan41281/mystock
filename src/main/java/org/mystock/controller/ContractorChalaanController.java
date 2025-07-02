package org.mystock.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v2/contractorchalaans/")
@AllArgsConstructor
@Tag(name = "Contractor Chalaan Operations", description = "CRUD Operations for contractor chalaan record")
public class ContractorChalaanController {

	private final ContractorChalaanService service;

	@PostMapping
	@Operation(summary = "Create or update contractor chalaan")
	public ResponseEntity<ApiResponseVo<ContractorChalaanVo>> save(@RequestBody ContractorChalaanVo vo) {
		ContractorChalaanVo saved = service.save(vo);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", saved != null && saved.getId() != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				saved != null && saved.getId() != null ? "Record saved" : "Record not saved", saved, metadata));
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Delete contractor chalaan")
	public ResponseEntity<ApiResponseVo<ContractorChalaanVo>> delete(@PathVariable Long id) {
		ContractorChalaanVo saved = service.deleteById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", saved != null && saved.getId() != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				saved != null && saved.getId() != null ? "Record deleted" : "Record not deleted", saved, metadata));
	}

	@GetMapping("{id}")
	@Operation(summary = "Get all chalaans by Id")
	public ResponseEntity<ApiResponseVo<ContractorChalaanVo>> findById(@PathVariable Long id) {
		ContractorChalaanVo saved = service.findById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", saved != null && saved.getId() != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				saved != null && saved.getId() != null ? "Record deleted" : "Record not deleted", saved, metadata));
	}

	@GetMapping
	@Operation(summary = "Get all chalaans")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> getAll() {
		List<ContractorChalaanVo> chalaans = service.findAll();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaannumber/{chalaannumber}")
	@Operation(summary = "Get all chalaans by chalaan number")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> getAllByChalaanNumber(
			@PathVariable Integer chalaannumber) {
		List<ContractorChalaanVo> chalaans = service.findByChalaanNumber(chalaannumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaandate/{chalaandate}")
	@Operation(summary = "Get all chalaans by chalaan date")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> getAllByChalaanDate(
			@PathVariable Long chalaandate) {
		List<ContractorChalaanVo> chalaans = service.findByChalaanDate(new Date(chalaandate));
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaandate/{fromchalaandate}/{tochalaandate}")
	@Operation(summary = "Get all chalaans by chalaan dates")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanDateBetween(
			@PathVariable Long fromchalaandate, @PathVariable Long tochalaandate) {
		List<ContractorChalaanVo> chalaans = service.findByChalaanDateBetween(new Date(fromchalaandate),
				new Date(tochalaandate));
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("contractorid/{id}")
	@Operation(summary = "Get all chalaans by contractor Id")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByContractorId(@PathVariable Long id) {
		List<ContractorChalaanVo> chalaans = service.findByContractor_Id(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaantype/{chalaantype}")
	@Operation(summary = "Get all chalaans by chalaan type", description = "I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanType(
			@PathVariable(name = "chalaantype") String chalaanType) {
		List<ContractorChalaanVo> chalaans = service.findByChalaanType(chalaanType);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaandate/contractorid")
	@Operation(summary = "Get all chalaans by chalaan date and contractor Id")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanDateBetweenAndContractor_Id(
			@RequestParam(value = "fromchalaandate") Long fromChalaanDate,
			@RequestParam(value = "tochalaandate") Long toChalaanDate, @RequestParam Long id) {
		List<ContractorChalaanVo> chalaans = service.findByChalaanDateBetweenAndContractor_Id(new Date(fromChalaanDate),
				new Date(toChalaanDate), id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaantype/contractorid")
	@Operation(summary = "Get all chalaans by chalaan type and contractor Id")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanTypeAndContractor_Id(
			@RequestParam(value = "chalaantype") String chalaanType, @RequestParam Long id) {
		List<ContractorChalaanVo> chalaans = service.findByChalaanTypeAndContractor_Id(chalaanType, id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaandate/chalaantype/contractorid")
	@Operation(summary = "Get all chalaans by chalaan date range and chalaan type and contractor Id")
	public ResponseEntity<ApiResponseVo<List<ContractorChalaanVo>>> findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(
			@RequestParam(value = "fromchalaandate") Long fromChalaanDate,
			@RequestParam(value = "tochalaandate") Long toChalaanDate,
			@RequestParam(value = "chalaantype") String chalaanType, @RequestParam Long id) {
		List<ContractorChalaanVo> chalaans = service.findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(
				new Date(fromChalaanDate), new Date(toChalaanDate), chalaanType, id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}
}
