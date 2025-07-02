package org.mystock.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mystock.entity.ContractorChalaanEntity;
import org.mystock.mapper.ContractorChalaanMapper;
import org.mystock.repositoty.ContractorChalaanRepository;
import org.mystock.service.ContractorChalaanService;
import org.mystock.vo.ContractorChalaanVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorChalaanServiceImpl implements ContractorChalaanService {

	private final ContractorChalaanRepository repository;
	private final ContractorChalaanMapper mapper;

	@Override
	public ContractorChalaanVo save(ContractorChalaanVo vo) {
		ContractorChalaanEntity entity = mapper.convert(vo);
		entity = repository.save(entity);
		return mapper.convert(entity);
	}

	@Override
	public List<ContractorChalaanVo> findAll() {
		return repository.findAll().stream().map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public ContractorChalaanVo findById(Long id) {
		ContractorChalaanVo contractorChalaanVo = null;
		Optional<ContractorChalaanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			contractorChalaanVo = mapper.convert(optional.get());
		}
		return contractorChalaanVo;
	}

	@Override
	public ContractorChalaanVo deleteById(Long id) {
		ContractorChalaanVo contractorChalaanVo = null;
		Optional<ContractorChalaanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			repository.deleteById(id);
			contractorChalaanVo = mapper.convert(optional.get());
		}
		return contractorChalaanVo;
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanNumber(Integer chalaanNumber) {
		return repository.findByChalaanNumber(chalaanNumber).stream().map(mapper::convert).collect(Collectors.toList());
	}

	public List<ContractorChalaanVo> findByChalaanDate(Date chalaanDate) {
		return repository.findByChalaanDate(chalaanDate).stream().map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanDateBetween(Date startDate, Date endDate) {
		return repository.findByChalaanDateBetween(startDate, endDate).stream().map(mapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByContractor_Id(Long contractorId) {
		return repository.findByContractor_Id(contractorId).stream().map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanType(String chalaanType) {
		return repository.findByChalaanType(chalaanType).stream().map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanDateBetweenAndContractor_Id(Date start, Date end, Long contractorId) {
		return repository.findByChalaanDateBetweenAndContractor_Id(start, end, contractorId).stream()
				.map(mapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanTypeAndContractor_Id(String type, Long contractorId) {
		return repository.findByChalaanTypeAndContractor_Id(type, contractorId).stream().map(mapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanDateBetweenAndChalaanType(Date start, Date end, String chalaanType) {
		return repository.findByChalaanDateBetweenAndChalaanType(start, end, chalaanType).stream().map(mapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorChalaanVo> findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(Date start, Date end,
			String chalaanType, Long contractorId) {
		return repository.findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(start, end, chalaanType, contractorId)
				.stream().map(mapper::convert).collect(Collectors.toList());
	}

}
