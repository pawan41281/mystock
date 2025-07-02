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
@RequestMapping("/v1/orders/")
@AllArgsConstructor
@Tag(name = "Order Operations")
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

	@GetMapping("{id}")
	@Operation(summary = "Find Operation", description = "Find order by id")
	public ResponseEntity<ApiResponseVo<OrderVo>> findById(@PathVariable Long id) {
		OrderVo orderVo = orderService.findById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(orderVo != null ? "1" : "0"));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, orderVo, metadata));
	}

	@GetMapping("ordernumber/{ordernumber}")
	@Operation(summary = "Find Operation", description = "Find by order number")
	public ResponseEntity<ApiResponseVo<List<OrderVo>>> findByOrderNumber(@PathVariable Integer ordernumber) {
		List<OrderVo> list = orderService.list();
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("client/{client}")
	@Operation(summary = "Find Operation", description = "Find by client")
	public ResponseEntity<ApiResponseVo<List<OrderVo>>> findByClient(@PathVariable Long client) {
		List<OrderVo> list = orderService.findByClient(client);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("fromdate/todate/client")
	@Operation(summary = "Find Operation", description = "Find by orderdate between and client")
	public ResponseEntity<ApiResponseVo<List<OrderVo>>> findByOrderDateBetweenAndClient(@RequestParam Long client,
			@RequestParam Long fromDate, @RequestParam Long toDate) {
		List<OrderVo> list = orderService.findByOrderDateBetweenAndClient(fromDate, toDate, client);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("fromdate/todate/client/design")
	@Operation(summary = "Find Operation", description = "Find by orderdate between and client and design")
	public ResponseEntity<ApiResponseVo<List<OrderVo>>> findByOrderDateBetweenAndClientAndDesign(
			@RequestParam Long client, @RequestParam Long fromDate, @RequestParam Long toDate,
			@RequestParam String design) {
		List<OrderVo> list = orderService.findByOrderDateBetweenAndClientAndDesign(fromDate, toDate, client, design);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("fromdate/todate/client/design/color")
	@Operation(summary = "Find Operation", description = "Find by orderdate between and client and design and color")
	public ResponseEntity<ApiResponseVo<List<OrderVo>>> findByOrderDateBetweenAndClientAndDesignAndColor(
			@RequestParam Long client, @RequestParam Long fromDate, @RequestParam Long toDate,
			@RequestParam String design, String color) {
		List<OrderVo> list = orderService.findByOrderDateBetweenAndClientAndDesignAndColor(fromDate, toDate, client, design, color);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("fromdate/todate/design")
	@Operation(summary = "Find Operation", description = "Find by client and orderdate between")
	public ResponseEntity<ApiResponseVo<List<OrderVo>>> findByOrderDateBetweenAndDesign(@RequestParam Long fromDate,
			@RequestParam Long toDate, @RequestParam String design) {
		List<OrderVo> list = orderService.findByOrderDateBetweenAndDesign(fromDate, toDate, design);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@GetMapping("fromdate/todate/design/color")
	@Operation(summary = "Find Operation", description = "Find by client and orderdate between")
	public ResponseEntity<ApiResponseVo<List<OrderVo>>> findByOrderDateBetweenAndDesignAndColor(
			@RequestParam Long fromDate, @RequestParam Long toDate, @RequestParam String design,
			@RequestParam String color) {
		List<OrderVo> list = orderService.findByOrderDateBetweenAndDesignAndColor(fromDate, toDate, design, color);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, list, metadata));
	}

	@PatchMapping("{id}/{status}")
	@Operation(summary = "Update Operation", description = "Update status by id")
	public ResponseEntity<ApiResponseVo<OrderVo>> updateStatus(@PathVariable Long id, @PathVariable boolean status) {
		OrderVo orderVo = orderService.updateStatus(status, id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(orderVo != null ? "1" : "0"));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(orderVo!=null?"Status updated successfully":"Record not found", orderVo, metadata));
	}
}
