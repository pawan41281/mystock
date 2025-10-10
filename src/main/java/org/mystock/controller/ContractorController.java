package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Contractor Operations", description = "Endpoints to manage Contractor records, including creation, update, and search operations.")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorController {

	private final ContractorService contractorService;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(
			summary = "Create or update contractor",
			description = "Creates a new contractor or updates an existing one based on the provided ID.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "Contractor details to create or update",
					required = true,
					content = @Content(
							schema = @Schema(implementation = ContractorVo.class),
							examples = @ExampleObject(value = """
									{
									  "id": null,
									  "contractorName": "ABC Constructions",
									  "city": "Delhi",
									  "state": "Delhi",
									  "email": "contact@abc.com",
									  "mobile": "9876543210",
									  "gstNo": "07ABCDE1234F1Z5",
									  "active": true
									}
									""")
					)
			),
			responses = {
					@ApiResponse(responseCode = "200", description = "Record saved successfully"),
					@ApiResponse(responseCode = "400", description = "Invalid contractor data", content = @Content),
					@ApiResponse(responseCode = "500", description = "Server error", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorVo>> save(@RequestBody ContractorVo vo) {
		log.info("Received request for save");
		if (vo.getId() != null && vo.getId().equals(0L)) vo.setId(null);
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
	@Operation(
			summary = "Create or update multiple contractors",
			description = "Creates or updates multiple contractor records in bulk.",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					description = "List of contractors to save or update",
					required = true,
					content = @Content(
							schema = @Schema(implementation = ContractorVo.class),
							examples = @ExampleObject(value = """
									[
									  {
									    "contractorName": "XYZ Builders",
									    "city": "Mumbai",
									    "state": "Maharashtra",
									    "email": "xyz@builders.com",
									    "mobile": "9898989898",
									    "gstNo": "27XYZDE1234H1Z2",
									    "active": true
									  },
									  {
									    "contractorName": "LMN Infra",
									    "city": "Pune",
									    "state": "Maharashtra",
									    "email": "info@lmninfra.com",
									    "mobile": "9123456780",
									    "gstNo": "27LMNDE6789H1Z4",
									    "active": false
									  }
									]
									""")
					)
			),
			responses = {
					@ApiResponse(responseCode = "200", description = "Records saved successfully"),
					@ApiResponse(responseCode = "500", description = "Records not saved", content = @Content)
			}
	)
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
	@Operation(
			summary = "Get contractor by ID",
			description = "Fetches contractor details by their unique ID.",
			parameters = {
					@Parameter(name = "id", description = "Contractor ID to fetch", example = "101")
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Record found successfully"),
					@ApiResponse(responseCode = "404", description = "Record not found", content = @Content)
			}
	)
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
	@Operation(
			summary = "Update contractor status by ID",
			description = "Updates the active/inactive status of a contractor by ID.",
			parameters = {
					@Parameter(name = "id", description = "Contractor ID to update", example = "5"),
					@Parameter(name = "status", description = "New status of contractor (true/false)", example = "true")
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Record updated successfully"),
					@ApiResponse(responseCode = "404", description = "Record not found", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorVo>> update(@PathVariable Long id, @PathVariable boolean status) {
		log.info("Received request for status update :: {} - {}", id, status);
		ContractorVo saved = contractorService.updateStatus(id, status);
		if (saved != null && saved.getId() != null) {
			log.info("Record updated");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record updated", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not updated");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not updated", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@GetMapping
	@Operation(
			summary = "Search contractors by filters",
			description = """
					Fetches contractor records based on filters.  
					You can search by any combination of:
					- Contractor Name  
					- City  
					- State  
					- Email  
					- Mobile  
					- GST Number  
					- Active Status
					""",
			parameters = {
					@Parameter(name = "contractorName", description = "Filter by contractor name (partial match allowed)", example = "ABC"),
					@Parameter(name = "city", description = "Filter by city", example = "Delhi"),
					@Parameter(name = "state", description = "Filter by state", example = "Maharashtra"),
					@Parameter(name = "mobile", description = "Filter by mobile number", example = "9876543210"),
					@Parameter(name = "email", description = "Filter by email address", example = "abc@xyz.com"),
					@Parameter(name = "gstNo", description = "Filter by GST number", example = "07ABCDE1234F1Z5"),
					@Parameter(name = "active", description = "Filter by active status", example = "true")
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Records fetched successfully"),
					@ApiResponse(responseCode = "404", description = "No records found", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorVo>>> find(
			@RequestParam(required = false) String contractorName,
			@RequestParam(required = false) String city,
			@RequestParam(required = false) String state,
			@RequestParam(required = false) String mobile,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String gstNo,
			@RequestParam(required = false) Boolean active) {

		log.info("Received request for find :: contractorName {}, city {}, state {}, mobile {}, email {}, gstNo {}, active {}",
				contractorName, city, state, mobile, email, gstNo, active);

		List<ContractorVo> found = contractorService.find(contractorName, city, state, mobile, email, gstNo, active);
		log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}
}