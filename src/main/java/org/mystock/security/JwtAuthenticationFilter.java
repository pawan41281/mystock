package org.mystock.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

// Execute Before Executing Spring Security Filters
// Validate the JWT Token and Provides user details to Spring Security for Authentication
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;

	private final UserDetailsService userDetailsService;
	
//	private final RestTemplate restTemplate;

//	public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
//		this.jwtTokenProvider = jwtTokenProvider;
//		this.userDetailsService = userDetailsService;
//	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Get JWT token from HTTP request
		String token = getTokenFromRequest(request);

		// Validate Token
		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
			// get username from token
			String username = jwtTokenProvider.getUsername(token);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}

		filterChain.doFilter(request, response);
	}
	
	
//	@Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Authorization", authHeader);
//            HttpEntity<String> entity = new HttpEntity<>(headers);
//
//            try {
//                ResponseEntity<String> res = restTemplate.exchange(
//                        "http://localhost:9999/api/auth/validate",
//                        HttpMethod.POST,
//                        entity,
//                        String.class
//                );
//
//                if (res.getStatusCode() == HttpStatus.OK) {
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//            } catch (Exception ex) {
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                return;
//            }
//        }
//
//        //response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        filterChain.doFilter(request, response);
//    }

	private String getTokenFromRequest(HttpServletRequest request) {
		
		String authHeader = request.getHeader("Authorization");

		if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7, authHeader.length());
		}

		return null;
	}
}