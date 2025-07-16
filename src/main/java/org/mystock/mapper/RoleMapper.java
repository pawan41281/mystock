package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.RoleEntity;
import org.mystock.vo.RoleVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RoleMapper {

	private final ModelMapper modelMapper;

	public RoleVo toVo(RoleEntity roleEntity) {
		return modelMapper.map(roleEntity, RoleVo.class);
	}

	public RoleEntity toEntity(RoleVo roleVo) {
		return modelMapper.map(roleVo, RoleEntity.class);
	}
	
}
