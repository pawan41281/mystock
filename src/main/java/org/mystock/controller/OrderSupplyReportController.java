package org.mystock.controller;

import java.util.HashMap;
import java.util.Map;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.OrderSupplyReportService;
import org.mystock.vo.OrderSupplyReportVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/v1/ordersupplyreport/")
@AllArgsConstructor
@Tag(name = "Order Supply Report Operations")
public class OrderSupplyReportController {

	private final OrderSupplyReportService orderSupplyReportService;

	@GetMapping("{ordernumber}")
	@Operation(summary = "Order Supply Report", description = "Order Sypply Report By Order Number")
	public ResponseEntity<ApiResponseVo<OrderSupplyReportVo>> getReport(@PathVariable Integer ordernumber) {
		OrderSupplyReportVo orderSupplyReportVo = orderSupplyReportService.getReport(ordernumber);
		Map<String, String> metadata = new HashMap<>();
		metadata.put("recordcount", String.valueOf(orderSupplyReportVo != null ? 1 : 0));
		return ResponseEntity.ok(ApiResponseVoWrapper.success(
				orderSupplyReportVo != null ? "Report found" : "Report not found", orderSupplyReportVo, metadata));
	}

}
