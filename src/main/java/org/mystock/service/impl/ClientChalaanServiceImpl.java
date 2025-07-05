package org.mystock.service.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.ClientChalaanEntity;
import org.mystock.mapper.ClientChalaanMapper;
import org.mystock.repository.ClientChalaanRepository;
import org.mystock.service.ClientChalaanService;
import org.mystock.vo.ClientChalaanVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientChalaanServiceImpl implements ClientChalaanService {

	private final ClientChalaanRepository repository;
	private final ClientChalaanMapper mapper;

	@Override
	public ClientChalaanVo save(ClientChalaanVo vo) {
		ClientChalaanEntity entity = mapper.toEntity(vo);
		entity = repository.save(entity);
		return mapper.toVo(entity);
	}

	@Override
	public Set<ClientChalaanVo> saveAll(Set<ClientChalaanVo> vos) {
		if (vos == null || vos.isEmpty())
			return Collections.emptySet();
		List<ClientChalaanEntity> entities = vos.stream().map(mapper::toEntity).collect(Collectors.toList());
		entities = repository.saveAll(entities);
		return entities.stream().map(mapper::toVo).collect(Collectors.toSet());
	}

	@Override
	public ClientChalaanVo findById(Long id) {
		ClientChalaanVo clientChalaanVo = null;
		Optional<ClientChalaanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			clientChalaanVo = mapper.toVo(optional.get());
		}
		return clientChalaanVo;
	}

	@Transactional
	public ClientChalaanVo deleteById(Long id) {
		return repository.findById(id).map(entity -> {
			repository.deleteById(id);
			return mapper.toVo(entity);
		}).orElse(null);
	}

	@Override
	public List<ClientChalaanVo> findAll(Integer chalaanNumber, Long clientId, LocalDate fromChalaanDate,
			LocalDate toChalaanDate, String chalaanType) {

		return repository.findAll(chalaanNumber, clientId, fromChalaanDate, toChalaanDate, chalaanType).stream()
				.map(mapper::toVo).collect(Collectors.toList());
	}

}
