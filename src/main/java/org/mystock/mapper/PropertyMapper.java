package org.mystock.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mystock.entity.PropertyEntity;
import org.mystock.vo.PropertyVo;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PropertyMapper {

	private final ModelMapper modelMapper;

	public PropertyVo toVo(PropertyEntity propertyEntity) {

		return modelMapper.map(propertyEntity, PropertyVo.class);
	}

	public PropertyEntity toEntity(PropertyVo propertyVo) {
		return modelMapper.map(propertyVo, PropertyEntity.class);
	}

}