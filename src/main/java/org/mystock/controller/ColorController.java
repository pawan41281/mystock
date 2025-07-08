package org.mystock.controller;

import java.util.List;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ColorService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ColorVo;
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
@RequestMapping("/v2/colors/")
@AllArgsConstructor
@Tag(name = "Color Operations", description = "CRUD Operations for color record")
@Slf4j
public class ColorController {

	private final ColorService colorService;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(summary = "Create or update color")
	public ResponseEntity<ApiResponseVo<ColorVo>> save(@RequestBody ColorVo vo) {
		log.info("Received request for save :: {}", vo);
		ColorVo saved = colorService.save(vo);
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
	@Operation(summary = "Create or update multiple colors")
	public ResponseEntity<ApiResponseVo<Set<ColorVo>>> saveAll(@RequestBody Set<ColorVo> vos) {
		log.info("Received request for bulk save :: {}", vos);
		Set<ColorVo> saved = colorService.saveAll(vos);
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

	@GetMapping("{id}")
	@Operation(summary = "Get color by ID")
	public ResponseEntity<ApiResponseVo<ColorVo>> getById(@PathVariable Long id) {
		log.info("Received request for find :: id - {}", id);
		ColorVo found = colorService.getById(id);
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
	@Operation(summary = "Get All", description = "Get all colors")
	public ResponseEntity<ApiResponseVo<List<ColorVo>>> getAll() {
		log.info("Received request for find all");
		List<ColorVo> found = colorService.getAll();
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
	public ResponseEntity<ApiResponseVo<ColorVo>> update(@PathVariable Long id, @PathVariable boolean status) {
		log.info("Received request for status update :: {} - {}", id, status);
		ColorVo saved = colorService.updateStatus(id, status);
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
