package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.dto.OrderDto;
import org.mystock.vo.OrderVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class OrderMapper {

	private final ModelMapper modelMapper;
	
	public OrderVo convert(OrderDto orderDto) {
		return modelMapper.map(orderDto, OrderVo.class);
	}
	
	public OrderDto convert(OrderVo orderVo) {
		return modelMapper.map(orderVo, OrderDto.class);
	}
	
}
