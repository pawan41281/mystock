package org.mystock.controller;

import java.util.List;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ClientService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ClientVo;
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
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v2/clients/")
@AllArgsConstructor
@Tag(name = "Client Operations", description = "CRUD Operations for client record")
@Slf4j
public class ClientController {

	private final ClientService clientService;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(summary = "Create or update client")
	public ResponseEntity<ApiResponseVo<ClientVo>> save(@RequestBody ClientVo vo) {
		log.info("Received request for save :: {}", vo);
		ClientVo saved = clientService.save(vo);
		if (saved != null && saved.getId() != null) {
			log.info("Record saved :: {}", saved);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved :: {}", vo);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not saved", vo, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Create or update multiple clients")
	public ResponseEntity<ApiResponseVo<Set<ClientVo>>> saveAll(@RequestBody Set<ClientVo> vos) {
		log.info("Received request for bulk save :: {}", vos);
		Set<ClientVo> saved = clientService.saveAll(vos);
		if (saved != null && !saved.isEmpty()) {
			log.info("Record saved :: {}", saved);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved :: {}", vos);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not saved", vos, metadataGenerator.getMetadata(saved)));
		}
	}

	@GetMapping("{id}")
	@Operation(summary = "Get client by ID")
	public ResponseEntity<ApiResponseVo<ClientVo>> getById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ClientVo found = clientService.getById(id);
		if (found != null) {
			log.info("Record found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}

	@PatchMapping("{id}/{status}")
	@Operation(summary = "Update status by ID", description = "Update client status by ID")
	public ResponseEntity<ApiResponseVo<ClientVo>> update(@PathVariable Long id, @PathVariable boolean status) {

		log.info("Received request for status update :: {} - {}", id, status);
		ClientVo saved = clientService.updateStatus(id, status);
		if (saved != null && saved.getId() != null) {
			log.info("Record updated :: {}", saved);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record updated", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved :: {}", saved);
			return ResponseEntity.ok(
					ApiResponseVoWrapper.success("Record not updated", saved, metadataGenerator.getMetadata(saved)));
		}

	}

	@GetMapping
	@Operation(summary = "Get client by Name and City and State and Email and Mobile and GST Number and Status")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> find(
			@RequestParam(required = false) String clientName,
			@RequestParam(required = false) String city, 
			@RequestParam(required = false) String state,
			@RequestParam(required = false) String mobile, 
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String gstNo, 
			@RequestParam(required = false) Boolean active) {

		log.info(
				"Received request for find :: clientName {}, city {}, state {}, mobile {}, email {}, gstNo {}, active {}",
				clientName, city, state, mobile, email, gstNo, active);

		List<ClientVo> found = clientService.find(clientName, city, state, mobile, email, gstNo, active);
		log.info("Record {} :: {}", found != null && !found.isEmpty() ? "found" : "not found", found);

		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));

	}

}
