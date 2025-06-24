package org.mystock.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mystock.dto.OrderDto;
import org.mystock.mapper.OrderMapper;
import org.mystock.repositoty.OrderRepository;
import org.mystock.service.OrderService;
import org.mystock.vo.OrderVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

	private final OrderMapper orderMapper;
	private final OrderRepository orderRepository;

	@Override
	public List<OrderVo> list() {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findAll();
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public OrderVo save(OrderVo orderVo) {
		OrderDto orderDto = orderMapper.convert(orderVo);
		orderDto = orderRepository.save(orderDto);
		orderVo = orderMapper.convert(orderDto);
		return orderVo;

	}

}
