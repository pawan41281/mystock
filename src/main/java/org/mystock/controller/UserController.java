package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.service.UserService;
import org.mystock.vo.UserVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/users")
@Tag(name = "User Operations", description = "User operations")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

	private final UserService userService;

	// Create New User
	@PostMapping
	@Operation(summary = "Create User Operation", description = "Create new user")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<UserVo>> save(@Valid @RequestBody UserVo userVo)
			throws UnableToProcessException, ResourceAlreadyExistsException {

		try {
			if(userVo.getId()!=null && userVo.getId().equals(0L)) userVo.setId(null);
			userVo = userService.save(userVo);
			if (userVo != null)
				userVo.setPassword("********");
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("User created successfully", userVo, null));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.failure(e.getMessage(), userVo, null));
		}
	}

	// Update Existing User
	@PatchMapping
	@Operation(summary = "Update Operation", description = "Update existing user")
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<ApiResponseVo<UserVo>> update(@Valid @RequestBody UserVo userVo)
			throws ResourceNotFoundException {

		try {
			userVo = userService.update(userVo);
			if (userVo != null)
				userVo.setPassword("********");
			return ResponseEntity.status(201)
					.body(ApiResponseVoWrapper.success("User updated successfully", userVo, null));
		} catch (Exception e) {
			return ResponseEntity.status(500).body(ApiResponseVoWrapper.failure(e.getMessage(), userVo, null));
		}

	}

	// Find Existing User
	@GetMapping("/userid/")
	@Operation(summary = "Find Operation", description = "Find existing user by userId")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<UserVo>> findByUserId(@RequestParam String userId)
			throws ResourceNotFoundException {

		try {
			UserVo userVo = userService.findByUserId(userId);
			if (userVo != null)
				userVo.setPassword("********");
			String message = userVo != null ? "User exist" : "User not exists";
			return ResponseEntity.status(201).body(ApiResponseVoWrapper.success(message, userVo, null));
		} catch (Exception e) {
			return ResponseEntity.status(201).body(ApiResponseVoWrapper.failure(e.getMessage(), null, null));
		}

	}

	// Find Existing User
	@GetMapping("/email/")
	@Operation(summary = "Find Operation", description = "Find existing user by email")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<UserVo>> findByEmail(@RequestParam String email)
			throws ResourceNotFoundException {

		try {
			UserVo userVo = userService.findByEmail(email);
			if (userVo != null)
				userVo.setPassword("********");
			String message = userVo != null ? "User exist" : "User not exists";
			return ResponseEntity.status(201).body(ApiResponseVoWrapper.success(message, userVo, null));
		} catch (Exception e) {
			return ResponseEntity.status(201).body(ApiResponseVoWrapper.failure(e.getMessage(), null, null));
		}

	}

	// Find Existing Users based on multiple parameters
	@GetMapping
	@Operation(summary = "Find Operation", description = "Find all existing usesr based on multiple parameters")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponseVo<List<UserVo>>> find(
			@RequestParam(required = false) String userId,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String mobile
	)throws UnableToProcessException {

		List<UserVo> list = null;
		try {
			list = userService.find(userId, email, mobile);
			String message = !list.isEmpty() ? "Users exist" : "Users not exists";
			Map<String, String> metadata = new HashMap<>();
			metadata.put("recordcount", String.valueOf(list.size()));
			return ResponseEntity.status(201).body(ApiResponseVoWrapper.success(message, list, metadata));
		} catch (Exception e) {
			return ResponseEntity.status(201).body(ApiResponseVoWrapper.failure(e.getMessage(), list, null));
		}

	}

}