package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.security.JwtAuthResponse;
import org.mystock.security.JwtTokenProvider;
import org.mystock.service.AuthService;
import org.mystock.vo.LoginVo;
import org.mystock.vo.RefreshTokenRequestVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/auth")
@Tag(name = "Auth Management", description = "Authority operations")
@Slf4j
public class AuthController {

	private final AuthService authService;
	private final JwtTokenProvider jwtTokenProvider;

//	@PostMapping("/login")
//	@Operation(summary = "Login Operation", description = "Generate the access token")
//	public ResponseEntity<ApiResponseVo<?>> login(@RequestBody LoginVo loginVo) throws ResourceNotFoundException {
//		log.info("Received request for login :: {}", loginVo.getUserId());
//		try {
//			String token = authService.login(loginVo);
//			log.info("Token has been generated :: {}", token);
//			JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
//			jwtAuthResponse.setAccessToken(token);
//			return ResponseEntity.status(HttpStatus.OK).body(ApiResponseVoWrapper.success(null, jwtAuthResponse, null));
//		} catch (Exception e) {
//			log.error("Token not generated :: {}", e.getMessage());
//			return ResponseEntity.status(HttpStatus.OK)
//					.body(ApiResponseVoWrapper.failure(e.getMessage(), loginVo, null));
//		}
//	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponseVo<?>> login(@RequestBody LoginVo loginVo, HttpServletResponse response)
			throws ResourceNotFoundException {
		log.info("Received request for login :: {}", loginVo.getUserId());
		try {
			Authentication authentication = authService.authenticate(loginVo);

			String accessToken = jwtTokenProvider.generateAccessToken(authentication);
			String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

			// Set HttpOnly cookie for refresh token
			ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken).httpOnly(true)
//	                .secure(true) // only over HTTPS
					.path("/v1/auth") // limit scope
					.maxAge(7 * 24 * 60 * 60) // 7 days
					.sameSite("Strict").build();

			response.addHeader("Set-Cookie", refreshCookie.toString());

			JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
			jwtAuthResponse.setAccessToken(accessToken);
			jwtAuthResponse.setRefreshToken("Set in HttpOnly Cookie");

			return ResponseEntity.ok(ApiResponseVoWrapper.success(null, jwtAuthResponse, null));
		} catch (Exception e) {
			log.error("Token not generated :: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK)
					.body(ApiResponseVoWrapper.failure(e.getMessage(), loginVo, null));
		}
	}

	@PostMapping("/refresh-token")
	@Operation(summary = "Refresh Token", description = "Generate new access token using refresh token")
	public ResponseEntity<ApiResponseVo<JwtAuthResponse>> refreshToken(@RequestBody RefreshTokenRequestVo request) {
		log.info("Received request for refresh token");
		try {
			JwtAuthResponse jwtAuthResponse = authService.refreshToken(request.getRefreshToken());
			return ResponseEntity.status(HttpStatus.OK)
					.body(ApiResponseVoWrapper.success("Token refreshed", jwtAuthResponse, null));
		} catch (Exception e) {
			log.error("Refresh failed: {}", e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(ApiResponseVoWrapper.failure(e.getMessage(), null, null));
		}
	}

//	@PostMapping("/signup")
//	@Operation(summary = "Signup Operation", description = "Create new user")
//	public ResponseEntity<ApiResponseVo<SignupRequestVo>> registerUser(
//			@Valid @RequestBody SignupRequestVo signUpRequestVo)
//			throws UnableToProcessException, ResourceAlreadyExistsException {
//		log.info("Received request for save :: {}", signUpRequestVo);
//		try {
//			authService.save(signUpRequestVo);
//			log.info("Record saved :: {}", signUpRequestVo);
//			return ResponseEntity.ok(ApiResponseVoWrapper.success("User created", signUpRequestVo, null));
//		} catch (Exception e) {
//			log.error("Record not saved :: {}", e.getMessage());
//			return ResponseEntity.status(HttpStatus.OK)
//					.body(ApiResponseVoWrapper.failure(e.getMessage(), signUpRequestVo, null));
//		}
//	}

	@PostMapping("/validate")
	@Operation(summary = "Validate Operation", description = "Validate the access token")
	public ResponseEntity<ApiResponseVo<String>> validate(@RequestHeader("Authorization") String token) {
		log.info("Received request for token validation :: {}", token);
		boolean isvalidtoken = authService.validateToken(token.replace("Bearer ", ""));
		if (isvalidtoken) {
			log.info("Token is valid");
			return ResponseEntity.status(HttpStatus.OK).body(ApiResponseVoWrapper.success("Valid token", token, null));
		} else {
			log.info("Token is invalid");
			return ResponseEntity.status(HttpStatus.OK)
					.body(ApiResponseVoWrapper.failure("Invalid token", token, null));
		}
	}

}