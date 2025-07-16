package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ClientChallanItemEntity;
import org.mystock.vo.ClientChallanItemVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientChallanItemMapper {

	private final ModelMapper modelMapper;

	public ClientChallanItemVo toVo(ClientChallanItemEntity orderChallanItemEntity) {
		return modelMapper.map(orderChallanItemEntity, ClientChallanItemVo.class);
	}

	public ClientChallanItemEntity toEntity(ClientChallanItemVo orderChallanItemVo) {
		return modelMapper.map(orderChallanItemVo, ClientChallanItemEntity.class);
	}

}
