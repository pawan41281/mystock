package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ClientOrderItemEntity;
import org.mystock.vo.ClientOrderItemVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientOrderItemMapper {

	private final ModelMapper modelMapper;

	public ClientOrderItemVo toVo(ClientOrderItemEntity orderOrderItemEntity) {
		return modelMapper.map(orderOrderItemEntity, ClientOrderItemVo.class);
	}

	public ClientOrderItemEntity toEntity(ClientOrderItemVo orderOrderItemVo) {
		return modelMapper.map(orderOrderItemVo, ClientOrderItemEntity.class);
	}

}
