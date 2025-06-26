package org.mystock.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	@Override
	public List<OrderVo> findByOrderNumber(Integer orderNumber) {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findByOrderNumber(orderNumber);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderVo> findByClient(Long client) {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findByClient(client);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderVo> findByDesign(String design) {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findByDesign(design);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderVo> findByOrderDateBetweenAndClient(Long fromDate, Long toDate, Long client) {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findByOrderDateBetweenAndClient(new Date(fromDate),
				new Date(toDate), client);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderVo> findByOrderDateBetweenAndClientAndDesign(Long fromDate, Long toDate, Long client,
			String design) {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findByOrderDateBetweenAndClientAndDesign(new Date(fromDate),
				new Date(toDate), client, design);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderVo> findByOrderDateBetweenAndClientAndDesignAndColor(Long fromDate, Long toDate, Long client,
			String design, String color) {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findByOrderDateBetweenAndClientAndDesignAndColor(new Date(fromDate),
				new Date(toDate), client, design, color);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderVo> findByOrderDateBetweenAndDesign(Long fromDate, Long toDate, String design) {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findByOrderDateBetweenAndDesign(new Date(fromDate), new Date(toDate),
				design);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderVo> findByOrderDateBetween(Date fromDate, Date toDate) {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findByOrderDateBetween(fromDate, toDate);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderVo> findByOrderDateBetweenAndDesignAndColor(Long fromDate, Long toDate, String design,
			String color) {
		List<OrderVo> voList = new ArrayList<>();
		List<OrderDto> dtoList = orderRepository.findByOrderDateBetweenAndDesignAndColor(new Date(fromDate), new Date(toDate), design,
				color);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public OrderVo findById(Long id) {
		OrderVo orderVo = null;
		Optional<OrderDto> optionalDto = orderRepository.findById(id);
		if (optionalDto.isPresent()) {
			orderVo = orderMapper.convert(optionalDto.get());
		}
		return orderVo;
	}

	@Override
	public OrderVo updateStatus(boolean status, Long id) {
		OrderVo orderVo = findById(id);
		if(orderVo!=null) {
			orderRepository.updateStatus(status, id);
			orderVo.setActive(status);
		}
		return null;
	}

}
