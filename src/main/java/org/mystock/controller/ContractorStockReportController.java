package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ContractorStockReportService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ContractorStockReportVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/contractorstockreports")
@AllArgsConstructor
@Slf4j
@Tag(name = "Report", description = "Endpoints for reports")
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorStockReportController {

	private final ContractorStockReportService contractorStockReportService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping
	@Operation(summary = "Get contractor stock report", description = "Returns the stock balance for each contractor, design, and color combination.")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Report fetched successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	public ResponseEntity<ApiResponseVo<List<ContractorStockReportVo>>> getBalanceReport(
			@Parameter(description = "Filter by contractor name (partial match)") @RequestParam(required = false) String contractorName,
			@Parameter(description = "Filter by design name (partial match)") @RequestParam(required = false) String designName,
			@Parameter(description = "Filter by color name (partial match)") @RequestParam(required = false) String colorName) {
		log.info("Received request for client's design wise stock report");

		List<ContractorStockReportVo> found = Collections.emptyList();

		if ((contractorName != null && !contractorName.isEmpty()) || (designName != null && !designName.isEmpty())
				|| (colorName != null && !colorName.isEmpty()))
			found = contractorStockReportService.getStockReport(contractorName, designName, colorName);
		else
			found = contractorStockReportService.getNonZeroStockReport(contractorName, designName, colorName);

		if (found != null && !found.isEmpty()) {
			log.info("Record found");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.error("Record not found");
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}

//	@GetMapping("/nonzero")
//	@Operation(summary = "Get contractor stock report (Non Zero Balance)", description = "Get contractor stock balance for each design, and color combination (Non Zero Balance)")
//	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Report fetched successfully"),
//			@ApiResponse(responseCode = "500", description = "Internal server error") })
//	public ResponseEntity<ApiResponseVo<List<ContractorStockReportVo>>> getNonZeroBalanceReport(
//			@Parameter(description = "Filter by contractor name (partial match)") @RequestParam(required = false) String contractorName,
//			@Parameter(description = "Filter by design name (partial match)") @RequestParam(required = false) String designName,
//			@Parameter(description = "Filter by color name (partial match)") @RequestParam(required = false) String colorName) {
//		log.info("Received request for client's design wise stock report");
//		List<ContractorStockReportVo> found = contractorStockReportService.getNonZeroStockReport(contractorName,
//				designName, colorName);
//		if (found != null && !found.isEmpty()) {
//			log.info("Record found");
//			return ResponseEntity
//					.ok(ApiResponseVoWrapper.success("Record found", found, metadataGenerator.getMetadata(found)));
//		} else {
//			log.error("Record not found");
//			return ResponseEntity
//					.ok(ApiResponseVoWrapper.success("Record not found", found, metadataGenerator.getMetadata(found)));
//		}
//	}
}