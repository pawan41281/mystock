package org.mystock.service;

import org.mystock.exception.InvalidCredentialsException;
import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.security.JwtAuthResponse;
import org.mystock.vo.LoginVo;
import org.mystock.vo.SignupRequestVo;
import org.mystock.vo.UserVo;
import org.springframework.security.core.Authentication;

public interface AuthService {

	String login(LoginVo loginVo) throws InvalidCredentialsException;

	JwtAuthResponse refreshToken(String refreshToken);

	boolean validateToken(String token);

	void invalidateToken(String token);

	boolean existsByUserId(String userId) throws ResourceNotFoundException;

	boolean existsByEmail(String email) throws ResourceNotFoundException;

	SignupRequestVo save(SignupRequestVo signUpRequestVo)
			throws UnableToProcessException, ResourceAlreadyExistsException;

	Authentication authenticate(LoginVo loginVo) throws InvalidCredentialsException;

	UserVo getUserFromToken(String token) throws ResourceNotFoundException;

	UserVo getCurrentUser() throws ResourceNotFoundException;
}