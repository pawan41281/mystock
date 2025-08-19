package org.mystock.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ClientChallanService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ClientChallanVo;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/v2/clientchallans")
@AllArgsConstructor
@Tag(name = "Client Challan Operations", description = "CRUD Operations for client challan record")
@Slf4j
public class ClientChallanController {

	private final ClientChallanService service;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(summary = "Create client challan", description = "Challan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<ClientChallanVo>> save(@Valid @RequestBody ClientChallanVo vo) {
		log.info("Received request for save :: {}", vo);
		if(vo.getId()!=null && vo.getId().equals(0L)) vo.setId(null);
		ClientChallanVo saved = service.save(vo);
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
	@Operation(summary = "Create multiple client challan", description = "Challan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<Set<ClientChallanVo>>> saveAll(@RequestBody Set<ClientChallanVo> vos) {
		log.info("Received request for bulk save :: {}", vos);
		Set<ClientChallanVo> saved = service.saveAll(vos);
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
	@Operation(summary = "Delete client challan")
	public ResponseEntity<ApiResponseVo<ClientChallanVo>> delete(@PathVariable Long id) {
		log.info("Received request for delete :: challanId {}", id);
		ClientChallanVo deleted = service.deleteById(id);
		if (deleted != null) {
			log.info("Record deleted");
			return ResponseEntity.status(201).body(
					ApiResponseVoWrapper.success("Record deleted", deleted, metadataGenerator.getMetadata(deleted)));
		} else {
			log.error("Record not deleted :: challanId {}", id);
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.success("Record not deleted", deleted,
					metadataGenerator.getMetadata(deleted)));
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get all challans by Id")
	public ResponseEntity<ApiResponseVo<ClientChallanVo>> findById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ClientChallanVo found = service.findById(id);
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
	@Operation(summary = "Get all challans by challan number and challan date range and challan type and client Id", description = "Challan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ClientChallanVo>>> find(
			@RequestParam(value = "challannumber", required = false) Integer challanNumber,
			@RequestParam(value = "clientid", required = false) Long clientId,
			@RequestParam(value = "fromchallandate", required = false) LocalDate fromChallanDate,
			@RequestParam(value = "tochallandate", required = false) LocalDate toChallanDate,
			@RequestParam(value = "challantype", required = false) String challanType) {

		log.info(
				"Received request for find :: challanNumber {}, clientId {}, fromChallanDate {}, toChallanDate {}, challanType {}",
				challanNumber, clientId, fromChallanDate, toChallanDate, challanType);

		List<ClientChallanVo> found = service.findAll(challanNumber, clientId,
				fromChallanDate, toChallanDate, challanType);
		
		log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}

}
