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
@Tag(name = "Report - Design Stock", description = "Endpoints for fetching design and color-wise stock reports")
@SecurityRequirement(name = "Bearer Authentication")
public class DesignStockReportController {

	private final DesignStockReportService designStockReportService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping
	@Operation(
			summary = "Get Design and Color-wise Stock Balance",
			description = """
                    Fetches stock balance grouped by design and color.
                    If no filter is provided, returns only records with non-zero balance.
                    You can optionally filter by design name or color name (partial matches allowed).
                    """
	)
	@ApiResponses({
			@ApiResponse(responseCode = "200", description = "Report fetched successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid filter parameter provided"),
			@ApiResponse(responseCode = "500", description = "Internal server error occurred")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<DesignStockReportVo>>> getBalanceReport(
			@Parameter(description = "Filter by design name (partial match)", example = "Floral")
			@RequestParam(required = false) String designName,

			@Parameter(description = "Filter by color name (partial match)", example = "Blue")
			@RequestParam(required = false) String colorName) {

		log.info("Received request for design and color-wise stock report with designName='{}', colorName='{}'",
				designName, colorName);

		List<DesignStockReportVo> found = Collections.emptyList();

		if ((designName != null && !designName.isEmpty()) || (colorName != null && !colorName.isEmpty())) {
			found = designStockReportService.getDesignStockReport(designName, colorName);
		} else {
			found = designStockReportService.getDesignStockNonZeroReport(designName, colorName);
		}

		String message = (found != null && !found.isEmpty()) ? "Record found" : "Record not found";
		log.info(message);

		return ResponseEntity.ok(
				ApiResponseVoWrapper.success(message, found, metadataGenerator.getMetadata(found))
		);
	}

	// Uncomment and document if you decide to re-enable the non-zero endpoint later
    /*
    @GetMapping("/nonzero")
    @Operation(
            summary = "Get Non-Zero Stock Balance Report",
            description = "Fetches only those design and color combinations that have a non-zero stock balance."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Non-zero stock report fetched successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ApiResponseVo<List<DesignStockReportVo>>> getNonZeroBalanceReport(
            @Parameter(description = "Filter by design name (partial match)")
            @RequestParam(required = false) String designName,

            @Parameter(description = "Filter by color name (partial match)")
            @RequestParam(required = false) String colorName) {

        log.info("Received request for non-zero design stock report :: designName='{}', colorName='{}'", designName, colorName);

        List<DesignStockReportVo> found = designStockReportService.getDesignStockNonZeroReport(designName, colorName);
        String message = (found != null && !found.isEmpty()) ? "Record found" : "Record not found";
        log.info(message);

        return ResponseEntity.ok(
                ApiResponseVoWrapper.success(message, found, metadataGenerator.getMetadata(found))
        );
    }
    */
}