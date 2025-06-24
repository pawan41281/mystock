package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.dto.OrderTransactionDto;
import org.mystock.vo.OrderTransactionVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class OrderTransactionMapper {

	private final ModelMapper modelMapper;
	
	public OrderTransactionVo convert(OrderTransactionDto orderTransactionDto) {
		return modelMapper.map(orderTransactionDto, OrderTransactionVo.class);
	}
	
	public OrderTransactionDto convert(OrderTransactionVo orderTransactionVo) {
		return modelMapper.map(orderTransactionVo, OrderTransactionDto.class);
	}
	
}
