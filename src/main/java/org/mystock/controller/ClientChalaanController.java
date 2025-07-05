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
@AllArgsConstructor
@Tag(name = "Client Chalaan Operations", description = "CRUD Operations for client chalaan record")
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
	@Operation(summary = "Get all chalaans by chalaan number and chalaan date range and chalaan type and client Id", description = "Chalaan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> find(
			@RequestParam(value = "chalaannumber", required = false) Integer chalaanNumber,
			@RequestParam(value = "clientid", required = false) Long clientId,
			@RequestParam(value = "fromchalaandate", required = false) Long fromChalaanDate,
			@RequestParam(value = "tochalaandate", required = false) Long toChalaanDate,
			@RequestParam(value = "chalaantype", required = false) String chalaanType) {
		List<ClientChalaanVo> found = service.findAll(chalaanNumber, clientId, ToLocalDate(fromChalaanDate),
				ToLocalDate(toChalaanDate), chalaanType);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, getMetadata(found)));
	}

	private LocalDate ToLocalDate(Long epochMillis) {
		if (epochMillis == null)
			return null;
		return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDate();
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
