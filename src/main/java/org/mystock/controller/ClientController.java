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
		ClientVo saved = clientService.save(clientVo);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", saved != null && saved.getId() != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				saved != null && saved.getId() != null ? "Record saved" : "Record not saved", saved, metadata));
	}

	@GetMapping
	@Operation(summary = "Get all clients")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getAll() {
		List<ClientVo> clients = clientService.getAll();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clients.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", clients, metadata));
	}

	@GetMapping("{id}")
	@Operation(summary = "Get client by ID")
	public ResponseEntity<ApiResponseVo<ClientVo>> getById(@PathVariable Long id) {
		ClientVo client = clientService.getById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", client != null ? "1" : "0");
		if (client != null) {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record found", client, metadata));
		} else {
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Record not found", null, metadata));
		}
	}

//	@PatchMapping("{id}/{status}")
//	@Operation(summary = "Update status by ID", description = "Update client status by ID")
//	public ResponseEntity<ApiResponseVo<ClientVo>> update(@PathVariable Long id, @PathVariable boolean status) {
//		ClientVo updatedVo = clientService.updateStatus(id, status);
//		Map<String, String> metadata = new HashMap<>();
//		metadata.put("recordcount", (updatedVo != null && updatedVo.getId() != null) ? "1" : "0");
//		return ResponseEntity.ok(ApiResponseVoWrapper.success(
//				(updatedVo != null && updatedVo.getId() != null) ? "Status updated" : "Status not updated", updatedVo,
//				metadata));
//	}

	@GetMapping("name")
	@Operation(summary = "Get client by Name")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getByName(@RequestParam String name) {
		List<ClientVo> clients = clientService.findByClientNameIgnoreCase(name);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clients.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", clients, metadata));
	}

	@GetMapping("city")
	@Operation(summary = "Get client by City")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getByCity(@RequestParam String city) {
		List<ClientVo> clients = clientService.findByCityIgnoreCase(city);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clients.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", clients, metadata));
	}

	@GetMapping("state")
	@Operation(summary = "Get client by State")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getByState(@RequestParam String state) {
		List<ClientVo> clients = clientService.findByStateIgnoreCase(state);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clients.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", clients, metadata));
	}

	@GetMapping("country")
	@Operation(summary = "Get client by Country")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getByCountry(@RequestParam String country) {
		List<ClientVo> clients = clientService.findByCountryIgnoreCase(country);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clients.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", clients, metadata));
	}

	@GetMapping("email")
	@Operation(summary = "Get client by Email")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getByEmail(@RequestParam String email) {
		List<ClientVo> clients = clientService.findByEmailIgnoreCase(email);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clients.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", clients, metadata));
	}

	@GetMapping("mobile")
	@Operation(summary = "Get client by Mobile")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getByMobile(@RequestParam String mobile) {
		List<ClientVo> clients = clientService.findByMobile(mobile);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clients.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", clients, metadata));
	}

	@GetMapping("gstno")
	@Operation(summary = "Get client by GST Number")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getByGst(@RequestParam String gst) {
		List<ClientVo> clients = clientService.findByGstNoIgnoreCase(gst);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clients.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", clients, metadata));
	}

	@GetMapping("status")
	@Operation(summary = "Get client by Status")
	public ResponseEntity<ApiResponseVo<List<ClientVo>>> getByActive(@RequestParam boolean active) {
		List<ClientVo> clients = clientService.findByActive(active);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(clients.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success("Records fetched", clients, metadata));
	}

}
