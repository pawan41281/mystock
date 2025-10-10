package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.BusinessException;
import org.mystock.service.ClientOrderService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ClientOrderVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

/**
 * Controller for managing Client Orders.
 * Provides endpoints for creating, retrieving, and deleting client order records.
 *
 * All endpoints are secured and require valid Bearer Token authentication.
 */
@RestController
@RequestMapping("/v1/clientorders")
@AllArgsConstructor
@Tag(
		name = "Client Order Operations",
		description = "Endpoints for performing CRUD operations on Client Orders.",
		extensions = {
				@Extension(
						name = "x-order",
						properties = { @ExtensionProperty(name = "position", value = "2") }
				)
		}
)
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ClientOrderController {

	private final ClientOrderService service;
	private final MetadataGenerator metadataGenerator;

	@Operation(
			summary = "Create a client order",
			description = "Creates a new client order record in the system. Requires ADMIN or USER role.",
			tags = {"Client Order Operations"}
	)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
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

	@Operation(
			summary = "Create multiple client orders (Bulk Save)",
			description = "Allows batch creation of multiple client orders. Requires ADMIN or USER role.",
			tags = {"Client Order Operations"}
	)
	@PostMapping("/bulk")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
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

	@Operation(
			summary = "Delete client order by ID",
			description = "Deletes the specified client order by its unique ID. Requires ADMIN or USER role.",
			tags = {"Client Order Operations"}
	)
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
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

	@Operation(
			summary = "Get client order by ID",
			description = "Fetches the client order details for the specified order ID. Requires ADMIN or USER role.",
			tags = {"Client Order Operations"}
	)
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
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

	@Operation(
			summary = "Search client orders",
			description = """
			Retrieves client orders by applying optional filters:
			- `ordernumber`: Filter by order number
			- `clientid`: Filter by client ID
			- `fromorderdate` and `toorderdate`: Filter by date range (maximum 90 days)
			
			Requires ADMIN or USER role.
			""",
			tags = {"Client Order Operations"}
	)
	@GetMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
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