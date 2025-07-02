package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.DesignEntity;
import org.mystock.vo.DesignVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class DesignMapper {

	private final ModelMapper modelMapper;

	public DesignVo convert(DesignEntity designEntity) {
		return modelMapper.map(designEntity, DesignVo.class);
	}

	public DesignEntity convert(DesignVo designVo) {
		return modelMapper.map(designVo, DesignEntity.class);
	}

}
