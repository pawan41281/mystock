package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ColorService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ColorVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Controller responsible for managing Color records.
 *
 * Provides endpoints for creating, retrieving, updating, and searching color records.
 *
 * All endpoints are protected with Bearer token authentication and accessible to ADMIN or USER roles.
 */
@RestController
@RequestMapping("/v1/colors")
@AllArgsConstructor
@Tag(
		name = "Color Operations",
		description = "Endpoints for performing CRUD operations on Color records."
)
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ColorController {

	private final ColorService colorService;
	private final MetadataGenerator metadataGenerator;

	@Operation(
			summary = "Create or update a color record",
			description = "Creates a new color record or updates an existing one based on the provided ID. Requires ADMIN or USER role."
	)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ColorVo>> save(@RequestBody ColorVo vo) {
		log.info("Received request for save :: {}", vo);
		ColorVo saved = colorService.save(vo);
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
			summary = "Create or update multiple colors",
			description = "Bulk create or update multiple color records in a single request. Requires ADMIN or USER role."
	)
	@PostMapping("/bulk")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<Set<ColorVo>>> saveAll(@RequestBody Set<ColorVo> vos) {
		log.info("Received request for bulk save");
		Set<ColorVo> saved = colorService.saveAll(vos);
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
			summary = "Get color by ID",
			description = "Fetches color details using the unique color ID. Requires ADMIN or USER role."
	)
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ColorVo>> getById(
			@Parameter(description = "Unique ID of the color record") @PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ColorVo found = colorService.getById(id);
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
			summary = "Get all colors",
			description = "Retrieves all color records from the system. Requires ADMIN or USER role."
	)
	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ColorVo>>> getAll() {
		log.info("Received request for find all");
		List<ColorVo> found = colorService.getAll();
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
			summary = "Update color status by ID",
			description = "Updates the active/inactive status of a color record using its ID. Requires ADMIN or USER role."
	)
	@PatchMapping("/{id}/{status}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ColorVo>> update(
			@Parameter(description = "Unique ID of the color record") @PathVariable Long id,
			@Parameter(description = "New status to set (true = active, false = inactive)") @PathVariable boolean status) {

		log.info("Received request for status update :: {} - {}", id, status);
		ColorVo saved = colorService.updateStatus(id, status);
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
			summary = "Get colors by name",
			description = "Search for colors matching the given name (case-insensitive, partial match allowed). Requires ADMIN or USER role."
	)
	@GetMapping("/name/{name}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ColorVo>>> getAll(
			@Parameter(name = "name", description = "Color name (partial or full match, case-insensitive)")
			@PathVariable String name) {
		log.info("Received request for find by name :: {}", name);
		name = name == null ? "%" : "%" + name.trim() + "%";
		List<ColorVo> found = colorService.findByNameIgnoreCaseLike(name);
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