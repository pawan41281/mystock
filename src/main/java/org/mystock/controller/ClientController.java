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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
