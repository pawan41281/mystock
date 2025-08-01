package org.mystock.controller;

import java.util.List;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorStockReportService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ContractorStockReportVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v2/contractorstockreports/")
@AllArgsConstructor
@Slf4j
@Tag(name = "Report", description = "Endpoints for reports")
public class ContractorStockReportController {

	private final ContractorStockReportService contractorStockReportService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping
	@Operation(summary = "Get contractor stock report", description = "Returns the stock balance for each contractor, design, and color combination.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Report fetched successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<ApiResponseVo<List<ContractorStockReportVo>>> getReport(
			@Parameter(description = "Filter by contractor name (partial match)") @RequestParam(required = false) String contractorName,
			@Parameter(description = "Filter by design name (partial match)") @RequestParam(required = false) String designName,
			@Parameter(description = "Filter by color name (partial match)") @RequestParam(required = false) String colorName) {
		log.info("Received request for client's design wise stock report");
		List<ContractorStockReportVo> found = contractorStockReportService.getStockReport(contractorName, designName,
				colorName);
		if (found != null && !found.isEmpty()) {
			log.info("Record found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.error("Record not found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}
}
