package org.mystock.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mystock.entity.ContractorEntity;
import org.mystock.mapper.ContractorMapper;
import org.mystock.repositoty.ContractorRepository;
import org.mystock.service.ContractorService;
import org.mystock.vo.ContractorVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorServiceImpl implements ContractorService {

	private final ContractorRepository contractorRepository;
	private final ContractorMapper contractorMapper;

	@Override
	public ContractorVo save(ContractorVo contractorVo) {
		ContractorEntity saved = contractorRepository.save(contractorMapper.convert(contractorVo));
		return contractorMapper.convert(saved);
	}

	@Override
	public List<ContractorVo> getAll() {
		return contractorRepository.findAll().stream().map(contractorMapper::convert).collect(Collectors.toList());
	}

	@Override
	public ContractorVo getById(Long id) {
		return contractorRepository.findById(id).map(contractorMapper::convert).orElse(null);
	}

	@Override
	public ContractorVo updateStatus(Long id, boolean status) {
		Optional<ContractorEntity> existing = contractorRepository.findById(id);
		if (existing.isPresent()) {
			ContractorEntity entity = existing.get();
			entity.setActive(status);
			ContractorEntity saved = contractorRepository.save(entity);
			return contractorMapper.convert(saved);
		}
		return null;
	}

	@Override
	public List<ContractorVo> findByContractorNameIgnoreCase(String contractorName) {
		return contractorRepository.findByContractorNameContainingIgnoreCase(contractorName).stream()
				.map(contractorMapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByCityIgnoreCase(String city) {
		return contractorRepository.findByCityContainingIgnoreCase(city).stream().map(contractorMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByStateIgnoreCase(String state) {
		return contractorRepository.findByStateContainingIgnoreCase(state).stream().map(contractorMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByCountryIgnoreCase(String country) {
		return contractorRepository.findByCountryIgnoreCase(country).stream().map(contractorMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByEmailIgnoreCase(String email) {
		return contractorRepository.findByEmailIgnoreCase(email).stream().map(contractorMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByMobile(String mobile) {
		return contractorRepository.findByMobile(mobile).stream().map(contractorMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByGstNoIgnoreCase(String gstNo) {
		return contractorRepository.findByGstNoContainingIgnoreCase(gstNo).stream().map(contractorMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByActive(boolean active) {
		return contractorRepository.findByActive(active).stream().map(contractorMapper::convert)
				.collect(Collectors.toList());
	}

}
