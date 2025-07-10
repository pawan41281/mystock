package org.mystock.security;

import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;

public interface AuthService {
	public String login(LoginVo loginVo) throws ResourceNotFoundException;

	public boolean validateToken(String token);

	public boolean existsByUsername(String userName) throws ResourceNotFoundException;

	public boolean existsByEmail(String email) throws ResourceNotFoundException;

	public boolean save(SignupRequestVo signUpRequestVo)
			throws UnableToProcessException, ResourceAlreadyExistsException;
}