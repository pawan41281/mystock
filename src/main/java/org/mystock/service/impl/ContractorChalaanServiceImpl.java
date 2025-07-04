package org.mystock.service.impl;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.ContractorChalaanEntity;
import org.mystock.mapper.ContractorChalaanMapper;
import org.mystock.repository.ContractorChalaanRepository;
import org.mystock.service.ContractorChalaanService;
import org.mystock.vo.ContractorChalaanVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorChalaanServiceImpl implements ContractorChalaanService {

	private final ContractorChalaanRepository repository;
	private final ContractorChalaanMapper mapper;

	@Override
	public ContractorChalaanVo save(ContractorChalaanVo vo) {
		ContractorChalaanEntity entity = mapper.toEntity(vo);
		entity = repository.save(entity);
		return mapper.toVo(entity);
	}

	@Override
	public Set<ContractorChalaanVo> saveAll(Set<ContractorChalaanVo> vos) {
		if (vos == null || vos.isEmpty())
			return Collections.emptySet();
		List<ContractorChalaanEntity> entities = vos.stream().map(mapper::toEntity).collect(Collectors.toList());
		entities = repository.saveAll(entities);
		return entities.stream().map(mapper::toVo).collect(Collectors.toSet());
	}

	@Override
	public List<ContractorChalaanVo> findAll() {
		return repository.findAll().stream().map(mapper::toVo).collect(Collectors.toList());
	}

	@Override
	public ContractorChalaanVo findById(Long id) {
		ContractorChalaanVo contractorChalaanVo = null;
		Optional<ContractorChalaanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			contractorChalaanVo = mapper.toVo(optional.get());
		}
		return contractorChalaanVo;
	}

//	@Override
//	public ContractorChalaanVo deleteById(Long id) {
//		ContractorChalaanVo contractorChalaanVo = null;
//		Optional<ContractorChalaanEntity> optional = repository.findById(id);
//		if (optional.isPresent()) {
//			repository.deleteById(id);
//			contractorChalaanVo = mapper.toVo(optional.get());
//		}
//		return contractorChalaanVo;
//	}

	@Transactional
	public ContractorChalaanVo deleteById(Long id) {
		return repository.findById(id).map(entity -> {
			repository.deleteById(id);
			return mapper.toVo(entity);
		}).orElse(null);
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanNumber(Integer chalaanNumber) {
		return repository.findByChalaanNumber(chalaanNumber).stream().map(mapper::toVo).collect(Collectors.toList());
	}

	public List<ContractorChalaanVo> findByChalaanDate(Date chalaanDate) {
		return repository.findByChalaanDate(chalaanDate).stream().map(mapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanDateBetween(Date startDate, Date endDate) {
		return repository.findByChalaanDateBetween(startDate, endDate).stream().map(mapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByContractor_Id(Long contractorId) {
		return repository.findByContractor_Id(contractorId).stream().map(mapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanType(String chalaanType) {
		return repository.findByChalaanType(chalaanType).stream().map(mapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanDateBetweenAndContractor_Id(Date start, Date end, Long contractorId) {
		return repository.findByChalaanDateBetweenAndContractor_Id(start, end, contractorId).stream().map(mapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanTypeAndContractor_Id(String type, Long contractorId) {
		return repository.findByChalaanTypeAndContractor_Id(type, contractorId).stream().map(mapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanDateBetweenAndChalaanType(Date start, Date end, String chalaanType) {
		return repository.findByChalaanDateBetweenAndChalaanType(start, end, chalaanType).stream().map(mapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(Date start, Date end,
			String chalaanType, Long contractorId) {
		return repository.findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(start, end, chalaanType, contractorId)
				.stream().map(mapper::toVo).collect(Collectors.toList());
	}

}
