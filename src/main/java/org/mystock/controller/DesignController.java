package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.DesignService;
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

	@PostMapping
	@Operation(summary = "Save operation", description = "Save design record")
	public ResponseEntity<ApiResponseVo<DesignVo>> save(@RequestBody DesignVo designVo) {
		designVo = designService.save(designVo);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", designVo != null && designVo.getId() != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				designVo != null && designVo.getId() != null ? "Record saved" : "Record not saved", designVo,
				metadata));
	}

	@PostMapping("bulk")
	@Operation(summary = "Bulk Save operation", description = "Save design record")
	public ResponseEntity<ApiResponseVo<Set<DesignVo>>> save(@RequestBody Set<DesignVo> designVos) {
		Set<DesignVo> saved = designService.saveAll(designVos);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(saved != null ? saved.size() : 0));
		return ResponseEntity
				.ok(ApiResponseVoWrapper.success(saved != null ? "Record saved" : "Record not saved", saved, metadata));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get by ID", description = "Get a design by its ID")
	public ResponseEntity<ApiResponseVo<DesignVo>> getById(@PathVariable Long id) {
		DesignVo designVo = designService.getById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", designVo != null ? "1" : "0");
		if (designVo != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", designVo, metadata));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", null, metadata));
		}
	}

	@GetMapping
	@Operation(summary = "Get All", description = "Get all designs")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getAll() {
		List<DesignVo> list = designService.getAll();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", list, metadata));
	}

	@GetMapping("status/{status}")
	@Operation(summary = "Get All By Status", description = "Get all designs by status")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getByStatus(@PathVariable boolean status) {
		List<DesignVo> list = designService.getByStatus(status);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", list, metadata));
	}

	@PatchMapping("/{id}/{status}")
	@Operation(summary = "Update status by ID", description = "Update design record status by ID")
	public ResponseEntity<ApiResponseVo<DesignVo>> update(@PathVariable Long id, @PathVariable boolean status) {
		DesignVo updatedVo = designService.update(id, status);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", (updatedVo != null && updatedVo.getId() != null) ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				(updatedVo != null && updatedVo.getId() != null) ? "Status updated" : "Status not updated", updatedVo,
				metadata));
	}

}
