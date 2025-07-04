package org.mystock.service.impl;

import java.sql.Date;
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
	public List<ClientChalaanVo> findAll() {
		return repository.findAll().stream().map(mapper::toVo).collect(Collectors.toList());
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

//	@Override
//	public ClientChalaanVo deleteById(Long id) {
//		ClientChalaanVo clientChalaanVo = null;
//		Optional<ClientChalaanEntity> optional = repository.findById(id);
//		if (optional.isPresent()) {
//			repository.deleteById(id);
//			clientChalaanVo = mapper.toVo(optional.get());
//		}
//		return clientChalaanVo;
//	}

	@Transactional
	public ClientChalaanVo deleteById(Long id) {
		return repository.findById(id).map(entity -> {
			repository.deleteById(id);
			return mapper.toVo(entity);
		}).orElse(null);
	}

	@Override
	public List<ClientChalaanVo> findByChalaanNumber(Integer chalaanNumber) {
		return repository.findByChalaanNumber(chalaanNumber).stream().map(mapper::toVo).collect(Collectors.toList());
	}

	public List<ClientChalaanVo> findByChalaanDate(Date chalaanDate) {
		return repository.findByChalaanDate(chalaanDate).stream().map(mapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanDateBetween(Date startDate, Date endDate) {
		return repository.findByChalaanDateBetween(startDate, endDate).stream().map(mapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByClient_Id(Long clientId) {
		return repository.findByClient_Id(clientId).stream().map(mapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanType(String chalaanType) {
		return repository.findByChalaanType(chalaanType).stream().map(mapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanDateBetweenAndClient_Id(Date start, Date end, Long clientId) {
		return repository.findByChalaanDateBetweenAndClient_Id(start, end, clientId).stream().map(mapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanTypeAndClient_Id(String type, Long clientId) {
		return repository.findByChalaanTypeAndClient_Id(type, clientId).stream().map(mapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanDateBetweenAndChalaanType(Date start, Date end, String chalaanType) {
		return repository.findByChalaanDateBetweenAndChalaanType(start, end, chalaanType).stream().map(mapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanDateBetweenAndChalaanTypeAndClient_Id(Date start, Date end,
			String chalaanType, Long clientId) {
		return repository.findByChalaanDateBetweenAndChalaanTypeAndClient_Id(start, end, chalaanType, clientId).stream()
				.map(mapper::toVo).collect(Collectors.toList());
	}

}
