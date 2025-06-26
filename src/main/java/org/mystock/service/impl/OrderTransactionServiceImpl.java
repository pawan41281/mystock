package org.mystock.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	@Override
	public OrderTransactionVo findById(Long id) {
		OrderTransactionVo orderTransactionVo = null;
		Optional<OrderTransactionDto> optionalDto = orderTransactionRepository.findById(id);
		if (optionalDto.isPresent()) {
			orderTransactionVo = orderTransactionMapper.convert(optionalDto.get());
		}
		return orderTransactionVo;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetween(Long fromDate, Long toDate) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository.findByChalaanDateBetween(new Date(fromDate),
				new Date(toDate));
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanNumber(Integer chalaanNumber) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository.findByChalaanNumber(chalaanNumber);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByOrderNumber(Integer orderNumber) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository.findByOrderNumber(orderNumber);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetweenAndClient(Long fromChalaanDate, Long toChalaanDate,
			Long client) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository
				.findByChalaanDateBetweenAndClient(new Date(fromChalaanDate), new Date(toChalaanDate), client);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndDesign(Long fromChalaanDate, Long toChalaanDate,
			Long client, String design) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository.findByChalaanDateBetweenAndClientAndDesign(
				new Date(fromChalaanDate), new Date(toChalaanDate), client, design);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndDesignAndColor(Long fromChalaanDate,
			Long toChalaanDate, Long client, String design, String color) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository
				.findByChalaanDateBetweenAndClientAndDesignAndColor(new Date(fromChalaanDate), new Date(toChalaanDate),
						client, design, color);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetweenAndOrderNumber(Long fromDate, Long toDate,
			Integer orderNumber) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository
				.findByChalaanDateBetweenAndOrderNumber(new Date(fromDate), new Date(toDate), orderNumber);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndOrderNumber(Long fromChalaanDate,
			Long toChalaanDate, Long client, Integer orderNumber) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository.findByChalaanDateBetweenAndClientAndOrderNumber(
				new Date(fromChalaanDate), new Date(toChalaanDate), client, orderNumber);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetweenAndOrderNumberAndDesign(Long fromChalaanDate,
			Long toChalaanDate, Integer orderNumber, String design) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository.findByChalaanDateBetweenAndOrderNumberAndDesign(
				new Date(fromChalaanDate), new Date(toChalaanDate), orderNumber, design);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetweenAndOrderNumberAndDesignAndColor(Long fromChalaanDate,
			Long toChalaanDate, Integer orderNumber, String design, String color) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository
				.findByChalaanDateBetweenAndOrderNumberAndDesignAndColor(new Date(fromChalaanDate),
						new Date(toChalaanDate), orderNumber, design, color);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndOrderNumberAndDesign(Long fromChalaanDate,
			Long toChalaanDate, Long client, Integer orderNumber, String design) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository
				.findByChalaanDateBetweenAndClientAndOrderNumberAndDesign(new Date(fromChalaanDate),
						new Date(toChalaanDate), client, orderNumber, design);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<OrderTransactionVo> findByChalaanDateBetweenAndClientAndOrderNumberAndDesignAndColor(
			Long fromChalaanDate, Long toChalaanDate, Long client, Integer orderNumber, String design, String color) {
		List<OrderTransactionVo> voList = new ArrayList<>();
		List<OrderTransactionDto> dtoList = orderTransactionRepository
				.findByChalaanDateBetweenAndClientAndOrderNumberAndDesignAndColor(new Date(fromChalaanDate),
						new Date(toChalaanDate), client, orderNumber, design, color);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(orderTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public OrderTransactionVo delete(Long id) {
		OrderTransactionVo orderTransactionVo = findById(id);
		if(orderTransactionVo!=null) {
			OrderTransactionDto orderTransactionDto = orderTransactionMapper.convert(orderTransactionVo);
			orderTransactionRepository.delete(orderTransactionDto);
			orderTransactionVo = orderTransactionMapper.convert(orderTransactionDto);			
		}
		return orderTransactionVo;
	}

	@Override
	public List<OrderTransactionVo> deleteByOrderNumber(Integer orderNumber) {
		List<OrderTransactionVo> list = findByOrderNumber(orderNumber);
		if(list!=null && list.size()>0) {
			orderTransactionRepository.deleteByOrderNumber(orderNumber);
		}
		return list;
	}

	@Override
	public List<OrderTransactionVo> deleteByChalaanNumber(Integer chalaanNumber) {
		List<OrderTransactionVo> list = findByChalaanNumber(chalaanNumber);
		if(list!=null && list.size()>0) {
			orderTransactionRepository.deleteByChalaanNumber(chalaanNumber);
		}
		return list;
	}

	@Override
	public List<OrderTransactionVo> deleteByDesign(String design) {
		List<OrderTransactionVo> list = new ArrayList<>();
		orderTransactionRepository.findByDesign(design).stream().forEach(dto -> {
			list.add(orderTransactionMapper.convert(dto));
		});
		if(list!=null && list.size()>0) {
			orderTransactionRepository.deleteByDesign(design);
		}
		return list;
	}

	@Override
	public List<OrderTransactionVo> delete(List<Long> ids) {
		List<OrderTransactionVo> list = new ArrayList<>();
		orderTransactionRepository.findAllById(ids).stream().forEach(dto -> {
			list.add(orderTransactionMapper.convert(dto));
		});
		if(list!=null && list.size()>0) {
			orderTransactionRepository.deleteAllById(ids);
		}
		return list;
	}

}
