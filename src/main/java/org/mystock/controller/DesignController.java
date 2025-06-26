package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/v1/designs/")
@AllArgsConstructor
@Tag(name = "Design Operations")
public class DesignController {

	private final DesignService designService;

	@GetMapping
	@Operation(summary = "List Operation", description = "Fetch design list")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getDesigns() {
		List<DesignVo> list = designService.list();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("{id}")
	@Operation(summary = "Find Operation", description = "Find based on id")
	public ResponseEntity<ApiResponseVo<DesignVo>> getDesignsById(@PathVariable Long id) {
		DesignVo designVo = designService.findById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", designVo==null?"0":"1");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(designVo==null?"Record not found":"Record found", designVo, metadata));
	}

	@GetMapping("designnumber/{designnumber}")
	@Operation(summary = "Find Operation", description = "Find based on design number")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getDesignsByDesignNumber(@PathVariable String designnumber) {
		List<DesignVo> list = designService.findByDesignIgnoreCase(designnumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("color/{color}")
	@Operation(summary = "Find Operation", description = "Find based on color")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getDesignsByColor(@PathVariable String color) {
		List<DesignVo> list = designService.findByColorIgnoreCase(color);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("status/{status}")
	@Operation(summary = "Find Operation", description = "Find based on status")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getDesignsByStatus(@PathVariable boolean status) {
		List<DesignVo> list = designService.findByStatusIgnoreCase(status);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("{designnumber}/{color}/{status}")
	@Operation(summary = "Find Operation", description = "Find based on design number or color or status")
	public ResponseEntity<ApiResponseVo<List<DesignVo>>> getDesignsByDesignNumberOrColorOrStatus(
			@PathVariable String designnumber, @PathVariable String color, @PathVariable boolean status) {
		List<DesignVo> list = designService.findByDesignIgnoreCaseOrColorIgnoreCaseOrStatus(designnumber, color,
				status);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Save design record")
	public ResponseEntity<ApiResponseVo<DesignVo>> save(@RequestBody DesignVo designVo) {
		designVo = designService.save(designVo);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, designVo, null));
	}
	


	@PatchMapping("{id}/{status}")
	@Operation(summary = "Update Operation", description = "Update status based on id")
	public ResponseEntity<ApiResponseVo<DesignVo>> updateStatus(@PathVariable Long id, @PathVariable boolean status) {
		DesignVo designVo = designService.updateStatus(status, id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", designVo==null?"0":"1");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(designVo==null?"Record not found":"Record updated successfully", designVo, metadata));
	}
}
