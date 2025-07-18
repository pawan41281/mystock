package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@RequestMapping("/v1/clients/")
@AllArgsConstructor
@Tag(name="Client Operations")
public class ClientController {

	private final ClientService clientService;

	@GetMapping
	@Operation(summary = "List Operation", description = "Fetch client list")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getClients() {
		List<ClientVo> list = clientService.list();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Save client record")
	public ResponseEntity<ApiResponseVo<ClientVo>> save(@RequestBody ClientVo clientVo) {
		clientVo = clientService.save(clientVo);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, clientVo, null));
	}
	
	@GetMapping("id/{id}")
	@Operation(summary = "Find Operation", description = "Find clients by id")
	public ResponseEntity<ApiResponseVo<ClientVo>> getClientsById(@PathVariable Long id) {
		ClientVo clientVo = clientService.findById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clientVo!=null?1:0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(clientVo!=null?"Record found":"Record not found", clientVo, metadata));
	}
	
	@GetMapping("email")
	@Operation(summary = "Find Operation", description = "Find clients by email")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getClientsByEmail(@RequestParam String email) {
		List<ClientVo> list = clientService.findByEmailIgnoreCase(email);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}
	
	@GetMapping("mobile/{mobile}")
	@Operation(summary = "Find Operation", description = "Find clients by mobile")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getClientsByMobile(@PathVariable String mobile) {
		List<ClientVo> list = clientService.findByMobile(mobile);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}
	
	@GetMapping("gstno/{gstNo}")
	@Operation(summary = "Find Operation", description = "Find clients by GST Number")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getClientsByGstNo(@PathVariable String gstNo) {
		List<ClientVo> list = clientService.findByGstNoIgnoreCase(gstNo);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}
	
	@GetMapping("status/{status}")
	@Operation(summary = "Find Operation", description = "Find clients by status")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getClientsByStatus(@PathVariable boolean status) {
		List<ClientVo> list = clientService.findByStatus(status);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}
	
	@GetMapping("email/mobile/gstno/status")
	@Operation(summary = "Find Operation", description = "Find clients by email or mobile or gstno or status")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> findByEmailOrMobileOrGstNoOrStatus(
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String mobile,
			@RequestParam(required = false) String gstNo,
			@RequestParam(required = false) boolean status) {
		List<ClientVo> list = clientService.findByEmailOrMobileOrGstNoOrStatus(email, mobile, gstNo, status);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}
	
	@PatchMapping("{id}/{status}")
	@Operation(summary = "Update Operation", description = "Update status by id")
	public ResponseEntity<ApiResponseVo<ClientVo>> updateStatus(@PathVariable Long id, @PathVariable boolean status) {
		ClientVo clientVo = clientService.updateStatus(status, id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clientVo!=null?1:0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(clientVo!=null?"Status updated successfully":"Record not found", clientVo, metadata));
	}
}
