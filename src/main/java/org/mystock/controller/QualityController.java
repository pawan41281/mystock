package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.QualityService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.QualityVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Controller responsible for managing Quality records.
 *
 * Provides endpoints for creating, retrieving, updating, and searching quality records.
 *
 * All endpoints are protected with Bearer token authentication and accessible to ADMIN or USER roles.
 */
@RestController
@RequestMapping("/v1/quality")
@AllArgsConstructor
@Tag(
		name = "Quality Operations",
		description = "Endpoints for performing CRUD operations on  quality records."
)
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class QualityController {

	private final QualityService qualityService;
	private final MetadataGenerator metadataGenerator;

	@Operation(
			summary = "Create or update a  quality record",
			description = "Creates a new  quality record or updates an existing one based on the provided ID. Requires ADMIN or USER role."
	)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<QualityVo>> save(@RequestBody QualityVo vo) {
		log.info("Received request for save :: {}", vo);
		QualityVo saved = qualityService.save(vo);
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

	@Operation(
			summary = "Create or update multiple  quality records",
			description = "Bulk create or update multiple  quality records in a single request. Requires ADMIN or USER role."
	)
	@PostMapping("/bulk")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<Set<QualityVo>>> saveAll(@RequestBody Set<QualityVo> vos) {
		log.info("Received request for bulk save");
		Set<QualityVo> saved = qualityService.saveAll(vos);
		if (saved != null && !saved.isEmpty()) {
			log.info("Records saved successfully");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Records saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Records not saved");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Records not saved", vos, metadataGenerator.getMetadata(saved)));
		}
	}

	@Operation(
			summary = "Get  quality record by ID",
			description = "Fetches  quality records using the unique ID. Requires ADMIN or USER role."
	)
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<QualityVo>> getById(
			@Parameter(description = "Unique ID of the  quality record") @PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		QualityVo found = qualityService.getById(id);
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

	@Operation(
			summary = "Get all  quality records",
			description = "Retrieves all  quality records from the system. Requires ADMIN or USER role."
	)
	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<QualityVo>>> getAll(
			@Parameter(description = "Active status (true/false)") @RequestParam(required = false) Boolean active) {
		log.info("Received request for find all");
		List<QualityVo> found = qualityService.getAll(active);
		if (found != null && !found.isEmpty()) {
			log.info("Records found");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Records found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.error("No records found");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("No records found", found, metadataGenerator.getMetadata(found)));
		}
	}

	@Operation(
			summary = "Update  quality status by ID",
			description = "Updates the active/inactive status of a  quality record using its ID. Requires ADMIN or USER role."
	)
	@PatchMapping("/{id}/{status}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<QualityVo>> update(
			@Parameter(description = "Unique ID of the  quality record") @PathVariable Long id,
			@Parameter(description = "New status to set (true = active, false = inactive)") @PathVariable boolean status) {

		log.info("Received request for status update :: {} - {}", id, status);
		QualityVo saved = qualityService.updateStatus(id, status);
		if (saved != null && saved.getId() != null) {
			log.info("Record updated successfully");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record updated", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not updated");
			return ResponseEntity.ok(
					ApiResponseVoWrapper.success("Record not updated", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@Operation(
			summary = "Get  quality records by name",
			description = "Search for  quality matching the given name (case-insensitive, partial match allowed). Requires ADMIN or USER role."
	)
	@GetMapping("/name/{name}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<QualityVo>>> getAll(
			@Parameter(name = "name", description = "quality name (partial or full match, case-insensitive)")
			@PathVariable String name) {
		log.info("Received request for find by name :: {}", name);
		name = name == null ? "%": "%"+ name.trim() + "%";
		List<QualityVo> found = qualityService.findByNameIgnoreCaseLike(name);
		if (found != null && !found.isEmpty()) {
			log.info("Records found");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Records found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.error("No records found for name {}", name);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("No records found", found, metadataGenerator.getMetadata(found)));
		}
	}
}