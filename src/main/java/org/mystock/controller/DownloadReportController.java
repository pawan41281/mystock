package org.mystock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.service.ContractorStockReportService;
import org.mystock.service.DesignStockReportService;
import org.mystock.vo.ContractorStockReportVo;
import org.mystock.vo.DesignStockReportVo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/v1/download")
@AllArgsConstructor
@Slf4j
@Tag(name = "Download Reports", description = "Endpoints to download stock reports in JSON format")
@SecurityRequirement(name = "Bearer Authentication")
public class DownloadReportController {

	private final DesignStockReportService designStockReportService;
	private final ContractorStockReportService contractorStockReportService;

	/**
	 * Endpoint: /v1/download/designs/colors/stock/report
	 *
	 * Allows downloading the design & color-wise stock balance report as a JSON file.
	 */
	@GetMapping("/designs/colors/stock/report")
	@Operation(
			summary = "Download Design & Color-wise Stock Report",
			description = "Download the stock report showing balance quantities for each design and color. "
					+ "Supports optional filtering and paginated streaming for large datasets.",
			tags = {"Download Reports"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Report fetched and download initiated successfully"),
			@ApiResponse(responseCode = "204", description = "No records found for the given filters"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<StreamingResponseBody> downloadDesignColorStockReport(
			@Parameter(description = "Filter by design name (partial match)", example = "Floral")
			@RequestParam(required = false) String designName,

			@Parameter(description = "Filter by color name (partial match)", example = "Blue")
			@RequestParam(required = false) String colorName,

			@Parameter(description = "Number of records to fetch per page (for streaming large reports)", example = "100")
			@RequestParam(required = false, defaultValue = "10") Integer pageSize) {

		log.info("Received download request for Design & Color-wise stock report.");
		int count = designStockReportService.getDesignStockCount(designName, colorName);
		log.info("Records found: {}", count);

		if (count > 0) {
			ObjectMapper mapper = new ObjectMapper();
			StreamingResponseBody stream = outputStream -> {
				int pageCount = 0;
				int totalPages = count / pageSize;
				outputStream.write("[".getBytes(StandardCharsets.UTF_8)); // Start of JSON array

				while (pageCount < totalPages) {
					List<DesignStockReportVo> found = designStockReportService
							.getDesignStockReport(designName, colorName, pageSize, pageCount);
					StringBuilder response = new StringBuilder(mapper.writeValueAsString(found));
					response.deleteCharAt(0); // remove '['
					response.deleteCharAt(response.indexOf("]")); // remove ']'
					outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));
					pageCount++;

					// Add comma if more pages exist
					if (pageCount < totalPages)
						outputStream.write(",".getBytes(StandardCharsets.UTF_8));
				}

				outputStream.write("]".getBytes(StandardCharsets.UTF_8)); // End JSON array
				outputStream.flush();
			};

			String fileName = "design-color-stock-report-" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".json";
			log.info("File generated: {}", fileName);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
					.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
					.body(stream);
		} else {
			log.info("No records found for filters - designName: {}, colorName: {}", designName, colorName);
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * Endpoint: /v1/download/contractor/designs/colors/stock/report
	 *
	 * Allows downloading the contractor design & color-wise stock balance report as a JSON file.
	 */
	@GetMapping("/contractor/designs/colors/stock/report")
	@Operation(
			summary = "Download Contractor Design & Color-wise Stock Report",
			description = "Download the stock report showing balance quantities for each contractor, design, and color. "
					+ "Supports optional filtering and paginated streaming for large datasets.",
			tags = {"Download Reports"}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Report fetched and download initiated successfully"),
			@ApiResponse(responseCode = "204", description = "No records found for the given filters"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<StreamingResponseBody> downloadContractorStockReport(
			@Parameter(description = "Filter by contractor name (partial match)", example = "ABC Contractor")
			@RequestParam(required = false) String contractorName,

			@Parameter(description = "Filter by design name (partial match)", example = "Floral")
			@RequestParam(required = false) String designName,

			@Parameter(description = "Filter by color name (partial match)", example = "Blue")
			@RequestParam(required = false) String colorName,

			@Parameter(description = "Number of records to fetch per page (for streaming large reports)", example = "100")
			@RequestParam(required = false, defaultValue = "100") Integer pageSize) {

		log.info("Received download request for Contractor Design & Color-wise stock report.");
		int count = contractorStockReportService.getStockCount(contractorName, designName, colorName);
		log.info("Records found: {}", count);

		if (count > 0) {
			ObjectMapper mapper = new ObjectMapper();
			StreamingResponseBody stream = outputStream -> {
				int pageCount = 0;
				int totalPages = count / pageSize;
				outputStream.write("[".getBytes(StandardCharsets.UTF_8)); // Start of JSON array

				while (pageCount < totalPages) {
					List<ContractorStockReportVo> found = contractorStockReportService
							.getStockReport(contractorName, designName, colorName, pageSize, pageCount);
					StringBuilder response = new StringBuilder(mapper.writeValueAsString(found));
					response.deleteCharAt(0); // remove '['
					response.deleteCharAt(response.indexOf("]")); // remove ']'
					outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));
					pageCount++;

					if (pageCount < totalPages)
						outputStream.write(",".getBytes(StandardCharsets.UTF_8));
				}

				outputStream.write("]".getBytes(StandardCharsets.UTF_8)); // End JSON array
				outputStream.flush();
			};

			String fileName = "contractor-design-color-stock-report-" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ".json";
			log.info("File generated: {}", fileName);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
					.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
					.body(stream);
		} else {
			log.info("No records found for filters - contractorName: {}, designName: {}, colorName: {}",
					contractorName, designName, colorName);
			return ResponseEntity.noContent().build();
		}
	}
}