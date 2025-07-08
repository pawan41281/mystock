package org.mystock.controller;

import java.util.List;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.DesignService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.DesignVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v2/designs/")
@AllArgsConstructor
@Tag(name = "Design Operations", description = "CRUD Operations for design record")
@Slf4j
public class DesignController {

	private final DesignService designService;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(summary = "Save operation", description = "Save design record")
	public ResponseEntity<ApiResponseVo<DesignVo>> save(@RequestBody DesignVo vo) {
		log.info("Received request for save :: {}", vo);
		DesignVo saved = designService.save(vo);
		if (saved != null && saved.getId() != null) {
			log.info("Record saved :: {}", saved);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved :: {}", vo);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not saved", vo, metadataGenerator.getMetadata(saved)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Bulk Save operation", description = "Save design record")
	public ResponseEntity<ApiResponseVo<Set<DesignVo>>> save(@RequestBody Set<DesignVo> vos) {
		log.info("Received request for bulk save :: {}", vos);
		Set<DesignVo> saved = designService.saveAll(vos);
		if (saved != null && !saved.isEmpty()) {
			log.info("Record saved :: {}", saved);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved :: {}", vos);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not saved", vos, metadataGenerator.getMetadata(saved)));
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get by ID", description = "Get a design by its ID")
	public ResponseEntity<ApiResponseVo<DesignVo>> getById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		DesignVo found = designService.getById(id);
		if (found != null) {
			log.info("Record found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.info("Record not found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
		}

	}

	@GetMapping
	@Operation(summary = "Get All", description = "Get all designs")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getAll() {
		log.info("Received request for find all");
		List<DesignVo> found = designService.getAll();
		if (found != null && !found.isEmpty()) {
			log.info("Record found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", found, metadataGenerator.getMetadata(found)));
		} else {
			log.error("Record not found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not saved", found, metadataGenerator.getMetadata(found)));
		}
	}

	@PatchMapping("/{id}/{status}")
	@Operation(summary = "Update status by ID", description = "Update design record status by ID")
	public ResponseEntity<ApiResponseVo<DesignVo>> update(@PathVariable Long id, @PathVariable boolean status) {
		log.info("Received request for status update :: {} - {}", id, status);
		DesignVo saved = designService.updateStatus(id, status);
		if (saved != null && saved.getId() != null) {
			log.info("Record updated :: {}", saved);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record updated", saved, metadataGenerator.getMetadata(saved)));
		} else {
			log.error("Record not saved :: {}", saved);
			return ResponseEntity.ok(
					ApiResponseVoWrapper.success("Record not updated", saved, metadataGenerator.getMetadata(saved)));
		}

	}

}
