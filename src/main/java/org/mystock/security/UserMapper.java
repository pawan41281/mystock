package org.mystock.security;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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
