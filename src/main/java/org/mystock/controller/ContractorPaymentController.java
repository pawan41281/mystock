package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "Contractor Payment Operations", description = "CRUD Operations for contractor payment record")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorPaymentController {

	private final ContractorPaymentService service;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(summary = "Create contractor payment", description = "Save or Update Payment Traction")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorPaymentVo>> save(@Valid @RequestBody ContractorPaymentVo vo) {
		log.info("Received request for save");
		if(vo.getId()!=null && vo.getId().equals(0L)) vo.setId(null);
		ContractorPaymentVo saved = service.save(vo);
		if (saved != null && saved.getId() != null) {
			log.info("Record saved");
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.status(500).body(
					ApiResponseVoWrapper.success("Record not saved", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("/bulk")
	@Operation(summary = "Create multiple contractor payment", description = "Bulk Save or Update Payment Traction")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<Set<ContractorPaymentVo>>> saveAll(@RequestBody Set<ContractorPaymentVo> vos) {
		log.info("Received request for bulk save");
		Set<ContractorPaymentVo> saved = service.saveAll(vos);
		if (saved != null && !saved.isEmpty()) {
			log.info("Record saved");
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved");
			return ResponseEntity.status(500).body(
					ApiResponseVoWrapper.success("Record not saved", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete contractor payment")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorPaymentVo>> delete(@PathVariable Long id) {
		log.info("Received request for delete :: paymentId {}", id);
		ContractorPaymentVo deleted = service.deleteById(id);
		if (deleted != null) {
			log.info("Record deleted");
			return ResponseEntity.status(201).body(
					ApiResponseVoWrapper.success("Record deleted", deleted, metadataGenerator.getMetadata(deleted)));
		} else {
			log.error("Record not deleted :: paymentId {}", id);
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.success("Record not deleted", deleted,
					metadataGenerator.getMetadata(deleted)));
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get all payments by Id")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<ContractorPaymentVo>> findById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ContractorPaymentVo found = service.findById(id);
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

	@GetMapping
	@Operation(summary = "Get all payments by payment date range (90 Days max) and payment amount and contractor Id")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorPaymentVo>>> find(
			@RequestParam(value = "frompaymentdate", required = true) LocalDate fromPaymentDate,
			@RequestParam(value = "topaymentdate", required = true) LocalDate toPaymentDate,
			@RequestParam(value = "paymentamountstart", required = false, defaultValue = "0") Integer paymentAmountStart,
			@RequestParam(value = "paymentamountend", required = false, defaultValue = "99999999") Integer paymentAmountEnd,
			@RequestParam(value = "contractorid", required = false) Long contractorId) {
		log.info(
				"Received request for find :: fromPaymentDate {}, toPaymentDate {}, paymentAmountStart {}, paymentAmountEnd {}, contractorId {}",
				fromPaymentDate, toPaymentDate, paymentAmountStart, paymentAmountEnd, contractorId);

		if (fromPaymentDate != null && toPaymentDate != null) {
			if (toPaymentDate.isBefore(fromPaymentDate)) {
				throw new BusinessException("Invalid date range: 'To Date' must be greater than or equal to 'From Date'");
			}

			long days = ChronoUnit.DAYS.between(fromPaymentDate, toPaymentDate);
			log.info("Days {}",days);
			if (days > 90) {
				throw new BusinessException("Date range cannot exceed 90 days");
			}
		}
		
		List<ContractorPaymentVo> found = service.findAll(fromPaymentDate, toPaymentDate, paymentAmountStart, paymentAmountEnd, contractorId);
		log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}

}