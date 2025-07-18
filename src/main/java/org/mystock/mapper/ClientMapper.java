package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ClientEntity;
import org.mystock.vo.ClientVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class ClientMapper {

	private final ModelMapper modelMapper;

	public ClientVo toVo(ClientEntity clientEntity) {
		log.info("ClientEntity to ClientVo mapping :: start");
		ClientVo vo = modelMapper.map(clientEntity, ClientVo.class);
		log.info("ClientEntity to ClientVo mapping :: end");
		return vo;
	}

	public ClientEntity toEntity(ClientVo clientVo) {
		log.info("ClientVo to ClientEntity mapping :: start");
		ClientEntity entity = modelMapper.map(clientVo, ClientEntity.class);
		log.info("ClientVo to ClientEntity mapping :: end");
		return entity;
	}

}
