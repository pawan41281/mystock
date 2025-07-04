package org.mystock.service.impl;

import java.util.Optional;

import org.mystock.entity.ClientChalaanItemEntity;
import org.mystock.mapper.ClientChalaanItemMapper;
import org.mystock.repository.ClientChalaanItemRepository;
import org.mystock.service.ClientChalaanItemService;
import org.mystock.vo.ClientChalaanItemVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientChalaanItemServiceImpl implements ClientChalaanItemService {

	private final ClientChalaanItemRepository clientChalaanItemRepository;
	private final ClientChalaanItemMapper mapper;

	@Override
	public ClientChalaanItemVo getById(Long id) {
		Optional<ClientChalaanItemEntity> optional = clientChalaanItemRepository.findById(id);
		if (optional.isPresent()) {
			return mapper.toVo(optional.get());
		}
		return null;
	}

}
