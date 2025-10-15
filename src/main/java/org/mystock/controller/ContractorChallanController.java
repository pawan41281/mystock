package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.BusinessException;
import org.mystock.service.ContractorChallanService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ContractorChallanVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/contractorchallans")
@AllArgsConstructor
@Tag(name = "Contractor Challan Operations", description = "Endpoints to manage Contractor Challan records (Issue/Receive).")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorChallanController {

	private final ContractorChallanService service;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(
			summary = "Create contractor challan",
			description = "Creates a new contractor challan record. **Challan Type**: `I` (Issue) or `R` (Received).",
			responses = {
					@ApiResponse(responseCode = "201", description = "Record created successfully"),
					@ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
					@ApiResponse(responseCode = "500", description = "Server error", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorChallanVo>> save(
			@Valid @RequestBody
			@Parameter(description = "Contractor challan request object", required = true) ContractorChallanVo vo) {

		log.info("Received request for save");
		if (vo.getId() != null && vo.getId().equals(0L)) vo.setId(null);
		ContractorChallanVo saved = service.save(vo);
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
			summary = "Create multiple contractor challans",
			description = "Creates multiple contractor challan records in a single request. **Challan Type**: `I` or `R`.",
			responses = {
					@ApiResponse(responseCode = "201", description = "Records created successfully"),
					@ApiResponse(responseCode = "500", description = "Records not saved", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<Set<ContractorChallanVo>>> saveAll(
			@RequestBody
			@Parameter(description = "Set of contractor challan objects", required = true) Set<ContractorChallanVo> vos) {
		log.info("Received request for bulk save");
		Set<ContractorChallanVo> saved = service.saveAll(vos);
		if (saved != null && !saved.isEmpty()) {
			log.info("Record saved");
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.status(500)
					.body(ApiResponseVoWrapper.success("Record not saved", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@DeleteMapping("/{id}")
	@Operation(
			summary = "Delete contractor challan",
			description = "Deletes a contractor challan by its ID.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Record deleted successfully"),
					@ApiResponse(responseCode = "404", description = "Record not found", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorChallanVo>> delete(@PathVariable Long id) {
		log.info("Received request for delete :: challanId {}", id);
		ContractorChallanVo deleted = service.deleteById(id);
		if (deleted != null) {
			log.info("Record deleted");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record deleted", deleted, metadataGenerator.getMetadata(deleted)));
		} else {
			log.error("Record not deleted :: challanId {}", id);
			return ResponseEntity.status(404).body(ApiResponseVoWrapper.success("Record not deleted", null, metadataGenerator.getMetadata(null)));
		}
	}

	@GetMapping("/{id}")
	@Operation(
			summary = "Get contractor challan by ID",
			description = "Fetches a contractor challan record using its ID.",
			responses = {
					@ApiResponse(responseCode = "200", description = "Record found successfully"),
					@ApiResponse(responseCode = "404", description = "Record not found", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorChallanVo>> findById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ContractorChallanVo found = service.findById(id);
		if (found != null) {
			log.info("Record found");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found");
			return ResponseEntity.status(404).body(ApiResponseVoWrapper.success("Record not found", null, metadataGenerator.getMetadata(null)));
		}
	}

	@GetMapping
	@Operation(
			summary = "Get all contractor challans with filters",
			description = """
					Fetches contractor challan records filtered by challan number, contractor ID, 
					challan date range (max 90 days), and challan type (I/R).
					""",
			responses = {
					@ApiResponse(responseCode = "200", description = "Records fetched successfully"),
					@ApiResponse(responseCode = "400", description = "Invalid date range or parameters", content = @Content)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorChallanVo>>> find(
			@RequestParam(value = "challannumber", required = false) Integer challanNumber,
			@RequestParam(value = "contractorid", required = false) Long contractorId,
			@RequestParam(value = "fromchallandate", required = false) LocalDate fromChallanDate,
			@RequestParam(value = "tochallandate", required = false) LocalDate toChallanDate,
			@RequestParam(value = "challantype", required = false) String challanType) {

		log.info("Received request for find :: challanNumber {}, contractorId {}, fromChallanDate {}, toChallanDate {}, challanType {}",
				challanNumber, contractorId, fromChallanDate, toChallanDate, challanType);

		if (fromChallanDate != null && toChallanDate != null) {
			if (toChallanDate.isBefore(fromChallanDate)) {
				throw new BusinessException("Invalid date range: 'To Date' must be greater than or equal to 'From Date'");
			}
			long days = ChronoUnit.DAYS.between(fromChallanDate, toChallanDate);
			if (days > 90) {
				throw new BusinessException("Date range cannot exceed 90 days");
			}
		}

		List<ContractorChallanVo> found = service.findAll(challanNumber, contractorId, fromChallanDate, toChallanDate, challanType);
		log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}
}