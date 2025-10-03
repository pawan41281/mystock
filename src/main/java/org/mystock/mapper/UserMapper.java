package org.mystock.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mystock.entity.RoleEntity;
import org.mystock.entity.UserEntity;
import org.mystock.vo.SignupRequestVo;
import org.mystock.vo.UserVo;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserMapper {

	private final ModelMapper modelMapper;

	public UserVo convert(UserEntity user) {
		return modelMapper.map(user, UserVo.class);
	}

	public SignupRequestVo toSignupRequestVo(UserEntity user) {
		SignupRequestVo signupRequestVo = new SignupRequestVo();
		signupRequestVo.setEmail(user.getEmail());
		signupRequestVo.setLocked(user.isLocked());
		signupRequestVo.setMobile(user.getMobile());
		signupRequestVo.setName(user.getName());
		signupRequestVo.setPassword(null);
		signupRequestVo.setUserId(user.getUserId());
		signupRequestVo.setRoles(user.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toSet()));
		return signupRequestVo;
	}

	public UserEntity convert(UserVo userVo) {
		return modelMapper.map(userVo, UserEntity.class);
	}

}
