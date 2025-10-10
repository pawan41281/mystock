package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.DesignService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.DesignVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/designs")
@AllArgsConstructor
@Tag(name = "Design", description = "CRUD operations for design management")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class DesignController {

	private final DesignService designService;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(
			summary = "Create or update a design",
			description = "Creates a new design or updates an existing design if ID is provided."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Design saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid design data provided"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<DesignVo>> save(
			@Validated @RequestBody @Parameter(description = "Design data to save or update") DesignVo vo) {
		log.info("Received request to save design :: {}", vo);
		if (vo.getId() != null && vo.getId().equals(0L)) vo.setId(null);

		DesignVo saved = designService.save(vo);
		String message = (saved != null && saved.getId() != null) ? "Record saved" : "Record not saved";
		log.info(message);

		return ResponseEntity.ok(ApiResponseVoWrapper.success(message, saved, metadataGenerator.getMetadata(saved)));
	}

	@PostMapping("/bulk")
	@Operation(
			summary = "Bulk save designs",
			description = "Creates or updates multiple design records in a single request."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Bulk design records saved successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid bulk data provided"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<Set<DesignVo>>> saveBulk(
			@RequestBody @Parameter(description = "Set of design records to be saved") Set<DesignVo> vos) {
		log.info("Received request for bulk save :: {}", vos);
		Set<DesignVo> saved = designService.saveAll(vos);
		String message = (saved != null && !saved.isEmpty()) ? "Record saved" : "Record not saved";
		log.info(message);

		return ResponseEntity.ok(ApiResponseVoWrapper.success(message, saved, metadataGenerator.getMetadata(saved)));
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Fetch design by ID",
			description = "Retrieve details of a specific design using its unique ID."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Design record fetched successfully"),
			@ApiResponse(responseCode = "404", description = "Design record not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<DesignVo>> getById(
			@Parameter(description = "Unique ID of the design") @PathVariable Long id) {
		log.info("Received request to fetch design by ID :: {}", id);
		DesignVo found = designService.getById(id);
		String message = (found != null) ? "Record found" : "Record not found";
		log.info(message);

		return ResponseEntity.ok(ApiResponseVoWrapper.success(message, found, metadataGenerator.getMetadata(found)));
	}

	@GetMapping("/name/{name}")
	@Operation(
			summary = "Fetch designs by name",
			description = "Retrieve one or more designs based on a partial or full name match."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Design records fetched successfully"),
			@ApiResponse(responseCode = "404", description = "No matching records found"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getByName(
			@Parameter(description = "Name (or part of the name) to search") @PathVariable(required = false) String name) {
		log.info("Received request to fetch designs by name :: {}", name);
		if (name == null) name = "";
		List<DesignVo> found = designService.getAllByName(name.trim());
		String message = (found != null && !found.isEmpty()) ? "Record found" : "Record not found";
		log.info(message);

		return ResponseEntity.ok(ApiResponseVoWrapper.success(message, found, metadataGenerator.getMetadata(found)));
	}

	@GetMapping
	@Operation(
			summary = "Fetch all designs",
			description = "Retrieve a list of all available designs in the system."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Design records fetched successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getAll() {
		log.info("Received request to fetch all designs");
		List<DesignVo> found = designService.getAll();
		String message = (found != null && !found.isEmpty()) ? "Record found" : "Record not found";
		log.info(message);

		return ResponseEntity.ok(ApiResponseVoWrapper.success(message, found, metadataGenerator.getMetadata(found)));
	}

	@PatchMapping("/{id}/{status}")
	@Operation(
			summary = "Update design status",
			description = "Enable or disable a design by updating its status (true = active, false = inactive)."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Design status updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input provided"),
			@ApiResponse(responseCode = "404", description = "Design not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<DesignVo>> updateStatus(
			@Parameter(description = "Unique ID of the design") @PathVariable Long id,
			@Parameter(description = "New status value (true or false)") @PathVariable boolean status) {
		log.info("Received request to update design status :: ID={}, status={}", id, status);
		DesignVo updated = designService.updateStatus(id, status);
		String message = (updated != null && updated.getId() != null) ? "Record updated" : "Record not updated";
		log.info(message);

		return ResponseEntity.ok(ApiResponseVoWrapper.success(message, updated, metadataGenerator.getMetadata(updated)));
	}
}