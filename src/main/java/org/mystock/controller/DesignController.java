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

@RestController
@RequestMapping("/v2/designs/")
@AllArgsConstructor
@Tag(name = "Design Operations", description = "CRUD Operations for design record")
public class DesignController {

	private final DesignService designService;
	private final MetadataGenerator metadataGenerator;

	@PostMapping
	@Operation(summary = "Save operation", description = "Save design record")
	public ResponseEntity<ApiResponseVo<DesignVo>> save(@RequestBody DesignVo designVo) {
		DesignVo vo = designService.save(designVo);
		if (vo != null) {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", vo, metadataGenerator.getMetadata(vo)));
		} else {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", designVo, metadataGenerator.getMetadata(vo)));
		}
	}

	@PostMapping("bulk")
	@Operation(summary = "Bulk Save operation", description = "Save design record")
	public ResponseEntity<ApiResponseVo<Set<DesignVo>>> save(@RequestBody Set<DesignVo> designVos) {
		Set<DesignVo> vos = designService.saveAll(designVos);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(vos != null ? "Record saved" : "Record not saved", vos,
				metadataGenerator.getMetadata(vos)));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get by ID", description = "Get a design by its ID")
	public ResponseEntity<ApiResponseVo<DesignVo>> getById(@PathVariable Long id) {
		DesignVo vo = designService.getById(id);
		if (vo != null) {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record found", vo, metadataGenerator.getMetadata(vo)));
		} else {
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not found", vo, metadataGenerator.getMetadata(vo)));
		}
	}

	@GetMapping
	@Operation(summary = "Get All", description = "Get all designs")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getAll() {
		List<DesignVo> vos = designService.getAll();
		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Records fetched", vos, metadataGenerator.getMetadata(vos)));
	}

	@GetMapping("status/{status}")
	@Operation(summary = "Get All By Status", description = "Get all designs by status")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getByStatus(@PathVariable boolean status) {
		List<DesignVo> vos = designService.getByStatus(status);
		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Records fetched", vos, metadataGenerator.getMetadata(vos)));
	}

	@PatchMapping("/{id}/{status}")
	@Operation(summary = "Update status by ID", description = "Update design record status by ID")
	public ResponseEntity<ApiResponseVo<DesignVo>> update(@PathVariable Long id, @PathVariable boolean status) {
		DesignVo vo = designService.update(id, status);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				(vo != null && vo.getId() != null) ? "Status updated" : "Status not updated", vo,
				metadataGenerator.getMetadata(vo)));
	}

}
