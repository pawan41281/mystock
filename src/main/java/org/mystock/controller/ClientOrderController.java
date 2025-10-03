package org.mystock.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.BusinessException;
import org.mystock.service.ClientOrderService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ClientOrderVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/clientorders")
@AllArgsConstructor
@Tag(name = "Client Order Operations", description = "CRUD Operations for client order record")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
public class ClientOrderController {

	private final ClientOrderService service;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(summary = "Create client order")
	public ResponseEntity<ApiResponseVo<ClientOrderVo>> save(@Valid @RequestBody ClientOrderVo vo) {
		log.info("Received request for save :: {}", vo);
		if (vo.getId() != null && vo.getId().equals(0L))
			vo.setId(null);
		ClientOrderVo saved = service.save(vo);
		if (saved != null && saved.getId() != null) {
			log.info("Record saved");
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved :: {}", vo);
			return ResponseEntity.status(500).body(
					ApiResponseVoWrapper.success("Record not saved", saved, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("/bulk")
	@Operation(summary = "Create multiple client order")
	public ResponseEntity<ApiResponseVo<Set<ClientOrderVo>>> saveAll(@RequestBody Set<ClientOrderVo> vos) {
		log.info("Received request for bulk save :: {}", vos);
		Set<ClientOrderVo> saved = service.saveAll(vos);
		if (saved != null) {
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
	@Operation(summary = "Delete client order")
	public ResponseEntity<ApiResponseVo<ClientOrderVo>> delete(@PathVariable Long id) {
		log.info("Received request for delete :: orderId {}", id);
		ClientOrderVo deleted = service.deleteById(id);
		if (deleted != null) {
			log.info("Record deleted");
			return ResponseEntity.status(201).body(
					ApiResponseVoWrapper.success("Record deleted", deleted, metadataGenerator.getMetadata(deleted)));
		} else {
			log.error("Record not deleted :: orderId {}", id);
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.success("Record not deleted", deleted,
					metadataGenerator.getMetadata(deleted)));
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get all orders by Id")
	public ResponseEntity<ApiResponseVo<ClientOrderVo>> findById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ClientOrderVo found = service.findById(id);
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
	@Operation(summary = "Get all orders by order number and order date range (90 Days max) and client Id")
	public ResponseEntity<ApiResponseVo<List<ClientOrderVo>>> find(
			@RequestParam(value = "ordernumber", required = false) Integer orderNumber,
			@RequestParam(value = "clientid", required = false) Long clientId,
			@RequestParam(value = "fromorderdate", required = false) LocalDate fromOrderDate,
			@RequestParam(value = "toorderdate", required = false) LocalDate toOrderDate) {

		log.info("Received request for find :: orderNumber {}, clientId {}, fromOrderDate {}, toOrderDate {}",
				orderNumber, clientId, fromOrderDate, toOrderDate);

		if (fromOrderDate != null && toOrderDate != null) {
			if (toOrderDate.isBefore(fromOrderDate)) {
				throw new BusinessException(
						"Invalid date range: 'To Date' must be greater than or equal to 'From Date'");
			}

			long days = ChronoUnit.DAYS.between(fromOrderDate, toOrderDate);
			log.info("Days {}", days);
			if (days > 90) {
				throw new BusinessException("Date range cannot exceed 90 days");
			}
		}

		List<ClientOrderVo> found = service.findAll(orderNumber, clientId, fromOrderDate, toOrderDate);

		log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}

}
