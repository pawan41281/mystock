package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import org.mystock.entity.RoleEntity;
import org.mystock.entity.UserEntity;
import org.mystock.exception.ResourceAlreadyExistsException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.mapper.UserMapper;
import org.mystock.repository.RoleRepository;
import org.mystock.repository.UserRepository;
import org.mystock.service.UserService;
import org.mystock.vo.RoleVo;
import org.mystock.vo.UserVo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	private final PasswordEncoder encoder;

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final UserMapper userMapper;

	@Override
	public UserVo save(UserVo userVo) throws UnableToProcessException, ResourceAlreadyExistsException {

		if (existsByUserId(userVo.getUserId())) {
			throw new ResourceAlreadyExistsException("UserId is already exists",userVo);
		}

		if (existsByEmail(userVo.getEmail())) {
			throw new ResourceAlreadyExistsException("Email is already exists",userVo);
		}

		try {

			// Create new user's account
			UserEntity user = new UserEntity(userVo.getName(), userVo.getUserId(), userVo.getEmail(),
					userVo.getMobile(), encoder.encode(userVo.getPassword()), userVo.getLocked());

			Set<RoleVo> voRoles = userVo.getRoles();
			Set<RoleEntity> entityRoles = new HashSet<>();

			if (voRoles != null && !voRoles.isEmpty()) {
				voRoles.forEach(role -> {

					RoleEntity roleEntity = roleRepository.findByNameIgnoreCase(role.getName());
					if (roleEntity == null)
						throw new ResourceNotFoundException(
								"Error: Role '" + role.getName() + "' not found in database.");

					entityRoles.add(roleEntity);
				});

			} else {

				RoleEntity roleEntity = roleRepository.findByNameIgnoreCase("ROLE_USER");
				if (roleEntity == null)
					throw new ResourceNotFoundException("Error: default role not found in database.");

				entityRoles.add(roleEntity);

			}

			user.setRoles(entityRoles);

			user = userRepository.save(user);

			return userMapper.convert(user);

		} catch (Exception e) {
			throw new UnableToProcessException(e.getMessage());
		}

	}

	@Override
	public UserVo update(UserVo userVo) throws UnableToProcessException, ResourceNotFoundException {

		// Update existing user's account
		UserEntity existingUser = null;
		UserEntity user = userMapper.convert(userVo);
		existingUser = userRepository.findById(userVo.getId()).get();

		if (existingUser == null) {
			throw new ResourceNotFoundException("UserId not exists");
		}

		if (user.getName() != null && !user.getName().isBlank())
			existingUser.setName(user.getName());

		if (user.getEmail() != null && !user.getEmail().isBlank())
			existingUser.setEmail(user.getEmail());

		if (user.getMobile() != null && !user.getMobile().isBlank())
			existingUser.setMobile(userVo.getMobile());

		if (user.getPassword() != null && !user.getPassword().isBlank())
			existingUser.setPassword(encoder.encode(user.getPassword()));

		existingUser.setLocked(user.isLocked());

		if (user.getRoles() != null && !user.getRoles().isEmpty()) {
			RoleEntity roleEntity = roleRepository.findByNameIgnoreCase(userVo.getRoles().stream().findFirst().orElseThrow().getName());
			Set<RoleEntity> roles = new HashSet<>();
			roles.add(roleEntity);
			existingUser.setRoles(roles);
		}

		try {
			UserEntity saved = userRepository.save(existingUser);
			userVo = userMapper.convert(saved);
			return userVo;
		} catch (Exception e) {
			throw new UnableToProcessException("User not updated");
		}
	}

	@Override
	public boolean updateStatus(Long id, boolean status) throws UnableToProcessException, ResourceNotFoundException {
		// check existing user's account
		if(userRepository.existsById(id)){
			userRepository.updateStatus(id,status);
		}else{
			throw new ResourceNotFoundException("User not exists");
		}
		return true;
	}

	@Override
	public boolean existsByUserId(String userName) throws ResourceNotFoundException {

		try {
			return userRepository.existsByUserId(userName);
		} catch (Exception e) {
			throw new ResourceNotFoundException("UserId not exists");
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
	public List<UserVo> findAll() throws UnableToProcessException {
		List<UserEntity> list = userRepository.findAll();

		if (!list.isEmpty())
			return list.stream().map(userMapper::convert).collect(Collectors.toList());
		else
			throw new ResourceNotFoundException("Record not exists");
	}

	@Override
	public UserVo findById(Long id) throws ResourceNotFoundException {
		Optional<UserEntity> user = userRepository.findById(id);
		if (user.isPresent())
			return userMapper.convert(user.get());
		else
			throw new ResourceNotFoundException("UserId not exists");
	}

	@Override
	public UserVo findByUserId(String userName) throws ResourceNotFoundException {
		UserEntity user = userRepository.findByUserId(userName);
		if (user != null)
			return userMapper.convert(user);
		else
			throw new ResourceNotFoundException("UserId not exists");
	}

	@Override
	public UserVo findByEmail(String email) throws ResourceNotFoundException {
		UserEntity user = userRepository.findByEmail(email);
		if (user != null)
			return userMapper.convert(user);
		else
			throw new ResourceNotFoundException("Email not exists");
	}

	@Override
	public List<UserVo> find(String userName, String email, String mobile)
			throws ResourceNotFoundException {
		List<UserEntity> list = userRepository.find(userName, email, mobile);
		if (list != null && !list.isEmpty())
			return list.stream().map(userMapper::convert).collect(Collectors.toList());
		else
			throw new ResourceNotFoundException("Record not exists");
	}

}