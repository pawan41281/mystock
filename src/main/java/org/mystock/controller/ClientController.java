package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ClientService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ClientVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/clients")
@AllArgsConstructor
@Tag(name = "Client Operations", description = "CRUD Operations for Client records")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ClientController {

	private final ClientService clientService;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(
			summary = "Create or update a client",
			description = """
					Creates or updates a single client record.
					- If `id` is null or 0 → a new record will be created.
					- If `id` exists → the record will be updated.
					"""
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Client created or updated successfully",
					content = @Content(schema = @Schema(implementation = ClientVo.class))),
			@ApiResponse(responseCode = "400", description = "Invalid client data"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ClientVo>> save(
			@Parameter(description = "Client data to create or update") @RequestBody ClientVo vo) {

		log.info("Received request for save :: {}", vo);
		if (vo.getId() != null && vo.getId().equals(0L))
			vo.setId(null);

		ClientVo saved = clientService.save(vo);
		if (saved != null && saved.getId() != null) {
			log.info("Record saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not saved", vo, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("/bulk")
	@Operation(
			summary = "Create or update multiple clients",
			description = "Bulk API to create or update multiple client records in one request."
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Clients created or updated successfully",
					content = @Content(schema = @Schema(implementation = ClientVo.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<Set<ClientVo>>> saveAll(
			@Parameter(description = "Set of clients to create or update") @RequestBody Set<ClientVo> vos) {

		log.info("Received request for bulk save");
		Set<ClientVo> saved = clientService.saveAll(vos);
		if (saved != null && !saved.isEmpty()) {
			log.info("Record saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not saved", vos, metadataGenerator.getMetadata(saved)));
		}
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Get client by ID",
			description = "Fetch client details using client ID"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Client found",
					content = @Content(schema = @Schema(implementation = ClientVo.class))),
			@ApiResponse(responseCode = "404", description = "Client not found")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ClientVo>> getById(
			@Parameter(description = "Client ID", required = true) @PathVariable Long id) {

		log.info("Received request for find :: id - {}", id);
		ClientVo found = clientService.getById(id);
		if (found != null) {
			log.info("Record found");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}

	@PatchMapping("/{id}/{status}")
	@Operation(
			summary = "Update client status",
			description = "Update the active/inactive status of a client by ID"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Status updated successfully"),
			@ApiResponse(responseCode = "404", description = "Client not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ClientVo>> update(
			@Parameter(description = "Client ID to update", required = true) @PathVariable Long id,
			@Parameter(description = "Status flag (true = active, false = inactive)", required = true) @PathVariable boolean status) {

		log.info("Received request for status update :: {} - {}", id, status);
		ClientVo saved = clientService.updateStatus(id, status);
		if (saved != null && saved.getId() != null) {
			log.info("Record updated");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record updated", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not updated");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not updated", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@GetMapping
	@Operation(
			summary = "Search clients by filters",
			description = """
					Search clients using any combination of the following filters:
					- clientName
					- city
					- state
					- mobile
					- email
					- gstNo
					- active (true/false)
					"""
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Clients fetched successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid filter parameters")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> find(
			@Parameter(description = "Client name") @RequestParam(required = false) String clientName,
			@Parameter(description = "City") @RequestParam(required = false) String city,
			@Parameter(description = "State") @RequestParam(required = false) String state,
			@Parameter(description = "Mobile number") @RequestParam(required = false) String mobile,
			@Parameter(description = "Email address") @RequestParam(required = false) String email,
			@Parameter(description = "GST number") @RequestParam(required = false) String gstNo,
			@Parameter(description = "Active status (true/false)") @RequestParam(required = false) Boolean active) {

		log.info(
				"Received request for find :: clientName {}, city {}, state {}, mobile {}, email {}, gstNo {}, active {}",
				clientName, city, state, mobile, email, gstNo, active);

		List<ClientVo> found = clientService.find(clientName, city, state, mobile, email, gstNo, active);
		log.info("Record {} :: {}", found != null && !found.isEmpty() ? "found" : "not found", found);

		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}
}