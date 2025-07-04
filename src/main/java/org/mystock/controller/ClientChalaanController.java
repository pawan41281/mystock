package org.mystock.controller;

import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v2/clientchalaans/")
@Tag(name = "Client Chalaan Operations", description = "CRUD Operations for client chalaan record")
@AllArgsConstructor
public class ClientChalaanController {

	private final ClientChalaanService service;

	@PostMapping
	@Operation(summary = "Create or update client chalaan", description = "Chalaan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<ClientChalaanVo>> save(@Valid @RequestBody ClientChalaanVo vo) {
		ClientChalaanVo saved = service.save(vo);
		if (saved != null && saved.getId() != null) {
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, getMetadata(saved)));
		} else {
			return ResponseEntity.status(500)
					.body(ApiResponseVoWrapper.success("Record not saved", saved, getMetadata(saved)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Create or update multiple client chalaan", description = "Chalaan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<Set<ClientChalaanVo>>> saveAll(@RequestBody Set<ClientChalaanVo> vos) {
		Set<ClientChalaanVo> saved = service.saveAll(vos);
		if (saved != null) {
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, getMetadata(saved)));
		} else {
			return ResponseEntity.status(500)
					.body(ApiResponseVoWrapper.success("Record not saved", saved, getMetadata(saved)));
		}
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Delete client chalaan")
	public ResponseEntity<ApiResponseVo<ClientChalaanVo>> delete(@PathVariable Long id) {
		ClientChalaanVo deleted = service.deleteById(id);
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
	public ResponseEntity<ApiResponseVo<ClientChalaanVo>> findById(@PathVariable Long id) {
		ClientChalaanVo found = service.findById(id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping
	@Operation(summary = "Get all chalaans")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> getAll() {
		List<ClientChalaanVo> found = service.findAll();
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaannumber/{chalaannumber}")
	@Operation(summary = "Get all chalaans by chalaan number")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> getAllByChalaanNumber(
			@PathVariable Integer chalaannumber) {
		List<ClientChalaanVo> found = service.findByChalaanNumber(chalaannumber);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaandate/{chalaandate}")
	@Operation(summary = "Get all chalaans by chalaan date")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> getAllByChalaanDate(@PathVariable Long chalaandate) {
		List<ClientChalaanVo> found = service.findByChalaanDate(new Date(chalaandate));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaandate/{fromchalaandate}/{tochalaandate}")
	@Operation(summary = "Get all chalaans by chalaan dates")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanDateBetween(
			@PathVariable Long fromchalaandate, @PathVariable Long tochalaandate) {
		List<ClientChalaanVo> found = service.findByChalaanDateBetween(new Date(fromchalaandate),
				new Date(tochalaandate));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("clientid/{id}")
	@Operation(summary = "Get all chalaans by client Id")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByClientId(@PathVariable Long id) {
		List<ClientChalaanVo> found = service.findByClient_Id(id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaantype/{chalaantype}")
	@Operation(summary = "Get all chalaans by chalaan type", description = "I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanType(
			@PathVariable(name = "chalaantype") String chalaanType) {
		List<ClientChalaanVo> found = service.findByChalaanType(chalaanType);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaandate/clientid")
	@Operation(summary = "Get all chalaans by chalaan date and client Id")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanDateBetweenAndClient_Id(
			@RequestParam(value = "fromchalaandate") Long fromChalaanDate,
			@RequestParam(value = "tochalaandate") Long toChalaanDate, @RequestParam Long id) {
		List<ClientChalaanVo> found = service.findByChalaanDateBetweenAndClient_Id(new Date(fromChalaanDate),
				new Date(toChalaanDate), id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaantype/clientid")
	@Operation(summary = "Get all chalaans by chalaan type and client Id")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanTypeAndClient_Id(
			@RequestParam(value = "chalaantype") String chalaanType, @RequestParam Long id) {
		List<ClientChalaanVo> found = service.findByChalaanTypeAndClient_Id(chalaanType, id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	@GetMapping("chalaandate/chalaantype/clientid")
	@Operation(summary = "Get all chalaans by chalaan date range and chalaan type and client Id")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> findByChalaanDateBetweenAndChalaanTypeAndClient_Id(
			@RequestParam(value = "fromchalaandate") Long fromChalaanDate,
			@RequestParam(value = "tochalaandate") Long toChalaanDate,
			@RequestParam(value = "chalaantype") String chalaanType, @RequestParam Long id) {
		List<ClientChalaanVo> found = service.findByChalaanDateBetweenAndChalaanTypeAndClient_Id(
				new Date(fromChalaanDate), new Date(toChalaanDate), chalaanType, id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	private Map<String, String> getMetadata(ClientChalaanVo vo) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(vo != null && vo.getId() != null ? 1 : 0));
		return metadata;
	}

	private Map<String, String> getMetadata(Collection<ClientChalaanVo> collection) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount",
				String.valueOf(collection != null && !collection.isEmpty() ? collection.size() : 0));
		return metadata;
	}
}
