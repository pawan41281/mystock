package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.BusinessException;
import org.mystock.service.ClientChallanService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ClientChallanVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/clientchallans")
@AllArgsConstructor
@Tag(name = "Client Challan Operations", description = "CRUD Operations for Client Challan records")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ClientChallanController {

	private final ClientChallanService service;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(
			summary = "Create a client challan",
			description = "Creates a single client challan. Challan Type: `I` (Issue) or `R` (Received)"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Challan created successfully",
					content = @Content(schema = @Schema(implementation = ClientChallanVo.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ClientChallanVo>> save(
			@Valid @RequestBody ClientChallanVo vo) {

		log.info("Received request for save :: {}", vo);
		if (vo.getId() != null && vo.getId().equals(0L))
			vo.setId(null);

		ClientChallanVo saved = service.save(vo);
		if (saved != null && saved.getId() != null) {
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		}
		return ResponseEntity.status(500)
				.body(ApiResponseVoWrapper.success("Record not saved", saved, metadataGenerator.getMetadata(saved)));
	}

	@PostMapping("/bulk")
	@Operation(
			summary = "Create multiple client challans",
			description = "Creates multiple challans in one request. Challan Type: `I` (Issue) or `R` (Received)"
	)
	@ApiResponses({
			@ApiResponse(responseCode = "201", description = "Challans created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid challan data"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<Set<ClientChallanVo>>> saveAll(
			@RequestBody Set<ClientChallanVo> vos) {

		log.info("Received request for bulk save :: {}", vos);
		Set<ClientChallanVo> saved = service.saveAll(vos);
		if (saved != null) {
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Records saved", saved, metadataGenerator.getMetadata(saved)));
		}
		return ResponseEntity.status(500)
				.body(ApiResponseVoWrapper.success("Records not saved", saved, metadataGenerator.getMetadata(saved)));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a client challan by ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Challan deleted successfully"),
			@ApiResponse(responseCode = "404", description = "Challan not found")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ClientChallanVo>> delete(
			@Parameter(description = "Challan ID to delete", required = true) @PathVariable Long id) {

		log.info("Received request for delete :: challanId {}", id);
		ClientChallanVo deleted = service.deleteById(id);
		if (deleted != null) {
			return ResponseEntity.status(200)
					.body(ApiResponseVoWrapper.success("Record deleted", deleted, metadataGenerator.getMetadata(deleted)));
		}
		return ResponseEntity.status(404)
				.body(ApiResponseVoWrapper.success("Record not found", deleted, metadataGenerator.getMetadata(deleted)));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a client challan by ID")
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Challan found"),
			@ApiResponse(responseCode = "404", description = "Challan not found")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ClientChallanVo>> findById(
			@Parameter(description = "Challan ID to fetch", required = true) @PathVariable Long id) {

		log.info("Received request for find :: id - {}", id);
		ClientChallanVo found = service.findById(id);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				found != null ? "Record found" : "Record not found",
				found, metadataGenerator.getMetadata(found)));
	}

	@GetMapping
	@Operation(
			summary = "Search challans by filters",
			description = """
					Fetch challans using optional filters:
					- challanNumber (Integer)
					- clientId (Long)
					- orderId (Long)
					- fromChallanDate / toChallanDate (max 90 days range)
					- challanType: `I` (Issue) or `R` (Received)
					"""
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Records fetched successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid filter or date range")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ClientChallanVo>>> find(
			@Parameter(description = "Challan number") @RequestParam(value = "challannumber", required = false) Integer challanNumber,
			@Parameter(description = "Client ID") @RequestParam(value = "clientid", required = false) Long clientId,
			@Parameter(description = "Order ID") @RequestParam(value = "orderid", required = false) Long orderId,
			@Parameter(description = "Start date for challan search (max 90-day range)") @RequestParam(value = "fromchallandate", required = false) LocalDate fromChallanDate,
			@Parameter(description = "End date for challan search (max 90-day range)") @RequestParam(value = "tochallandate", required = false) LocalDate toChallanDate,
			@Parameter(description = "Challan type: I (Issue) or R (Received)") @RequestParam(value = "challantype", required = false) String challanType) {

		log.info(
				"Received request for find :: challanNumber {}, clientId {}, fromChallanDate {}, toChallanDate {}, challanType {}, orderId {}",
				challanNumber, clientId, fromChallanDate, toChallanDate, challanType, orderId);

		if (fromChallanDate != null && toChallanDate != null) {
			if (toChallanDate.isBefore(fromChallanDate)) {
				throw new BusinessException("Invalid date range: 'To Date' must be greater than or equal to 'From Date'");
			}
			long days = ChronoUnit.DAYS.between(fromChallanDate, toChallanDate);
			if (days > 90) {
				throw new BusinessException("Date range cannot exceed 90 days");
			}
		}

		List<ClientChallanVo> found = service.findAll(challanNumber, clientId, orderId, fromChallanDate, toChallanDate, challanType);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}
}