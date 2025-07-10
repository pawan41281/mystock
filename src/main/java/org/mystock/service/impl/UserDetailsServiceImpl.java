package org.mystock.service.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.UserEntity;
import org.mystock.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

		UserEntity user = userRepository.findByUserName(userName);
		if (user == null)
			throw new UsernameNotFoundException("User not exists by UserName");

		Set<GrantedAuthority> authorities = user.getRoles().stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

		return new org.springframework.security.core.userdetails.User(userName, user.getPassword(), authorities);
	}

}