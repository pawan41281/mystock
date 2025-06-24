package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.OrderTransactionService;
import org.mystock.vo.OrderTransactionVo;
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
@RequestMapping("/v1/ordertransactions/")
@AllArgsConstructor
@Tag(name="Order Transaction Operations")
public class OrderTransactionController {

	private final OrderTransactionService orderTransactionService;

	@GetMapping
	@Operation(summary = "List Operation", description = "Fetch order transaction list")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> getOrders() {
		List<OrderTransactionVo> list = orderTransactionService.list();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Save order record")
	public ResponseEntity<ApiResponseVo<OrderTransactionVo>> save(@RequestBody OrderTransactionVo orderTransactionVo) {
		orderTransactionVo = orderTransactionService.save(orderTransactionVo);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, orderTransactionVo, null));
	}
}
