package org.mystock.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.ClientOrderEntity;
import org.mystock.mapper.ClientOrderMapper;
import org.mystock.repository.ClientOrderRepository;
import org.mystock.service.ClientOrderService;
import org.mystock.vo.ClientOrderVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientOrderServiceImpl implements ClientOrderService {

	private final ClientOrderRepository repository;
	private final ClientOrderMapper mapper;
	// private final StockService stockService;
	// private final DesignMapper designMapper;
	// private final ColorMapper colorMapper;

	@Transactional
	@Override
	public ClientOrderVo save(ClientOrderVo vo) {

		ClientOrderEntity entity = mapper.toEntity(vo);
		final ClientOrderEntity savedEntity = repository.save(entity);
		return mapper.toVo(savedEntity);
	}

	@Transactional
	@Override
	public Set<ClientOrderVo> saveAll(Set<ClientOrderVo> vos) {
		if (vos == null || vos.isEmpty())
			return Collections.emptySet();
		List<ClientOrderEntity> entities = vos.stream().map(mapper::toEntity).collect(Collectors.toList());
		entities = repository.saveAll(entities);
		return entities.stream().map(mapper::toVo).collect(Collectors.toSet());
	}

	@Override
	public ClientOrderVo findById(Long id) {
		ClientOrderVo clientOrderVo = null;
		Optional<ClientOrderEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			clientOrderVo = mapper.toVo(optional.get());
		}
		return clientOrderVo;
	}

	@Transactional
	public ClientOrderVo deleteById(Long id) {

		ClientOrderVo orderVo = findById(id);
		if (orderVo != null) {
			repository.deleteById(id);
		}
		return orderVo;
	}

	@Override
	public List<ClientOrderVo> findAll(Integer orderNumber, Long clientId, LocalDate fromOrderDate,
			LocalDate toOrderDate) {
		return repository.findAll(orderNumber, clientId, fromOrderDate, toOrderDate).stream().map(mapper::toVo)
				.collect(Collectors.toList());
	}

}
