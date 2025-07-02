package org.mystock.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mystock.entity.ClientChalaanEntity;
import org.mystock.mapper.ClientChalaanMapper;
import org.mystock.repositoty.ClientChalaanRepository;
import org.mystock.service.ClientChalaanService;
import org.mystock.vo.ClientChalaanVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientChalaanServiceImpl implements ClientChalaanService {

	private final ClientChalaanRepository repository;
	private final ClientChalaanMapper mapper;

	@Override
	public ClientChalaanVo save(ClientChalaanVo vo) {
		ClientChalaanEntity entity = mapper.convert(vo);
		entity = repository.save(entity);
		return mapper.convert(entity);
	}

	@Override
	public List<ClientChalaanVo> findAll() {
		return repository.findAll().stream().map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public ClientChalaanVo findById(Long id) {
		ClientChalaanVo clientChalaanVo = null;
		Optional<ClientChalaanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			clientChalaanVo = mapper.convert(optional.get());
		}
		return clientChalaanVo;
	}

	@Override
	public ClientChalaanVo deleteById(Long id) {
		ClientChalaanVo clientChalaanVo = null;
		Optional<ClientChalaanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
			clientChalaanVo = mapper.convert(optional.get());
		}
		return clientChalaanVo;
	}

	@Override
	public List<ClientChalaanVo> findByChalaanNumber(Integer chalaanNumber) {
		return repository.findByChalaanNumber(chalaanNumber).stream().map(mapper::convert).collect(Collectors.toList());
	}

	public List<ClientChalaanVo> findByChalaanDate(Date chalaanDate) {
		return repository.findByChalaanDate(chalaanDate).stream().map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanDateBetween(Date startDate, Date endDate) {
		return repository.findByChalaanDateBetween(startDate, endDate).stream().map(mapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByClient_Id(Long clientId) {
		return repository.findByClient_Id(clientId).stream().map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanType(String chalaanType) {
		return repository.findByChalaanType(chalaanType).stream().map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanDateBetweenAndClient_Id(Date start, Date end, Long clientId) {
		return repository.findByChalaanDateBetweenAndClient_Id(start, end, clientId).stream()
				.map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanTypeAndClient_Id(String type, Long clientId) {
		return repository.findByChalaanTypeAndClient_Id(type, clientId).stream().map(mapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanDateBetweenAndChalaanType(Date start, Date end, String chalaanType) {
		return repository.findByChalaanDateBetweenAndChalaanType(start, end, chalaanType).stream().map(mapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientChalaanVo> findByChalaanDateBetweenAndChalaanTypeAndClient_Id(Date start, Date end,
			String chalaanType, Long clientId) {
		return repository.findByChalaanDateBetweenAndChalaanTypeAndClient_Id(start, end, chalaanType, clientId)
				.stream().map(mapper::convert).collect(Collectors.toList());
	}

}
