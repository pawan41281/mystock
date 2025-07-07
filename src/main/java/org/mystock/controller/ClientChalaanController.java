package org.mystock.controller;

import java.util.List;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ClientChalaanService;
import org.mystock.util.DateTimeUtil;
import org.mystock.util.MetadataGenerator;
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
	private final MetadataGenerator metadataGenerator;
	private final DateTimeUtil dateTimeUtil;

	@PostMapping
	@Operation(summary = "Create client chalaan", description = "Chalaan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<ClientChalaanVo>> save(@Valid @RequestBody ClientChalaanVo vo) {
		ClientChalaanVo saved = service.save(vo);
		if (saved != null && saved.getId() != null) {
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			return ResponseEntity.status(500).body(
					ApiResponseVoWrapper.success("Record not saved", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Create multiple client chalaan", description = "Chalaan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<Set<ClientChalaanVo>>> saveAll(@RequestBody Set<ClientChalaanVo> vos) {
		Set<ClientChalaanVo> saved = service.saveAll(vos);
		if (saved != null) {
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			return ResponseEntity.status(500).body(
					ApiResponseVoWrapper.success("Record not saved", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@DeleteMapping("{id}")
	@Operation(summary = "Delete client chalaan")
	public ResponseEntity<ApiResponseVo<ClientChalaanVo>> delete(@PathVariable Long id) {
		ClientChalaanVo deleted = service.deleteById(id);
		if (deleted != null) {
			return ResponseEntity.status(201).body(
					ApiResponseVoWrapper.success("Record deleted", deleted, metadataGenerator.getMetadata(deleted)));
		} else {
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.success("Record not deleted", deleted,
					metadataGenerator.getMetadata(deleted)));
		}
	}

	@GetMapping("{id}")
	@Operation(summary = "Get all chalaans by Id")
	public ResponseEntity<ApiResponseVo<ClientChalaanVo>> findById(@PathVariable Long id) {
		ClientChalaanVo found = service.findById(id);
		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}

	@GetMapping
	@Operation(summary = "Get all chalaans by chalaan number and chalaan date range and chalaan type and client Id", description = "Chalaan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ClientChalaanVo>>> find(
			@RequestParam(value = "chalaannumber", required = false) Integer chalaanNumber,
			@RequestParam(value = "clientid", required = false) Long clientId,
			@RequestParam(value = "fromchalaandate", required = false) Long fromChalaanDate,
			@RequestParam(value = "tochalaandate", required = false) Long toChalaanDate,
			@RequestParam(value = "chalaantype", required = false) String chalaanType) {
		List<ClientChalaanVo> found = service.findAll(chalaanNumber, clientId,
				dateTimeUtil.toLocalDate(fromChalaanDate), dateTimeUtil.toLocalDate(toChalaanDate), chalaanType);
		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}

}
