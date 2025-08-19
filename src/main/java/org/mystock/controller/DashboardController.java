package org.mystock.controller;

import java.util.List;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ClientChallanService;
import org.mystock.service.ContractorChallanService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ClientChallanVo;
import org.mystock.vo.ContractorChallanVo;
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
@RequestMapping("/v2/dashboard")
@AllArgsConstructor
@Tag(name = "Dashboard Operations", description = "Dashboard Operations")
@Slf4j
public class DashboardController {

	private final ClientChallanService clientChallanService;
	private final ContractorChallanService contractorChallanService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping("/clients/challans/challantype")
	@Operation(summary = "Get today's client challans", description = "Challan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ClientChallanVo>>> getRecentClientChallans(
			@Parameter(description = "Challan Type :: I - Issue, R - Received", name = "challantype")
			@RequestParam(value = "challantype", required = false) String challanType) {
		log.info("Received request for getRecentIssuedChallans with challantype :: {} and recordcount :: {}", challanType);
		
		List<ClientChallanVo> found = clientChallanService.getRecentChallans(challanType);
		log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}

	@GetMapping("/contractors/challans/challantype")
	@Operation(summary = "Get today's contractor challans", description = "Challan Type :: I - Issue, R - Received")
	public ResponseEntity<ApiResponseVo<List<ContractorChallanVo>>> getRecentContractorChallans(
			@Parameter(description = "Challan Type :: I - Issue, R - Received", name = "challantype")
			@RequestParam(value = "challantype", required = false) String challanType) {
		log.info("Received request for getRecentIssuedChallans with challantype :: {} and recordcount :: {}", challanType);
		
		List<ContractorChallanVo> found = contractorChallanService.getRecentChallans(challanType);
		log.info("Record {}", found != null && !found.isEmpty() ? "found" : "not found");

		return ResponseEntity
				.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}
	

}
