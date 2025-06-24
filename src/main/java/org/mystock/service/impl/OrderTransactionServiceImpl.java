package org.mystock.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mystock.dto.OrderTransactionDto;
import org.mystock.mapper.OrderTransactionMapper;
import org.mystock.repositoty.OrderTransactionRepository;
import org.mystock.service.OrderTransactionService;
import org.mystock.vo.OrderTransactionVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderTransactionServiceImpl implements OrderTransactionService {

	private final OrderTransactionMapper orderTransactionMapper;
	private final OrderTransactionRepository orderTransactionRepository;

	@Override
	public List<OrderTransactionVo> list() {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository.findAll();
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public OrderTransactionVo save(OrderTransactionVo orderTransactionVo) {
		OrderTransactionDto orderTransactionDto = orderTransactionMapper.convert(orderTransactionVo);
		orderTransactionDto = orderTransactionRepository.save(orderTransactionDto);
		orderTransactionVo = orderTransactionMapper.convert(orderTransactionDto);
		return orderTransactionVo;

	}

}
