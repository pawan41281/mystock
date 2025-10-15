package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.security.JwtTokenProvider;
import org.mystock.service.AuthService;
import org.mystock.service.UserService;
import org.mystock.vo.UserVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
@Tag(name = "User Operations", description = "Endpoints for managing users — create, update, find, and fetch current user details.")
@SecurityRequirement(name = "Bearer Authentication")
@Slf4j
public class UserController {

	private final UserService userService;
	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;

	// ----------------------------
	// CREATE USER
	// ----------------------------
	@Operation(
			summary = "Create a new user",
			description = "Creates a new user record. Only accessible to users with ADMIN role."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "409", description = "User already exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<UserVo>> save(@Valid @RequestBody UserVo userVo)
			throws UnableToProcessException, ResourceAlreadyExistsException {

		try {
			if (userVo.getId() != null && !userVo.getId().equals(0L)){
				userVo = userService.update(userVo);
				if (userVo != null) userVo.setPassword("********");
				return ResponseEntity.ok(ApiResponseVoWrapper.success("User updated successfully", userVo, null));
			}
			else{
				userVo.setId(null);
				userVo = userService.save(userVo);
				if (userVo != null) userVo.setPassword("********");
				return ResponseEntity.status(201)
						.body(ApiResponseVoWrapper.success("User created successfully", userVo, null));
			}
		} catch (Exception e) {
			return ResponseEntity.status(200).body(ApiResponseVoWrapper.failure(e.getMessage(), userVo, null));
		}
	}

	// ----------------------------
	// UPDATE USER
	// ----------------------------
	@Operation(
			summary = "Update an existing user",
			description = "Updates an existing user’s details. Accessible to ADMIN or USER roles."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User updated successfully"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PatchMapping
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<UserVo>> update(@Valid @RequestBody UserVo userVo)
			throws ResourceNotFoundException {

		try {
			userVo = userService.update(userVo);
			if (userVo != null) userVo.setPassword("********");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("User updated successfully", userVo, null));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.failure(e.getMessage(), userVo, null));
		}
	}

	// ----------------------------
	// FIND BY USER ID
	// ----------------------------
	@Operation(
			summary = "Find user by userId",
			description = "Fetches user details using their unique userId. Accessible only to ADMIN role."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User found"),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@GetMapping("/userid/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<UserVo>> findByUserId(
			@Parameter(name = "userId", description = "Unique user ID", in = ParameterIn.QUERY, required = true)
			@RequestParam String userId) throws ResourceNotFoundException {

		try {
			UserVo userVo = userService.findByUserId(userId);
			if (userVo != null) userVo.setPassword("********");
			String message = userVo != null ? "User exists" : "User not exists";
			return ResponseEntity.ok(ApiResponseVoWrapper.success(message, userVo, null));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.failure(e.getMessage(), null, null));
		}
	}

	// ----------------------------
	// UPDATE STATUS BY USER ID
	// ----------------------------
	@Operation(
			summary = "Update status of exiting user",
			description = "Update user status. Only accessible to users with ADMIN role."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Status updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid input data"),
			@ApiResponse(responseCode = "500", description = "Internal server error")
	})
	@PatchMapping("/{userid}/{status}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<UserVo>> updateStatus(@PathVariable(name = "userid") Long id, @PathVariable(name="status") boolean status)
			throws UnableToProcessException, ResourceNotFoundException {
		try {
			UserVo userVo = userService.findById(id);
			userVo.setLocked(status);
			userService.updateStatus(id,status);
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("User status updated successfully", userVo, null));
		} catch (Exception e) {
			return ResponseEntity.status(200).body(ApiResponseVoWrapper.failure(e.getMessage(), null, null));
		}
	}

	// ----------------------------
	// FIND USERS BY FILTERS
	// ----------------------------
	@Operation(
			summary = "Find users",
			description = "Fetches all users matching optional parameters like userId, email, or mobile. Accessible only to ADMIN role."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Users fetched successfully")
	})
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<List<UserVo>>> find(
			@Parameter(description = "User ID to filter by", in = ParameterIn.QUERY) @RequestParam(required = false) String userId,
			@Parameter(description = "Email to filter by", in = ParameterIn.QUERY) @RequestParam(required = false) String email,
			@Parameter(description = "Mobile number to filter by", in = ParameterIn.QUERY) @RequestParam(required = false) String mobile
	) throws UnableToProcessException {

		try {
			List<UserVo> list = userService.find(userId, email, mobile);
			if(!list.isEmpty()){
				List<UserVo> tmpList = new ArrayList<>();
				list.stream().forEach(obj -> {
					obj.setPassword("********");
					tmpList.add(obj);
				});
				list.clear();
				boolean b = list.addAll(tmpList);
			}
			String message = !list.isEmpty() ? "Users exist" : "No users found";
			Map<String, String> metadata = new HashMap<>();
			metadata.put("recordCount", String.valueOf(list.size()));
			return ResponseEntity.ok(ApiResponseVoWrapper.success(message, list, metadata));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.failure(e.getMessage(), null, null));
		}
	}

	// ----------------------------
	// GET USER FROM TOKEN
	// ----------------------------
	@Operation(
			summary = "Get user by token",
			description = "Fetches user details from JWT token provided in Authorization header."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User details fetched successfully"),
			@ApiResponse(responseCode = "401", description = "Invalid or expired token")
	})
	@PostMapping("/user")
	public ResponseEntity<ApiResponseVo<UserVo>> getUser(
			@Parameter(description = "JWT Authorization token (format: Bearer <token>)", required = true)
			@RequestHeader("Authorization") String token) {
		try {
			String jwt = token.replace("Bearer ", "").trim();
			UserVo userVo = authService.getUserFromToken(jwt);
			if (userVo != null) userVo.setPassword("********");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("User details fetched successfully", userVo, null));
		} catch (Exception e) {
			log.error("Failed to get user from token: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponseVoWrapper.failure("Invalid or expired token", null, null));
		}
	}

	// ----------------------------
	// GET CURRENT USER (SECURITY CONTEXT)
	// ----------------------------
	@Operation(
			summary = "Get current logged-in user",
			description = "Fetches details of the currently logged-in user using Spring Security context."
	)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User details fetched successfully"),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "401", description = "Invalid or expired token")
	})
	@PostMapping("/currentuser")
	public ResponseEntity<ApiResponseVo<?>> getCurrentUser() {
		try {
			UserVo userVo = authService.getCurrentUser();
			if (userVo != null) userVo.setPassword("********");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("User details fetched successfully", userVo, null));
		} catch (ResourceNotFoundException e) {
			log.error("User not found: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(ApiResponseVoWrapper.failure(e.getMessage(), null, null));
		} catch (Exception e) {
			log.error("Failed to fetch current user: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(ApiResponseVoWrapper.failure("Invalid or expired token", null, null));
		}
	}
}