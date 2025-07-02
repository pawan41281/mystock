package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ClientEntity;
import org.mystock.vo.ClientVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientMapper {

	private final ModelMapper modelMapper;

	public ClientVo convert(ClientEntity clientEntity) {
		return modelMapper.map(clientEntity, ClientVo.class);
	}

	public ClientEntity convert(ClientVo clientVo) {
		return modelMapper.map(clientVo, ClientEntity.class);
	}

}
