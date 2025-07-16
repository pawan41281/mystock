package org.mystock.service.impl;

import java.util.Optional;

import org.mystock.entity.ClientChallanItemEntity;
import org.mystock.mapper.ClientChallanItemMapper;
import org.mystock.repository.ClientChallanItemRepository;
import org.mystock.service.ClientChallanItemService;
import org.mystock.vo.ClientChallanItemVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientChallanItemServiceImpl implements ClientChallanItemService {

	private final ClientChallanItemRepository clientChallanItemRepository;
	private final ClientChallanItemMapper mapper;

	@Override
	public ClientChallanItemVo getById(Long id) {
		Optional<ClientChallanItemEntity> optional = clientChallanItemRepository.findById(id);
		if (optional.isPresent()) {
			return mapper.toVo(optional.get());
		}
		return null;
	}

}
