package org.mystock.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mystock.entity.MenuGroupEntity;
import org.mystock.vo.MenuGroupVo;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MenuGroupMapper {

	private final ModelMapper modelMapper;

	public MenuGroupVo convert(MenuGroupEntity menuGroup) {
		return modelMapper.map(menuGroup, MenuGroupVo.class);
	}

	public MenuGroupEntity convert(MenuGroupVo menuGroupVo) {
		return modelMapper.map(menuGroupVo, MenuGroupEntity.class);
	}

}