package org.mystock.controller;

import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.security.AuthService;
import org.mystock.security.JwtAuthResponse;
import org.mystock.security.LoginVo;
import org.mystock.security.SignupRequestVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestController
@RequestMapping("/v2/auth")
@Tag(name = "Auth Management", description = "Authority operations")
@Slf4j
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	@Operation(summary = "Login Operation", description = "Generate the access token")
	public ResponseEntity<ApiResponseVo<?>> login(@RequestBody LoginVo loginVo) throws ResourceNotFoundException {
		log.info("Received request for login :: {}", loginVo.getUserName());
		try {
			String token = authService.login(loginVo);
			log.info("Token has been generated :: {}", token);
			JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
			jwtAuthResponse.setAccessToken(token);
			return ResponseEntity.ok(ApiResponseVoWrapper.success(null, jwtAuthResponse, null));
		} catch (Exception e) {
			log.error("Token not generated :: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
					.body(ApiResponseVoWrapper.failure(e.getMessage(), loginVo, null));
		}
	}

	@PostMapping("/signup")
	@Operation(summary = "Signup Operation", description = "Create new user")
	public ResponseEntity<ApiResponseVo<SignupRequestVo>> registerUser(
			@Valid @RequestBody SignupRequestVo signUpRequestVo)
			throws UnableToProcessException, ResourceAlreadyExistsException {
		log.info("Received request for save :: {}", signUpRequestVo);
		try {
			authService.save(signUpRequestVo);
			log.info("Record saved :: {}", signUpRequestVo);
			return ResponseEntity.ok(ApiResponseVoWrapper.success("User created", signUpRequestVo, null));
		} catch (Exception e) {
			log.error("Record not saved :: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
					.body(ApiResponseVoWrapper.failure(e.getMessage(), signUpRequestVo, null));
		}
	}

	@PostMapping("/validate")
	@Operation(summary = "Validate Operation", description = "Validate the access token")
	public ResponseEntity<ApiResponseVo<String>> validate(@RequestHeader("Authorization") String token) {
		log.info("Received request for token vaidateion :: {}", token);
		boolean isvalidtoken = authService.validateToken(token.replace("Bearer ", ""));
		if (isvalidtoken) {
			log.info("Token is valid");
			return ResponseEntity.ok(ApiResponseVoWrapper.success("Valid token", token, null));
		}
		else {
			log.info("Token is invalid");
			return ResponseEntity.ok(ApiResponseVoWrapper.failure("Invalid token", token, null));
		}
	}

}