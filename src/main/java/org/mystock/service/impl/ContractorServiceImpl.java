package org.mystock.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.ContractorEntity;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.mapper.ContractorMapper;
import org.mystock.repository.ContractorRepository;
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
		if (contractorVo.getId() != null) {// update request
			ContractorVo existingVo = getById(contractorVo.getId());
			if (existingVo == null)
				throw new ResourceNotFoundException("Invalid ID :: ".concat(String.valueOf(contractorVo.getId())));
			if (contractorVo.getActive() == null)
				contractorVo.setActive(existingVo.getActive());
			if (contractorVo.getAddress() == null)
				contractorVo.setAddress(existingVo.getAddress());
			if (contractorVo.getCity() == null)
				contractorVo.setCity(existingVo.getCity());
			if (contractorVo.getContractorName() == null)
				contractorVo.setContractorName(existingVo.getContractorName());
			if (contractorVo.getCountry() == null)
				contractorVo.setCountry(existingVo.getCountry());
			contractorVo.setCreatedOn(existingVo.getCreatedOn());
			if (contractorVo.getEmail() == null)
				contractorVo.setEmail(existingVo.getEmail());
			if (contractorVo.getGstNo() == null)
				contractorVo.setGstNo(existingVo.getGstNo());
			if (contractorVo.getMobile() == null)
				contractorVo.setMobile(existingVo.getMobile());
			if (contractorVo.getState() == null)
				contractorVo.setState(existingVo.getState());
		} else {// new request
			contractorVo.setCreatedOn(LocalDateTime.now());
			contractorVo.setActive(Boolean.TRUE);
		}
		ContractorEntity saved = contractorRepository.save(contractorMapper.toEntity(contractorVo));
		return contractorMapper.toVo(saved);
	}

	@Override
	public Set<ContractorVo> saveAll(Set<ContractorVo> contractorVos) {
		List<ContractorEntity> entities = contractorVos.stream().map(contractorMapper::toEntity)
				.collect(Collectors.toList());
		entities = contractorRepository.saveAll(entities);
		return entities.stream().map(contractorMapper::toVo).collect(Collectors.toSet());
	}

	@Override
	public List<ContractorVo> getAll() {
		return contractorRepository.findAll().stream().map(contractorMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public ContractorVo getById(Long id) {
		return contractorRepository.findById(id).map(contractorMapper::toVo).orElse(null);
	}

	@Override
	public ContractorVo updateStatus(Long id, boolean status) {
		Optional<ContractorEntity> existing = contractorRepository.findById(id);
		if (existing.isPresent()) {
			ContractorEntity entity = existing.get();
			entity.setActive(status);
			ContractorEntity saved = contractorRepository.save(entity);
			return contractorMapper.toVo(saved);
		}
		return null;
	}

	@Override
	public List<ContractorVo> findByContractorNameIgnoreCase(String contractorName) {
		return contractorRepository.findByContractorNameContainingIgnoreCase(contractorName).stream()
				.map(contractorMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByCityIgnoreCase(String city) {
		return contractorRepository.findByCityContainingIgnoreCase(city).stream().map(contractorMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByStateIgnoreCase(String state) {
		return contractorRepository.findByStateContainingIgnoreCase(state).stream().map(contractorMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByCountryIgnoreCase(String country) {
		return contractorRepository.findByCountryIgnoreCase(country).stream().map(contractorMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByEmailIgnoreCase(String email) {
		return contractorRepository.findByEmailIgnoreCase(email).stream().map(contractorMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByMobile(String mobile) {
		return contractorRepository.findByMobile(mobile).stream().map(contractorMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByGstNoIgnoreCase(String gstNo) {
		return contractorRepository.findByGstNoContainingIgnoreCase(gstNo).stream().map(contractorMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> findByActive(boolean active) {
		return contractorRepository.findByActive(active).stream().map(contractorMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorVo> find(String contractorName, String city, String state, String mobile, String email,
			String gstNo, Boolean active) {
		return contractorRepository.find(contractorName, city, state, mobile, email, gstNo, active).stream()
				.map(contractorMapper::toVo).collect(Collectors.toList());
	}

}
