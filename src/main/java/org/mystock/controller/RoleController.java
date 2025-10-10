package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.service.RoleService;
import org.mystock.util.MetadataGenerator;
import org.mystock.vo.RoleVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/roles")
@AllArgsConstructor
@Tag(name = "Role Operations", description = "Endpoints for fetching Role records")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class RoleController {

	private final RoleService roleService;
	private final MetadataGenerator metadataGenerator;

	/**
	 * Endpoint: /v1/roles
	 *
	 * Fetches all roles available in the system.
	 * Accessible by users with ADMIN or USER roles.
	 */
	@GetMapping
	@Operation(summary = "Get all roles", description = "Retrieve a list of all roles in the system.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Roles fetched successfully"),
			@ApiResponse(responseCode = "403", description = "Access denied"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<RoleVo>>> getAll() {
		log.info("Received request to fetch all roles");

		List<RoleVo> roles = roleService.getAll();
		log.info("Roles found: {}", roles != null ? roles.size() : 0);

		return ResponseEntity.ok(
				ApiResponseVoWrapper.success("Roles fetched successfully", roles, metadataGenerator.getMetadata(roles))
		);
	}
}