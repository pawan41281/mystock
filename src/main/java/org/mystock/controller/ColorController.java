package org.mystock.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ColorService;
import org.mystock.vo.ColorVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v2/colors/")
@AllArgsConstructor
@Tag(name = "Color Operations", description = "CRUD Operations for color record")
public class ColorController {

	private final ColorService colorService;

	@PostMapping
	@Operation(summary = "Create or update color")
	public ResponseEntity<ApiResponseVo<ColorVo>> save(@RequestBody ColorVo colorVo) {
		ColorVo vo = colorService.save(colorVo);
		return ResponseEntity.ok(ApiResponseVoWrapper
				.success(vo != null && vo.getId() != null ? "Record saved" : "Record not saved", vo, getMetadata(vo)));
	}

	@PostMapping("bulk")
	@Operation(summary = "Create or update multiple colors")
	public ResponseEntity<ApiResponseVo<Set<ColorVo>>> saveAll(@RequestBody Set<ColorVo> colorVos) {
		Set<ColorVo> vos = colorService.saveAll(colorVos);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(vos != null ? "Record saved" : "Record not saved",
				colorVos, getMetadata(vos)));
	}

	@GetMapping("{id}")
	@Operation(summary = "Get color by ID")
	public ResponseEntity<ApiResponseVo<ColorVo>> getById(@PathVariable Long id) {
		ColorVo vo = colorService.getById(id);
		if (vo != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", vo, getMetadata(vo)));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", null, getMetadata(vo)));
		}
	}

	private Map<String, String> getMetadata(ColorVo vo) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(vo != null && vo.getId() != null ? 1 : 0));
		return metadata;
	}

	private Map<String, String> getMetadata(Collection<ColorVo> collection) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount",
				String.valueOf(collection != null && !collection.isEmpty() ? collection.size() : 0));
		return metadata;
	}

}
