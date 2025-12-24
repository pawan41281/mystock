package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.ClientChallanService;
import org.mystock.service.ContractorChallanService;
import org.mystock.service.DashboardCardService;
import org.mystock.service.DashboardGraphService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.ClientChallanVo;
import org.mystock.vo.ContractorChallanVo;
import org.mystock.vo.DashboardCardVo;
import org.mystock.vo.DashboardGraphVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/dashboard")
@AllArgsConstructor
@Slf4j
@Tag(
		name = "Dashboard Operations",
		description = "Provides API endpoints for dashboard metrics, graphs, and recent challans for both clients and contractors."
)
@SecurityRequirement(name = "Bearer Authentication")
public class DashboardController {

	private final ClientChallanService clientChallanService;
	private final ContractorChallanService contractorChallanService;
	private final MetadataGenerator metadataGenerator;
	private final DashboardCardService dashboardCardService;
	private final DashboardGraphService dashboardGraphService;

	// --------------------------------------------------------------------------------------------
	// CLIENT CHALLANS
	// --------------------------------------------------------------------------------------------

	@GetMapping("/clients/challans/challantype")
	@Operation(
			summary = "Get Today's Client Challans",
			description = """
            Fetch today's client challans filtered by challan type.
            <br><b>Challan Type:</b>  
            - I → Issue  
            - R → Received
            """,
			parameters = {
					@Parameter(
							name = "challantype",
							description = "Type of challan to fetch (I = Issue, R = Received)",
							example = "I"
					)
			}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Records fetched successfully",
					content = @Content(schema = @Schema(implementation = ApiResponseVo.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid token"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ClientChallanVo>>> getRecentClientChallans(
			@RequestParam(value = "challantype", required = false) String challanType) {
		log.info("Received request for client challans, challantype :: {}", challanType);

		List<ClientChallanVo> found = clientChallanService.getRecentChallans(challanType);
		log.info("Record {}", (found != null && !found.isEmpty()) ? "found" : "not found");

		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}

	// --------------------------------------------------------------------------------------------
	// CONTRACTOR CHALLANS
	// --------------------------------------------------------------------------------------------

	@GetMapping("/contractors/challans/challantype")
	@Operation(
			summary = "Get Today's Contractor Challans",
			description = """
            Fetch today's contractor challans filtered by challan type.
            <br><b>Challan Type:</b>  
            - I → Issue  
            - R → Received
            """,
			parameters = {
					@Parameter(
							name = "challantype",
							description = "Type of challan to fetch (I = Issue, R = Received)",
							example = "R"
					)
			}
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Records fetched successfully",
					content = @Content(schema = @Schema(implementation = ApiResponseVo.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid token"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<ContractorChallanVo>>> getRecentContractorChallans(
			@RequestParam(value = "challantype", required = false) String challanType) {
		log.info("Received request for contractor challans, challantype :: {}", challanType);

		List<ContractorChallanVo> found = contractorChallanService.getRecentChallans(challanType);
		log.info("Record {}", (found != null && !found.isEmpty()) ? "found" : "not found");

		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}

	// --------------------------------------------------------------------------------------------
	// DASHBOARD CARDS
	// --------------------------------------------------------------------------------------------

	@GetMapping("/cards")
	@Operation(
			summary = "Get Dashboard Card Values",
			description = "Retrieves the summarized key metrics displayed on the dashboard such as total clients, contractors, stock, etc."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Dashboard data fetched successfully",
					content = @Content(schema = @Schema(implementation = ApiResponseVo.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid token"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<DashboardCardVo>> getDashboardCardValues() {
		log.info("Received request for getDashboardCardValues");

		DashboardCardVo vo = dashboardCardService.getDashboardCardValues();
		log.info("Record {}", vo != null ? "found" : "not found");

		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", vo, metadataGenerator.getMetadata(vo)));
	}

	// --------------------------------------------------------------------------------------------
	// DASHBOARD GRAPHS
	// --------------------------------------------------------------------------------------------

	@GetMapping("/graphs")
	@Operation(
			summary = "Get Dashboard Graph Data",
			description = "Fetches data for dashboard graphs, including challan trends, payments, and stock variations over time."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Graph data fetched successfully",
					content = @Content(schema = @Schema(implementation = ApiResponseVo.class))),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Missing or invalid token"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<DashboardGraphVo>> getDashboardGraphValues() {
		log.info("Received request for getDashboardGraphValues");

		DashboardGraphVo vo = dashboardGraphService.getDashboardGraphValues();
		log.info("Record {}", vo != null ? "found" : "not found");

		return ResponseEntity.ok(ApiResponseVoWrapper.success("Record fetched", vo, metadataGenerator.getMetadata(vo)));
	}

}