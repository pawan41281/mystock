package org.mystock.controller;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/download")
@AllArgsConstructor
@Slf4j
@Tag(name = "Download", description = "Endpoints for download reports")
@SecurityRequirement(name = "Bearer Authentication")
public class DownloadReportController {

	private final DesignStockReportService designStockReportService;
	private final ContractorStockReportService contractorStockReportService;


	@GetMapping("/designs/colors/stock/report")
	@Operation(summary = "Get Design and Color wise Stock balance Report", description = "Get Design and Color wise Stock balance Report")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Report fetched successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<StreamingResponseBody> getReport(
			@Parameter(description = "Filter by design name (partial match)") @RequestParam(required = false) String designName,
			@Parameter(description = "Filter by color name (partial match)") @RequestParam(required = false) String colorName,
			@Parameter(description = "Page Size") @RequestParam(required = false, defaultValue = "10") Integer pageSize){
		log.info("Received download request for design and color wise stock report");
		int count = designStockReportService.getDesignStockCount(designName, colorName);
		log.info("Record found :: {}",count);
		if(count>0){
			ObjectMapper mapper = new ObjectMapper();
			StreamingResponseBody stream = outputStream -> {
				int pagecount=0;
				int totalpages = count/pageSize;
				outputStream.write("[".getBytes(StandardCharsets.UTF_8)); // Start of JSON array
				while(pagecount<totalpages) {
					List<DesignStockReportVo> found = designStockReportService.getDesignStockReport(designName, colorName, pageSize, pagecount);
					StringBuilder response = new StringBuilder(mapper.writeValueAsString(found));
					response.deleteCharAt(0);//delete [ at first index
					response.deleteCharAt(response.indexOf("]"));//delete ] from last index
					outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));
					pagecount++;
					
					//add comma at last if more pages available
					if(pagecount<totalpages)
						outputStream.write(",".getBytes(StandardCharsets.UTF_8));
					
				}
				outputStream.write("]".getBytes(StandardCharsets.UTF_8)); // End JSON array
				outputStream.flush();
			};
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String fileName = "design-color-stock-report-" + LocalDate.now().format(formatter) + ".json";
			log.info("fileName :: {}",fileName);
			log.info("Headers: Content-Disposition={}, Content-Type={}",
					"attachment; filename=\"" + fileName + "\"",
					"application/json");
			return ResponseEntity
					.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
					.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
					.body(stream);
		}else{
			log.info("No records found for filters - designName: {}, colorName: {}", designName, colorName);
			return ResponseEntity.noContent().build();
		}
	}

	@GetMapping("/contractor/designs/colors/stock/report")
	@Operation(summary = "Get Design and Color wise Contractor Stock balance Report", description = "Get Design and Color wise Contractor Stock balance Report")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Report fetched successfully"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<StreamingResponseBody> getReport(
			@Parameter(description = "Filter by contractor name (partial match)")
			@RequestParam(required = false) String contractorName,
			@Parameter(description = "Filter by design name (partial match)")
			@RequestParam(required = false) String designName,
			@Parameter(description = "Filter by color name (partial match)")
			@RequestParam(required = false) String colorName,
			@Parameter(description = "Page Size")
			@RequestParam(required = false, defaultValue = "100") Integer pageSize){
		log.info("Received download request for design and color wise contractor stock report");
		int count = contractorStockReportService.getStockCount(contractorName, designName, colorName);
		log.info("Record found :: {}",count);
		if(count>0){
			ObjectMapper mapper = new ObjectMapper();
			StreamingResponseBody stream = outputStream -> {
				int pagecount=0;
				int totalpages = count/pageSize;
				outputStream.write("[".getBytes(StandardCharsets.UTF_8)); // Start of JSON array
				while(pagecount<totalpages) {
					List<ContractorStockReportVo> found = contractorStockReportService.getStockReport(contractorName, designName, colorName, pageSize, pagecount);
					StringBuilder response = new StringBuilder(mapper.writeValueAsString(found));
					response.deleteCharAt(0);//delete [ at first index
					response.deleteCharAt(response.indexOf("]"));//delete ] from last index
					outputStream.write(response.toString().getBytes(StandardCharsets.UTF_8));
					pagecount++;
					
					//add comma at last if more pages available
					if(pagecount<totalpages)
						outputStream.write(",".getBytes(StandardCharsets.UTF_8));
					
				}
				outputStream.write("]".getBytes(StandardCharsets.UTF_8)); // End JSON array
				outputStream.flush();
			};
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
			String fileName = "contractor-design-color-stock-report-" + LocalDate.now().format(formatter) + ".json";
			log.info("fileName :: {}",fileName);
			log.info("Headers: Content-Disposition={}, Content-Type={}",
					"attachment; filename=\"" + fileName + "\"",
					"application/json");
			return ResponseEntity
					.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
					.header(HttpHeaders.CONTENT_TYPE, "application/octet-stream")
					.body(stream);
		}else{
			log.info("No records found for filters - designName: {}, colorName: {}", designName, colorName);
			return ResponseEntity.noContent().build();
		}
	}
}