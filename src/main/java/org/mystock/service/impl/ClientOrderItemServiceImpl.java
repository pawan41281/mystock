package org.mystock.service.impl;

import java.util.Optional;

import org.mystock.entity.ClientOrderItemEntity;
import org.mystock.mapper.ClientOrderItemMapper;
import org.mystock.repository.ClientOrderItemRepository;
import org.mystock.service.ClientOrderItemService;
import org.mystock.vo.ClientOrderItemVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientOrderItemServiceImpl implements ClientOrderItemService {

	private final ClientOrderItemRepository clientOrderItemRepository;
	private final ClientOrderItemMapper mapper;

	@Override
	public ClientOrderItemVo getById(Long id) {
		Optional<ClientOrderItemEntity> optional = clientOrderItemRepository.findById(id);
		if (optional.isPresent()) {
			return mapper.toVo(optional.get());
		}
		return null;
	}

}
