package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ContractorVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/contractors")
@AllArgsConstructor
@Tag(name = "Contractor Operations", description = "CRUD Operations for contractor record")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorController {

	private final ContractorService contractorService;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(summary = "Create or update contractor")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorVo>> save(@RequestBody ContractorVo vo) {
		log.info("Received request for save");
		if(vo.getId()!=null && vo.getId().equals(0L)) vo.setId(null);
		ContractorVo saved = contractorService.save(vo);
		if (saved != null && saved.getId() != null) {
			log.info("Record saved");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not saved", vo, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("/bulk")
	@Operation(summary = "Create or update multiple contractors")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<Set<ContractorVo>>> saveAll(@RequestBody Set<ContractorVo> vos) {
		log.info("Received request for bulk save :: {}", vos);
		Set<ContractorVo> saved = contractorService.saveAll(vos);
		if (saved != null && !saved.isEmpty()) {
			log.info("Record saved");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not saved", vos, metadataGenerator.getMetadata(saved)));
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get contractor by ID")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorVo>> getById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ContractorVo found = contractorService.getById(id);
		if (found != null) {
			log.info("Record found");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}

	@PatchMapping("/{id}/{status}")
	@Operation(summary = "Update status by ID", description = "Update contractor status by ID")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorVo>> update(@PathVariable Long id, @PathVariable boolean status) {

		log.info("Received request for status update :: {} - {}", id, status);
		ContractorVo saved = contractorService.updateStatus(id, status);
		if (saved != null && saved.getId() != null) {
			log.info("Record updated");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record updated", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.ok(
					ApiResponseVoWrapper.success("Record not updated", saved, metadataGenerator.getMetadata(saved)));
		}

	}

	@GetMapping
	@Operation(summary = "Get contractor by Name and City and State and Email and Mobile and GST Number and Status")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> find(@RequestParam(required = false) String contractorName,
			@RequestParam(required = false) String city, @RequestParam(required = false) String state,
			@RequestParam(required = false) String mobile, @RequestParam(required = false) String email,
			@RequestParam(required = false) String gstNo, @RequestParam(required = false) Boolean active) {

		log.info(
				"Received request for find :: contractorName {}, city {}, state {}, mobile {}, email {}, gstNo {}, active {}",
				contractorName, city, state, mobile, email, gstNo, active);

		List<ContractorVo> found = contractorService.find(contractorName, city, state, mobile, email, gstNo, active);
		log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));

	}

}