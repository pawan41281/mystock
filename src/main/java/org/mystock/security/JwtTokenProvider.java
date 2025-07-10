package org.mystock.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	@Value("${app.jwt-secret}")
	private String jwtSecret;

	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}

	private Key getSigningKey() {
		return this.key;
	}

	// generate JWT token
	public String generateToken(Authentication authentication) {

		Date now = new Date();
		Date expiry = new Date(now.getTime() + jwtExpirationDate);

		return Jwts.builder().subject(authentication.getName()).claim("roles", authentication.getAuthorities())
				.issuedAt(now).expiration(expiry).signWith(getSigningKey()).compact();

	}

	// get username from JWT token
	public String getUsername(String token) {

		return Jwts.parser().verifyWith((SecretKey) getSigningKey()).build().parseSignedClaims(token).getPayload()
				.getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().verifyWith((SecretKey) key).build().parseSignedClaims(token);
			return true;
		} catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException
				| IllegalArgumentException ex) {
			log.warn("JWT validation failed: {}", ex.getMessage());
			return false;
		}
	}
}