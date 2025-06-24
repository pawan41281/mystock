package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.OrderService;
import org.mystock.vo.OrderVo;
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
@RequestMapping("/v1/orders/")
@AllArgsConstructor
@Tag(name="Order Operations")
public class OrderController {

	private final OrderService orderService;

	@GetMapping
	@Operation(summary = "List Operation", description = "Fetch order list")
	public ResponseEntity<ApiResponseVo<List<OrderVo>>> getOrders() {
		List<OrderVo> list = orderService.list();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Save order record")
	public ResponseEntity<ApiResponseVo<OrderVo>> save(@RequestBody OrderVo orderVo) {
		orderVo = orderService.save(orderVo);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, orderVo, null));
	}
}
