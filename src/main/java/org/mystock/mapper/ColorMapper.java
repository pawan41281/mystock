package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ColorEntity;
import org.mystock.vo.ColorVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ColorMapper {

	private final ModelMapper modelMapper;

	public ColorVo toVo(ColorEntity colorEntity) {

		return modelMapper.map(colorEntity, ColorVo.class);
	}

	public ColorEntity toEntity(ColorVo colorVo) {
		return modelMapper.map(colorVo, ColorEntity.class);
	}

}
