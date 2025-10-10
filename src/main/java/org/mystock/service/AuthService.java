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

	public String login(LoginVo loginVo) throws InvalidCredentialsException;

	public JwtAuthResponse refreshToken(String refreshToken);

	public boolean validateToken(String token);

	public void invalidateToken(String token);

	public boolean existsByUserId(String userId) throws ResourceNotFoundException;

	public boolean existsByEmail(String email) throws ResourceNotFoundException;

	public SignupRequestVo save(SignupRequestVo signUpRequestVo)
			throws UnableToProcessException, ResourceAlreadyExistsException;

	public Authentication authenticate(LoginVo loginVo) throws InvalidCredentialsException;

	public UserVo getUserFromToken(String token) throws ResourceNotFoundException;

	public UserVo getCurrentUser() throws ResourceNotFoundException;
}