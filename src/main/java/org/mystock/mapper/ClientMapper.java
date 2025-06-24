package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.dto.ClientDto;
import org.mystock.vo.ClientVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ClientMapper {

	private final ModelMapper modelMapper;
	
	public ClientVo convert(ClientDto clientDto) {
		return modelMapper.map(clientDto, ClientVo.class);
	}
	
	public ClientDto convert(ClientVo clientVo) {
		return modelMapper.map(clientVo, ClientDto.class);
	}
	
}
