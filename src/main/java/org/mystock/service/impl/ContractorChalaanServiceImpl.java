package org.mystock.service.impl;

import java.time.LocalDate;
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
	public ContractorChalaanVo findById(Long id) {
		ContractorChalaanVo contractorChalaanVo = null;
		Optional<ContractorChalaanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			contractorChalaanVo = mapper.toVo(optional.get());
		}
		return contractorChalaanVo;
	}

	@Transactional
	public ContractorChalaanVo deleteById(Long id) {
		return repository.findById(id).map(entity -> {
			repository.deleteById(id);
			return mapper.toVo(entity);
		}).orElse(null);
	}

	@Override
	public List<ContractorChalaanVo> findAll(Integer chalaanNumber, Long contractorId, LocalDate fromChalaanDate,
			LocalDate toChalaanDate, String chalaanType) {

		return repository.findAll(chalaanNumber, contractorId, fromChalaanDate, toChalaanDate, chalaanType).stream()
				.map(mapper::toVo).collect(Collectors.toList());
	}

}
