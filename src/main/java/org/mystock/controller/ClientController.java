package org.mystock.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ClientService;
import org.mystock.vo.ClientVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v2/clients/")
@AllArgsConstructor
@Tag(name = "Client Operations", description = "CRUD Operations for client record")
public class ClientController {

	private final ClientService clientService;

	@PostMapping
	@Operation(summary = "Create or update client")
	public ResponseEntity<ApiResponseVo<ClientVo>> save(@RequestBody ClientVo clientVo) {
		ClientVo vo = clientService.save(clientVo);
		return ResponseEntity.ok(ApiResponseVoWrapper
				.success(vo != null && vo.getId() != null ? "Record saved" : "Record not saved", vo, getMetadata(vo)));
	}

	@PostMapping("bulk")
	@Operation(summary = "Create or update multiple clients")
	public ResponseEntity<ApiResponseVo<Set<ClientVo>>> saveAll(@RequestBody Set<ClientVo> clientVos) {
		Set<ClientVo> vos = clientService.saveAll(clientVos);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(vos != null ? "Record saved" : "Record not saved",
				clientVos, getMetadata(vos)));
	}

	@GetMapping("{id}")
	@Operation(summary = "Get client by ID")
	public ResponseEntity<ApiResponseVo<ClientVo>> getById(@PathVariable Long id) {
		ClientVo vo = clientService.getById(id);
		if (vo != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", vo, getMetadata(vo)));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", null, getMetadata(vo)));
		}
	}

	@PatchMapping("{id}/{status}")
	@Operation(summary = "Update status by ID", description = "Update client status by ID")
	public ResponseEntity<ApiResponseVo<ClientVo>> update(@PathVariable Long id, @PathVariable boolean status) {
		ClientVo vo = clientService.updateStatus(id, status);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				(vo != null && vo.getId() != null) ? "Status updated" : "Status not updated", vo, getMetadata(vo)));
	}

	@GetMapping
	@Operation(summary = "Get client by Name and City and State and Email and Mobile and GST Number and Status")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> find(@RequestParam(required = false) String clientName,
			@RequestParam(required = false) String city, @RequestParam(required = false) String state,
			@RequestParam(required = false) String mobile, @RequestParam(required = false) String email,
			@RequestParam(required = false) String gstNo, @RequestParam(required = false) Boolean active) {
		List<ClientVo> vos = clientService.find(clientName, city, state, mobile, email, gstNo, active);
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", vos, getMetadata(vos)));
	}

	private Map<String, String> getMetadata(ClientVo vo) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(vo != null && vo.getId() != null ? 1 : 0));
		return metadata;
	}

	private Map<String, String> getMetadata(Collection<ClientVo> collection) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount",
				String.valueOf(collection != null && !collection.isEmpty() ? collection.size() : 0));
		return metadata;
	}

}
