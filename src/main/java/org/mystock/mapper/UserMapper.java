package org.mystock.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.mystock.entity.RoleEntity;
import org.mystock.entity.UserEntity;
import org.mystock.vo.SignupRequestVo;
import org.mystock.vo.UserVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class UserMapper {

	private final ModelMapper modelMapper;

	public UserVo convert(UserEntity user) {
		UserVo userVo = modelMapper.map(user, UserVo.class);
		return userVo;
	}

	public SignupRequestVo toSignupRequestVo(UserEntity user) {
		SignupRequestVo signupRequestVo = new SignupRequestVo();
		signupRequestVo.setEmail(user.getEmail());
		signupRequestVo.setLocked(user.isLocked());
		signupRequestVo.setMobile(user.getMobile());
		signupRequestVo.setName(user.getName());
		signupRequestVo.setPassword(null);
		signupRequestVo.setUsername(user.getUserName());
		signupRequestVo.setRoles(user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet()));
		return signupRequestVo;
	}

	public UserEntity convert(UserVo userVo) {
		UserEntity user = modelMapper.map(userVo, UserEntity.class);
		return user;
	}

	public List<UserVo> convertToUserVoList(List<UserEntity> userList) {
		List<UserVo> list = new ArrayList<>();
		userList.parallelStream().forEach(vo -> {
			list.add(convert(vo));
		});
		return list;
	}

	public List<UserEntity> convertToUserList(List<UserVo> userVoList) {
		List<UserEntity> list = new ArrayList<>();
		userVoList.parallelStream().forEach(vo -> {
			list.add(convert(vo));
		});
		return list;
	}

}
