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
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v2/contractorstockreports")
@AllArgsConstructor
@Slf4j
@Tag(name = "Reports", description = "All Reports")
public class ContractorStockReportController {

	private final ContractorStockReportService contractorStockReportService;
	private final MetadataGenerator metadataGenerator;
	
	@GetMapping
	@Operation(summary = "Get Contractor and Design and Color wise Stock balance", description = "Get Contractor and Design and Color wise Stock balance")
	public ResponseEntity<ApiResponseVo<List<ContractorStockReportVo>>> getReport(
			@Parameter(description = "Filter by contractor name (partial match)") 
			@RequestParam(required = false) String contractorName,
			@Parameter(description = "Filter by design name (partial match)") 
			@RequestParam(required = false) String designName,
			@Parameter(description = "Filter by color name (partial match)") 
			@RequestParam(required = false) String colorName){
		log.info("Received request for find all");
		List<ContractorStockReportVo> found = contractorStockReportService.getStockReport(contractorName, designName, colorName);
		if (found != null && !found.isEmpty()) {
			log.info("Record found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record saved", found, metadataGenerator.getMetadata(found)));
		} else {
			log.error("Record not found :: {}", found);
			return ResponseEntity
					.ok(ApiResponseVoWrapper.success("Record not saved", found, metadataGenerator.getMetadata(found)));
		}
	}
}
