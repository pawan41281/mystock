package org.mystock.service;

import org.mystock.exception.InvalidCredentialsException;
import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.vo.LoginVo;
import org.mystock.vo.SignupRequestVo;

public interface AuthService {

	public String login(LoginVo loginVo) throws InvalidCredentialsException;

	public boolean validateToken(String token);

	public boolean existsByUsername(String userName) throws ResourceNotFoundException;

	public boolean existsByEmail(String email) throws ResourceNotFoundException;

	public SignupRequestVo save(SignupRequestVo signUpRequestVo)
			throws UnableToProcessException, ResourceAlreadyExistsException;
}