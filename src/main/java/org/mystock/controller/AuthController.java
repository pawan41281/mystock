package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Authentication Management", description = "Handles user login, token management, validation, and logout operations.")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    // ------------------------------------------------------------
    // LOGIN
    // ------------------------------------------------------------
    @Operation(
            summary = "User Login",
            description = "Authenticate user using credentials and generate JWT access & refresh tokens.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful and tokens generated",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JwtAuthResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "500", description = "Internal server error",
                            content = @Content(mediaType = "application/json"))
            }
    )
    @PostMapping("/login")
    public ResponseEntity<ApiResponseVo<?>> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials for login",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginVo.class))
            )
            @RequestBody LoginVo loginVo,
            HttpServletResponse response
    ) throws ResourceNotFoundException {

        log.info("Received request for login :: {}", loginVo.getUserId());
        try {
            Authentication authentication = authService.authenticate(loginVo);

            String accessToken = jwtTokenProvider.generateAccessToken(authentication);
            String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

            // Set HttpOnly cookie for refresh token
            ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                    .httpOnly(true)
                    //.secure(true) // enable for HTTPS
                    .path("/v1/auth")
                    .maxAge(7 * 24 * 60 * 60) // 7 days
                    .sameSite("Strict")
                    .build();

            response.addHeader("Set-Cookie", refreshCookie.toString());

            JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
            jwtAuthResponse.setAccessToken(accessToken);
            jwtAuthResponse.setRefreshToken("Set in HttpOnly Cookie");

            return ResponseEntity.ok(ApiResponseVoWrapper.success("Login successful", jwtAuthResponse, null));
        } catch (Exception e) {
            log.error("Token not generated :: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseVoWrapper.failure("Login failed: " + e.getMessage(), loginVo, null));
        }
    }

    // ------------------------------------------------------------
    // REFRESH TOKEN
    // ------------------------------------------------------------
    @Operation(
            summary = "Refresh Access Token",
            description = "Generate a new access token using a valid refresh token.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
                            content = @Content(schema = @Schema(implementation = JwtAuthResponse.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token"),
                    @ApiResponse(responseCode = "500", description = "Internal server error")
            }
    )
    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponseVo<JwtAuthResponse>> refreshToken(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Refresh token request",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RefreshTokenRequestVo.class))
            )
            @RequestBody RefreshTokenRequestVo request) {

        log.info("Received request for refresh token");
        try {
            JwtAuthResponse jwtAuthResponse = authService.refreshToken(request.getRefreshToken());
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponseVoWrapper.success("Token refreshed", jwtAuthResponse, null));
        } catch (Exception e) {
            log.error("Refresh failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseVoWrapper.failure("Refresh failed: " + e.getMessage(), null, null));
        }
    }

    // ------------------------------------------------------------
    // VALIDATE TOKEN
    // ------------------------------------------------------------
    @Operation(
            summary = "Validate JWT Token",
            description = "Validate a JWT access token to check its authenticity and expiry.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token validation result",
                            content = @Content(schema = @Schema(implementation = String.class))),
                    @ApiResponse(responseCode = "401", description = "Invalid or expired token")
            }
    )
    @PostMapping("/validate")
    public ResponseEntity<ApiResponseVo<String>> validate(
            @Parameter(description = "Authorization header containing Bearer token", required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
            @RequestHeader("Authorization") String token) {

        log.info("Received request for token validation :: {}", token);
        boolean isValidToken = authService.validateToken(token.replace("Bearer ", ""));
        if (isValidToken) {
            log.info("Token is valid");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponseVoWrapper.success("Valid token", token, null));
        } else {
            log.info("Token is invalid");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponseVoWrapper.failure("Invalid token", token, null));
        }
    }

    // ------------------------------------------------------------
    // LOGOUT
    // ------------------------------------------------------------
    @Operation(
            summary = "Logout User",
            description = "Invalidate tokens and clear refresh token cookie.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Logout successful"),
                    @ApiResponse(responseCode = "500", description = "Logout failed due to server error")
            }
    )
    @PostMapping("/logout")
    public ResponseEntity<ApiResponseVo<String>> logout(
            @Parameter(description = "Authorization header with Bearer token", required = false)
            @RequestHeader(value = "Authorization", required = false) String token,
            HttpServletResponse response) {

        log.info("Received logout request");

        try {
            // Optionally blacklist access token (if implemented in AuthService)
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                authService.invalidateToken(jwt);
            }

            // Clear the refresh token cookie
            ResponseCookie clearCookie = ResponseCookie.from("refreshToken", "")
                    .httpOnly(true)
                    //.secure(true)
                    .path("/v1/auth")
                    .maxAge(0)
                    .sameSite("Strict")
                    .build();

            response.addHeader("Set-Cookie", clearCookie.toString());

            return ResponseEntity.ok(ApiResponseVoWrapper.success("Logout successful", null, null));
        } catch (Exception e) {
            log.error("Logout failed: {}", e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ApiResponseVoWrapper.failure("Logout failed: " + e.getMessage(), null, null));
        }
    }
}