package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.dto.DesignDto;
import org.mystock.vo.DesignVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class DesignMapper {

	private final ModelMapper modelMapper;
	
	public DesignVo convert(DesignDto designDto) {
		return modelMapper.map(designDto, DesignVo.class);
	}
	
	public DesignDto convert(DesignVo designVo) {
		return modelMapper.map(designVo, DesignDto.class);
	}
	
}
