package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.BusinessException;
import org.mystock.service.ContractorAccountStatementService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ContractorAccountStatementVo;
import org.mystock.vo.ContractorPaymentVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/v1/contractoraccountstatments")
@AllArgsConstructor
@Tag(name = "Contractor Account Statement Operations", description = "Contractor Account Statement Operations")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class ContractorAccountStatementController {

    private final ContractorAccountStatementService contractorAccountStatementService;
    private final MetadataGenerator metadataGenerator;


    @GetMapping
    @Operation(
            summary = "Get Contractor Account Statement by Filters",
            description = """
                    Fetches contractor account statement filtered by:
                    - Date Range (maximum 90 days)
                    - Contractor ID
                    """,
//            parameters = {
//                    @Parameter(name = "fromdate", description = "Start date of the account statement range (inclusive)", required = false),
//                    @Parameter(name = "todate", description = "End date of the account statement range (inclusive)", required = false),
//                    @Parameter(name = "contractorid", description = "Contractor ID for filtering", required = false)
//            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Filtered records fetched successfully",
                            content = @Content(schema = @Schema(implementation = ContractorPaymentVo.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid date range or parameters", content = @Content)
            }
    )
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ApiResponseVo<List<ContractorAccountStatementVo>>> find(
            @RequestParam(value = "fromdate", required = false) LocalDate fromDate,
            @RequestParam(value = "todate", required = false) LocalDate toDate,
            @RequestParam(value = "contractorid", required = false) Long contractorId) {

        log.info("Received request for find :: fromDate {}, toDate {}, contractorId {}",
                fromDate, toDate, contractorId);

        if (fromDate == null) {
            fromDate = LocalDate.now().withDayOfMonth(1); // first day of current month
        }

        if (toDate == null) {
            toDate = LocalDate.now();
        }

        if (toDate.isBefore(fromDate)) {
            throw new BusinessException("Invalid date range: 'To Date' must be greater than or equal to 'From Date'");
        }

        long days = ChronoUnit.DAYS.between(fromDate, toDate);
        if (days > 31) {
            throw new BusinessException("Date range cannot exceed 31 days");
        }

        List<ContractorAccountStatementVo> found = contractorAccountStatementService.findAll(fromDate, toDate, contractorId);
        log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

        return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
    }
}