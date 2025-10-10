package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.DesignStockReportService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.DesignStockReportVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/designstockreports")
@AllArgsConstructor
@Slf4j
@Tag(name = "Report", description = "Endpoints for reports")
@SecurityRequirement(name = "Bearer Authentication")
public class DesignStockReportController {

	private final DesignStockReportService designStockReportService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping
	@Operation(summary = "Get Design and Color wise Stock balance", description = "Get Design and Color wise Stock balance")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<DesignStockReportVo>>> getBalanceReport(
			@Parameter(description = "Filter by design name (partial match)") @RequestParam(required = false) String designName,
			@Parameter(description = "Filter by color name (partial match)") @RequestParam(required = false) String colorName) {
		log.info("Received request for design wise stock report");

		List<DesignStockReportVo> found = Collections.emptyList();

		if ((designName != null && !designName.isEmpty()) || (colorName != null && !colorName.isEmpty()))
			found = designStockReportService.getDesignStockReport(designName, colorName);
		else
			found = designStockReportService.getDesignStockNonZeroReport(designName, colorName);

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
//	@Operation(summary = "Get Design and Color wise Stock balance", description = "Get Design and Color wise Stock balance (Non Zero Balance)")
//	public ResponseEntity<ApiResponseVo<List<DesignStockReportVo>>> getNonZeroBalanceReport(
//			@Parameter(description = "Filter by design name (partial match)") 
//			@RequestParam(required = false) String designName,
//			@Parameter(description = "Filter by color name (partial match)") 
//			@RequestParam(required = false) String colorName){
//		log.info("Received request for design wise stock report");
//		List<DesignStockReportVo> found = designStockReportService.getDesignStockNonZeroReport(designName, colorName);
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