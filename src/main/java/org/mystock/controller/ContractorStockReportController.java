package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(
		name = "Contractor Stock Reports",
		description = "Endpoints to generate and filter contractor stock balance reports, with optional filters for contractor, design, and color."
)
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorStockReportController {

	private final ContractorStockReportService contractorStockReportService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping
	@Operation(
			summary = "Get Contractor Stock Report",
			description = """
            Retrieves a contractor stock balance report.  
            - If any filter (contractorName, designName, colorName) is provided, returns all matching records.  
            - If no filter is provided, returns **only non-zero balances**.
            """,
			parameters = {
					@Parameter(
							name = "contractorName",
							description = "Filter by contractor name (supports partial match)",
							example = "Ravi Traders"
					),
					@Parameter(
							name = "designName",
							description = "Filter by design name (supports partial match)",
							example = "Classic Marble"
					),
					@Parameter(
							name = "colorName",
							description = "Filter by color name (supports partial match)",
							example = "Ivory White"
					)
			},
			responses = {
					@ApiResponse(
							responseCode = "200",
							description = "Report fetched successfully",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					),
					@ApiResponse(
							responseCode = "400",
							description = "Invalid request parameters",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					),
					@ApiResponse(
							responseCode = "401",
							description = "Unauthorized - missing or invalid token",
							content = @Content
					),
					@ApiResponse(
							responseCode = "500",
							description = "Internal server error",
							content = @Content(schema = @Schema(implementation = ApiResponseVo.class))
					)
			}
	)
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorStockReportVo>>> getBalanceReport(
			@RequestParam(required = false) String contractorName,
			@RequestParam(required = false) String designName,
			@RequestParam(required = false) String colorName) {

		log.info("Received request for contractor stock report: contractor={}, design={}, color={}",
				contractorName, designName, colorName);

		List<ContractorStockReportVo> found = Collections.emptyList();

		// Logic: if any filter is passed, fetch filtered report; else fetch non-zero report
		if ((contractorName != null && !contractorName.isEmpty()) ||
				(designName != null && !designName.isEmpty()) ||
				(colorName != null && !colorName.isEmpty())) {

			found = contractorStockReportService.getStockReport(contractorName, designName, colorName);
		} else {
			found = contractorStockReportService.getNonZeroStockReport(contractorName, designName, colorName);
		}

		if (found != null && !found.isEmpty()) {
			log.info("Report records found: {}", found.size());
			return ResponseEntity.ok(ApiResponseVoWrapper.success(
					"Record(s) found", found, metadataGenerator.getMetadata(found)));
		} else {
			log.warn("No records found for given filters");
			return ResponseEntity.ok(ApiResponseVoWrapper.success(
					"Record not found", found, metadataGenerator.getMetadata(found)));
		}
	}

}