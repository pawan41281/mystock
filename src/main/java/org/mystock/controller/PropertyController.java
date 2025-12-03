package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.PropertyService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.PropertyVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Controller responsible for managing Property records.
 * Provides endpoints for creating, retrieving, updating, and searching property records.
 * All endpoints are protected with Bearer token authentication and accessible to ADMIN or USER roles.
 */
@RestController
@RequestMapping("/v1/properties")
@AllArgsConstructor
@Tag(
		name = "Property Operations",
		description = "Endpoints for performing CRUD operations on Property records."
)
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class PropertyController {

	private final PropertyService propertyService;
	private final MetadataGenerator metadataGenerator;

	@Operation(
			summary = "Create or update a property record",
			description = "Creates a new property record or updates an existing one based on the provided ID. Requires ADMIN role."
	)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<PropertyVo>> save(@RequestBody PropertyVo vo) {
		log.info("Received request for save :: {}", vo);
		PropertyVo saved = propertyService.save(vo);
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
			summary = "Create or update multiple properties",
			description = "Bulk create or update multiple property records in a single request. Requires ADMIN role."
	)
	@PostMapping("/bulk")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<Set<PropertyVo>>> saveAll(@RequestBody Set<PropertyVo> vos) {
		log.info("Received request for bulk save");
		Set<PropertyVo> saved = propertyService.saveAll(vos);
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
			summary = "Get property by ID",
			description = "Fetches property details using the unique property ID. Requires ADMIN role."
	)
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<PropertyVo>> getById(
			@Parameter(description = "Unique ID of the property record") @PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		PropertyVo found = propertyService.getById(id);
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
			summary = "Get all properties",
			description = "Retrieves all property records from the system. Requires ADMIN role."
	)
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<List<PropertyVo>>> getAll() {
		log.info("Received request for find all");
		List<PropertyVo> found = propertyService.getAll();
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
			summary = "Get properties by name",
			description = "Search for properties matching the given name (case-insensitive, partial match allowed). Requires ADMIN or USER role."
	)
	@GetMapping("/name/{name}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<PropertyVo>>> getAll(
			@Parameter(name = "name", description = "Property name (partial or full match, case-insensitive)")
			@PathVariable String name) {
		log.info("Received request for find by name :: {}", name);
		name = name == null ? "%" : "%" + name.trim() + "%";
		List<PropertyVo> found = propertyService.findByNameIgnoreCaseLike(name);
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