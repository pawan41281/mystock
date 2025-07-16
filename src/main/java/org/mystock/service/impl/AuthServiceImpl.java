package org.mystock.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.mystock.entity.RoleEntity;
import org.mystock.entity.UserEntity;
import org.mystock.exception.InvalidCredentialsException;
import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.mapper.UserMapper;
import org.mystock.repository.RoleRepository;
import org.mystock.repository.UserRepository;
import org.mystock.security.JwtAuthResponse;
import org.mystock.security.JwtTokenProvider;
import org.mystock.service.AuthService;
import org.mystock.vo.LoginVo;
import org.mystock.vo.SignupRequestVo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final AuthenticationManager authenticationManager;

	private final JwtTokenProvider jwtTokenProvider;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder encoder;

	private final UserMapper userMapper;

	@Override
	public String login(LoginVo loginVo) throws InvalidCredentialsException {
		try {
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
					loginVo.getUserName(), loginVo.getPassword());
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
//			return jwtTokenProvider.generateToken(authentication);
			return jwtTokenProvider.generateAccessToken(authentication);
		} catch (BadCredentialsException e) {
			throw new InvalidCredentialsException("Invalid username or password.");
		}
	}

	@Override
	public JwtAuthResponse refreshToken(String refreshToken) {
		if (!jwtTokenProvider.validateToken(refreshToken)) {
			throw new InvalidCredentialsException("Invalid or expired refresh token");
		}
		String username = jwtTokenProvider.getUsernameFromToken(refreshToken);

		// Rebuild authentication object manually (optional, based on your token
		// content)
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());

		String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
		String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(newAccessToken);
		jwtAuthResponse.setRefreshToken(newRefreshToken);
		return jwtAuthResponse;
	}

	@Override
	public boolean existsByUsername(String userName) throws ResourceNotFoundException {
		try {
			return userRepository.existsByUserName(userName);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Username not exists");
		}
	}

	@Override
	public boolean existsByEmail(String email) throws ResourceNotFoundException {
		try {
			return userRepository.existsByEmail(email);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Email not exists");
		}
	}

	@Override
	public SignupRequestVo save(SignupRequestVo signUpRequestVo)
			throws UnableToProcessException, ResourceAlreadyExistsException {

		if (existsByUsername(signUpRequestVo.getUsername())) {
			throw new ResourceAlreadyExistsException("Username is already exists");
		}

		if (existsByEmail(signUpRequestVo.getEmail())) {
			throw new ResourceAlreadyExistsException("Email is already exists");
		}

		try {

			// Create new user's account
			UserEntity user = new UserEntity(signUpRequestVo.getName(), signUpRequestVo.getUsername(),
					signUpRequestVo.getEmail(), signUpRequestVo.getMobile(),
					encoder.encode(signUpRequestVo.getPassword()), signUpRequestVo.isLocked());

			Set<String> strRoles = signUpRequestVo.getRoles();
			Set<RoleEntity> roles = resolveRoles(strRoles);
			user.setRoles(roles);
			UserEntity saved = userRepository.save(user);
			return saved.getId() != null ? userMapper.toSignupRequestVo(saved) : signUpRequestVo;
		} catch (Exception e) {
			throw new UnableToProcessException(e.getMessage());
		}
	}

	@Override
	public boolean validateToken(String token) {
		return jwtTokenProvider.validateToken(token);
	}

	private Set<RoleEntity> resolveRoles(Set<String> roleNames) {
		Set<RoleEntity> roles = new HashSet<>();
		if (roleNames != null && !roleNames.isEmpty()) {
			for (String role : roleNames) {
				RoleEntity userRole = roleRepository.findByNameIgnoreCase(role);
				if (userRole == null) {
					throw new ResourceNotFoundException("Role '" + role + "' not found.");
				}
				roles.add(userRole);
			}
		}
		else {//Assign default role
			RoleEntity userRole = roleRepository.findByNameIgnoreCase("ROLE_USER");
			if (userRole == null) {
				throw new ResourceNotFoundException("Default role not found.");
			}
			roles.add(userRole);
		}
		return roles;
	}
	
	@Override
	public Authentication authenticate(LoginVo loginVo) throws InvalidCredentialsException {
	    try {
	        UsernamePasswordAuthenticationToken authRequest =
	                new UsernamePasswordAuthenticationToken(loginVo.getUserName(), loginVo.getPassword());
	        return authenticationManager.authenticate(authRequest);
	    } catch (BadCredentialsException e) {
	        throw new InvalidCredentialsException("Invalid username or password.");
	    }
	}


}