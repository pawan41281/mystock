package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ClientChalaanItemEntity;
import org.mystock.vo.ClientChalaanItemVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClientChalaanItemMapper {

	private final ModelMapper modelMapper;

	public ClientChalaanItemVo convert(ClientChalaanItemEntity orderChalaanItemEntity) {
		return modelMapper.map(orderChalaanItemEntity, ClientChalaanItemVo.class);
	}

	public ClientChalaanItemEntity convert(ClientChalaanItemVo orderChalaanItemVo) {
		return modelMapper.map(orderChalaanItemVo, ClientChalaanItemEntity.class);
	}

}
