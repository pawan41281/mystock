package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.BusinessException;
import org.mystock.service.ContractorPaymentService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ContractorPaymentVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/contractorpayments")
@AllArgsConstructor
@Tag(name = "Contractor Payment Operations", description = "CRUD Operations for contractor payment records")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorPaymentController {

	private final ContractorPaymentService service;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(
			summary = "Create or Update Contractor Payment",
			description = "Creates a new contractor payment record or updates an existing one based on the provided ID. "
					+ "If `id` is null or 0, a new record will be created.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Payment record created successfully",
							content = @Content(schema = @Schema(implementation = ContractorPaymentVo.class))),
					@ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content),
					@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorPaymentVo>> save(
			@Valid @RequestBody ContractorPaymentVo vo) {
		log.info("Received request for save");
		if (vo.getId() != null && vo.getId().equals(0L)) vo.setId(null);

		ContractorPaymentVo saved = service.save(vo);
		if (saved != null && saved.getId() != null) {
			log.info("Record saved");
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.status(500)
					.body(ApiResponseVoWrapper.success("Record not saved", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("/bulk")
	@Operation(
			summary = "Bulk Create or Update Contractor Payments",
			description = "Creates or updates multiple contractor payment records in a single request.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Bulk records saved successfully",
							content = @Content(schema = @Schema(implementation = ContractorPaymentVo.class))),
					@ApiResponse(responseCode = "500", description = "Failed to save records", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<Set<ContractorPaymentVo>>> saveAll(
			@RequestBody Set<ContractorPaymentVo> vos) {
		log.info("Received request for bulk save");
		Set<ContractorPaymentVo> saved = service.saveAll(vos);
		if (saved != null && !saved.isEmpty()) {
			log.info("Records saved");
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Records saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Records not saved");
			return ResponseEntity.status(500)
					.body(ApiResponseVoWrapper.success("Records not saved", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@DeleteMapping("/{id}")
	@Operation(
			summary = "Delete Contractor Payment by ID",
			description = "Deletes a contractor payment record based on the given ID.",
			parameters = @Parameter(name = "id", description = "Contractor Payment ID", required = true),
			responses = {
					@ApiResponse(responseCode = "200", description = "Record deleted successfully",
							content = @Content(schema = @Schema(implementation = ContractorPaymentVo.class))),
					@ApiResponse(responseCode = "404", description = "Record not found", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorPaymentVo>> delete(@PathVariable Long id) {
		log.info("Received request for delete :: paymentId {}", id);
		ContractorPaymentVo deleted = service.deleteById(id);
		if (deleted != null) {
			log.info("Record deleted");
			return ResponseEntity.status(200)
					.body(ApiResponseVoWrapper.success("Record deleted", deleted, metadataGenerator.getMetadata(deleted)));
		} else {
			log.error("Record not deleted :: paymentId {}", id);
			return ResponseEntity.status(404)
					.body(ApiResponseVoWrapper.success("Record not deleted", deleted, metadataGenerator.getMetadata(deleted)));
		}
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Get Contractor Payment by ID",
			description = "Retrieves a specific contractor payment record using the provided ID.",
			parameters = @Parameter(name = "id", description = "Contractor Payment ID", required = true),
			responses = {
					@ApiResponse(responseCode = "200", description = "Record found",
							content = @Content(schema = @Schema(implementation = ContractorPaymentVo.class))),
					@ApiResponse(responseCode = "404", description = "Record not found", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorPaymentVo>> findById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ContractorPaymentVo found = service.findById(id);
		if (found != null) {
			log.info("Record found");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}

	@GetMapping
	@Operation(
			summary = "Get Contractor Payments by Filters",
			description = """
                    Fetches contractor payment records filtered by:
                    - Payment Date Range (maximum 90 days)
                    - Payment Amount Range
                    - Contractor ID
                    """,
			parameters = {
					@Parameter(name = "frompaymentdate", description = "Start date of the payment range (inclusive)", required = true),
					@Parameter(name = "topaymentdate", description = "End date of the payment range (inclusive)", required = true),
					@Parameter(name = "paymentamountstart", description = "Minimum payment amount (default: 0)", required = false),
					@Parameter(name = "paymentamountend", description = "Maximum payment amount (default: 99999999)", required = false),
					@Parameter(name = "contractorid", description = "Contractor ID for filtering", required = false)
			},
			responses = {
					@ApiResponse(responseCode = "200", description = "Filtered records fetched successfully",
							content = @Content(schema = @Schema(implementation = ContractorPaymentVo.class))),
					@ApiResponse(responseCode = "400", description = "Invalid date range or parameters", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorPaymentVo>>> find(
			@RequestParam(value = "frompaymentdate") LocalDate fromPaymentDate,
			@RequestParam(value = "topaymentdate") LocalDate toPaymentDate,
			@RequestParam(value = "paymentamountstart", required = false, defaultValue = "0") Integer paymentAmountStart,
			@RequestParam(value = "paymentamountend", required = false, defaultValue = "99999999") Integer paymentAmountEnd,
			@RequestParam(value = "contractorid", required = false) Long contractorId) {

		log.info("Received request for find :: fromPaymentDate {}, toPaymentDate {}, paymentAmountStart {}, paymentAmountEnd {}, contractorId {}",
				fromPaymentDate, toPaymentDate, paymentAmountStart, paymentAmountEnd, contractorId);

		if (toPaymentDate.isBefore(fromPaymentDate)) {
			throw new BusinessException("Invalid date range: 'To Date' must be greater than or equal to 'From Date'");
		}

		long days = ChronoUnit.DAYS.between(fromPaymentDate, toPaymentDate);
		if (days > 90) {
			throw new BusinessException("Date range cannot exceed 90 days");
		}

		List<ContractorPaymentVo> found = service.findAll(fromPaymentDate, toPaymentDate, paymentAmountStart, paymentAmountEnd, contractorId);
		log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}
}