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

    @PostMapping("/logout")
    @Operation(summary = "Logout Operation", description = "Invalidate tokens and clear refresh token cookie")
    public ResponseEntity<ApiResponseVo<String>> logout(@RequestHeader(value = "Authorization", required = false) String token,
                                                        HttpServletResponse response) {
        log.info("Received logout request");

        try {
            // ✅ Optionally blacklist access token (if implemented in AuthService)
            if (token != null && token.startsWith("Bearer ")) {
                String jwt = token.substring(7);
                authService.invalidateToken(jwt);
            }

            // ✅ Clear the refresh token cookie
            ResponseCookie clearCookie = ResponseCookie.from("refreshToken", "")
                    .httpOnly(true)
                    //.secure(true)
                    .path("/v1/auth")
                    .maxAge(0) // expire immediately
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