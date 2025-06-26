package org.mystock.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.OrderTransactionService;
import org.mystock.vo.OrderTransactionVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/v1/ordertransactions/")
@AllArgsConstructor
@Tag(name = "Order Transaction Operations")
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

	@GetMapping("{id}")
	@Operation(summary = "Find Operation", description = "Find order transaction by Id")
	public ResponseEntity<ApiResponseVo<OrderTransactionVo>> findById(@PathVariable Long id) {
		OrderTransactionVo orderTransactionVo = orderTransactionService.findById(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", orderTransactionVo != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				orderTransactionVo != null ? "Record exists" : "Recoud not exists", orderTransactionVo, metadata));
	}

	@GetMapping("/chalaannumber/{chalaannumber}")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan number")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanNumber(
			@PathVariable Integer chalaannumber) {
		List<OrderTransactionVo> list = orderTransactionService.findByChalaanNumber(chalaannumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("ordernumber/{ordernumber}")
	@Operation(summary = "Find Operation", description = "Find order transaction by order number")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByOrderNumber(
			@PathVariable Integer ordernumber) {
		List<OrderTransactionVo> list = orderTransactionService.findByOrderNumber(ordernumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("{fromDate}/{toDate}")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetween(@PathVariable Long fromDate,
			@PathVariable Long toDate) {
		List<OrderTransactionVo> list = orderTransactionService.findByChalaanDateBetween(fromDate, toDate);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/ordernumber")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date and order number")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetweenAndOrderNumber(
			@RequestParam Long fromDate, @RequestParam Long toDate, @RequestParam Integer ordernumber) {
		List<OrderTransactionVo> list = orderTransactionService.findByChalaanDateBetweenAndOrderNumber(fromDate, toDate,
				ordernumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/client")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date and client")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetweenAndClient(
			@RequestParam Long fromDate, @RequestParam Long toDate, @RequestParam Long client) {
		List<OrderTransactionVo> list = orderTransactionService.findByChalaanDateBetweenAndClient(fromDate, toDate,
				client);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/client/ordernumber")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date and client")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetweenAndClientAndOrderNumber(
			@RequestParam Long fromDate, @RequestParam Long toDate, @RequestParam Long client,
			@RequestParam Integer ordernumber) {
		List<OrderTransactionVo> list = orderTransactionService
				.findByChalaanDateBetweenAndClientAndOrderNumber(fromDate, toDate, client, ordernumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/client/ordernumber/design/color")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date and client and ordernumber and design and color")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetweenAndClientAndOrderNumberAndDesignAndColor(
			@RequestParam Long fromChalaanDate, @RequestParam Long toChalaanDate, @RequestParam Long client,
			@RequestParam Integer ordernumber, @RequestParam String design, @RequestParam String color) {
		List<OrderTransactionVo> list = orderTransactionService
				.findByChalaanDateBetweenAndClientAndOrderNumberAndDesignAndColor(fromChalaanDate, toChalaanDate,
						client, ordernumber, design, color);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/client/ordernumber/design")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date and client and ordernumber and design")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetweenAndClientAndOrderNumberAndDesign(
			@RequestParam Long fromChalaanDate, @RequestParam Long toChalaanDate, @RequestParam Long client,
			@RequestParam Integer ordernumber, @RequestParam String design) {
		List<OrderTransactionVo> list = orderTransactionService
				.findByChalaanDateBetweenAndClientAndOrderNumberAndDesign(fromChalaanDate, toChalaanDate, client,
						ordernumber, design);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/ordernumber/design/color")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date and ordernumber and design and color")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetweenAndOrderNumberAndDesignAndColor(
			@RequestParam Long fromChalaanDate, @RequestParam Long toChalaanDate, @RequestParam Integer ordernumber,
			@RequestParam String design, @RequestParam String color) {
		List<OrderTransactionVo> list = orderTransactionService.findByChalaanDateBetweenAndOrderNumberAndDesignAndColor(
				fromChalaanDate, toChalaanDate, ordernumber, design, color);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/ordernumber/design")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date and ordernumber and design")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetweenAndOrderNumberAndDesign(
			@RequestParam Long fromChalaanDate, @RequestParam Long toChalaanDate, @RequestParam Integer ordernumber,
			@RequestParam String design) {
		List<OrderTransactionVo> list = orderTransactionService
				.findByChalaanDateBetweenAndOrderNumberAndDesign(fromChalaanDate, toChalaanDate, ordernumber, design);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/client/design/color")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date and client and design and color")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetweenAndClientAndDesignAndColor(
			@RequestParam Long fromChalaanDate, @RequestParam Long toChalaanDate, @RequestParam Long client,
			@RequestParam String design, @RequestParam String color) {
		List<OrderTransactionVo> list = orderTransactionService.findByChalaanDateBetweenAndClientAndDesignAndColor(
				fromChalaanDate, toChalaanDate, client, design, color);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@GetMapping("fromchalaandate/tochalaanDate/client/design")
	@Operation(summary = "Find Operation", description = "Find order transaction by chalaan date and client and design")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> findByChalaanDateBetweenAndClientAndDesign(
			@RequestParam Long fromChalaanDate, @RequestParam Long toChalaanDate, @RequestParam Long client,
			@RequestParam String design) {
		List<OrderTransactionVo> list = orderTransactionService
				.findByChalaanDateBetweenAndClientAndDesign(fromChalaanDate, toChalaanDate, client, design);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record found" : "Record not found", list, metadata));
	}

	@PostMapping
	@Operation(summary = "Save Operation", description = "Save order record")
	public ResponseEntity<ApiResponseVo<OrderTransactionVo>> save(@RequestBody OrderTransactionVo orderTransactionVo) {
		orderTransactionVo = orderTransactionService.save(orderTransactionVo);
		return ResponseEntity.ok(ApiResponseVoWrapper.success(null, orderTransactionVo, null));
	}
	
	@DeleteMapping("{id}")
	@Operation(summary = "Delete Operation", description = "Delete order transaction by Id")
	public ResponseEntity<ApiResponseVo<OrderTransactionVo>> deleteById(@PathVariable Long id) {
		OrderTransactionVo orderTransactionVo = orderTransactionService.delete(id);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", orderTransactionVo != null ? "1" : "0");
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				orderTransactionVo != null ? "Record deleted" : "Recoud not exists", orderTransactionVo, metadata));
	}
	
	@DeleteMapping("ids")
	@Operation(summary = "Delete Operation", description = "Delete order transaction by ids")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> deleteByIds(
			@RequestBody List<Long> ids) {
		List<OrderTransactionVo> list = orderTransactionService.delete(ids);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record deleted" : "Record not found", list, metadata));
	}

	@DeleteMapping("/chalaannumber/{chalaannumber}")
	@Operation(summary = "Delete Operation", description = "Delete order transaction by chalaan number")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> deleteByChalaanNumber(
			@PathVariable Integer chalaannumber) {
		List<OrderTransactionVo> list = orderTransactionService.deleteByChalaanNumber(chalaannumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record deleted" : "Record not found", list, metadata));
	}

	@DeleteMapping("/design/{design}")
	@Operation(summary = "Delete Operation", description = "Delete order transaction by design")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> deleteByDesign(
			@PathVariable String design) {
		List<OrderTransactionVo> list = orderTransactionService.deleteByDesign(design);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record deleted" : "Record not found", list, metadata));
	}
	
	@DeleteMapping("ordernumber/{ordernumber}")
	@Operation(summary = "Delete Operation", description = "Delete order transaction by order number")
	public ResponseEntity<ApiResponseVo<List<OrderTransactionVo>>> deleteByOrderNumber(
			@PathVariable Integer ordernumber) {
		List<OrderTransactionVo> list = orderTransactionService.deleteByOrderNumber(ordernumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(list.size()));
		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(list.size() > 0 ? "Record deleted" : "Record not found", list, metadata));
	}
}
