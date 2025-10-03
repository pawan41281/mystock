package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/v1/roles/")
@AllArgsConstructor
@Tag(name = "Role Operations", description = "CRUD Operations for role record")
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")
public class RoleController {

	private final RoleService roleService;
	private final MetadataGenerator metadataGenerator;

	@GetMapping
	@Operation(summary = "Get All", description = "Get all roles")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<List<RoleVo>>> getAll() {
		log.info("Received request for find all");
		List<RoleVo> found = roleService.getAll();
		log.info("Record found :: {}", found);
		return ResponseEntity.status(201)
				.body(ApiResponseVoWrapper.success("Record fetched", found, metadataGenerator.getMetadata(found)));
	}

}
