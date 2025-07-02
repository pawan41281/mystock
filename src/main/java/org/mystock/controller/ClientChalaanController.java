package org.mystock.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ClientChalaanService;
import org.mystock.vo.ClientChalaanVo;
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
@RequestMapping("/v2/clientchalaans/")
@Tag(name = "Client Chalaan Operations", description = "CRUD Operations for client chalaan record")
@AllArgsConstructor
public class ClientChalaanController{

	private final ClientChalaanService service;

	@PostMapping
	@Operation(summary = "Create or update client chalaan")
	public ResponseEntity<ApiResponseVo<ClientChalaanVo>> save(@RequestBody ClientChalaanVo vo) {
		ClientChalaanVo saved = service.save(vo);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", saved != null && saved.getId() != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				saved != null && saved.getId() != null ? "Record saved" : "Record not saved", saved, metadata));
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Delete client chalaan")
	public ResponseEntity<ApiResponseVo<ClientChalaanVo>> delete(@PathVariable Long id) {
		ClientChalaanVo saved = service.deleteById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", saved != null && saved.getId() != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				saved != null && saved.getId() != null ? "Record deleted" : "Record not deleted", saved, metadata));
	}

	@GetMapping("{id}")
	@Operation(summary = "Get all chalaans by Id")
	public ResponseEntity<ApiResponseVo<ClientChalaanVo>> findById(@PathVariable Long id) {
		ClientChalaanVo saved = service.findById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", saved != null && saved.getId() != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				saved != null && saved.getId() != null ? "Record deleted" : "Record not deleted", saved, metadata));
	}

	@GetMapping
	@Operation(summary = "Get all chalaans")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> getAll() {
		List<ClientChalaanVo> chalaans = service.findAll();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaannumber/{chalaannumber}")
	@Operation(summary = "Get all chalaans by chalaan number")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> getAllByChalaanNumber(
			@PathVariable Integer chalaannumber) {
		List<ClientChalaanVo> chalaans = service.findByChalaanNumber(chalaannumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaandate/{chalaandate}")
	@Operation(summary = "Get all chalaans by chalaan date")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> getAllByChalaanDate(
			@PathVariable Long chalaandate) {
		List<ClientChalaanVo> chalaans = service.findByChalaanDate(new Date(chalaandate));
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaandate/{fromchalaandate}/{tochalaandate}")
	@Operation(summary = "Get all chalaans by chalaan dates")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanDateBetween(
			@PathVariable Long fromchalaandate, @PathVariable Long tochalaandate) {
		List<ClientChalaanVo> chalaans = service.findByChalaanDateBetween(new Date(fromchalaandate),
				new Date(tochalaandate));
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("clientid/{id}")
	@Operation(summary = "Get all chalaans by client Id")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByClientId(@PathVariable Long id) {
		List<ClientChalaanVo> chalaans = service.findByClient_Id(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaantype/{chalaantype}")
	@Operation(summary = "Get all chalaans by chalaan type", description = "I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanType(
			@PathVariable(name = "chalaantype") String chalaanType) {
		List<ClientChalaanVo> chalaans = service.findByChalaanType(chalaanType);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaandate/clientid")
	@Operation(summary = "Get all chalaans by chalaan date and client Id")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanDateBetweenAndClient_Id(
			@RequestParam(value = "fromchalaandate") Long fromChalaanDate,
			@RequestParam(value = "tochalaandate") Long toChalaanDate, @RequestParam Long id) {
		List<ClientChalaanVo> chalaans = service.findByChalaanDateBetweenAndClient_Id(new Date(fromChalaanDate),
				new Date(toChalaanDate), id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaantype/clientid")
	@Operation(summary = "Get all chalaans by chalaan type and client Id")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanTypeAndClient_Id(
			@RequestParam(value = "chalaantype") String chalaanType, @RequestParam Long id) {
		List<ClientChalaanVo> chalaans = service.findByChalaanTypeAndClient_Id(chalaanType, id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}

	@GetMapping("chalaandate/chalaantype/clientid")
	@Operation(summary = "Get all chalaans by chalaan date range and chalaan type and client Id")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanDateBetweenAndChalaanTypeAndClient_Id(
			@RequestParam(value = "fromchalaandate") Long fromChalaanDate,
			@RequestParam(value = "tochalaandate") Long toChalaanDate,
			@RequestParam(value = "chalaantype") String chalaanType, @RequestParam Long id) {
		List<ClientChalaanVo> chalaans = service.findByChalaanDateBetweenAndChalaanTypeAndClient_Id(
				new Date(fromChalaanDate), new Date(toChalaanDate), chalaanType, id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(chalaans != null && chalaans.size() > 0 ? chalaans.size() : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", chalaans, metadata));
	}
}
