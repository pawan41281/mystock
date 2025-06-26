package org.mystock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mystock.dto.ContractorDto;
import org.mystock.mapper.ContractorMapper;
import org.mystock.repositoty.ContractorRepository;
import org.mystock.service.ContractorService;
import org.mystock.vo.ContractorVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorServiceImpl implements ContractorService {

	private final ContractorMapper contractorMapper;
	private final ContractorRepository contractorRepository;

	@Override
	public List<ContractorVo> list() {
		List<ContractorVo> voList = new ArrayList<>();
		List<ContractorDto> dtoList = contractorRepository.findAll();
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractorMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public ContractorVo save(ContractorVo contractorVo) {
		ContractorDto contractorDto = contractorMapper.convert(contractorVo);
		contractorDto = contractorRepository.save(contractorDto);
		contractorVo = contractorMapper.convert(contractorDto);
		return contractorVo;

	}

	@Override
	public List<ContractorVo> findByContractorNameIgnoreCase(String name) {
		List<ContractorVo> voList = new ArrayList<>();
		List<ContractorDto> dtoList = contractorRepository.findByContractorNameIgnoreCase(name);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractorMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ContractorVo> findByEmailIgnoreCase(String email) {
		List<ContractorVo> voList = new ArrayList<>();
		List<ContractorDto> dtoList = contractorRepository.findByEmailIgnoreCase(email);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractorMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ContractorVo> findByMobile(String mobile) {
		List<ContractorVo> voList = new ArrayList<>();
		List<ContractorDto> dtoList = contractorRepository.findByMobile(mobile);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractorMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ContractorVo> findByGstNoIgnoreCase(String gstNo) {
		List<ContractorVo> voList = new ArrayList<>();
		List<ContractorDto> dtoList = contractorRepository.findByGstNoIgnoreCase(gstNo);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractorMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ContractorVo> findByStatus(boolean active) {
		List<ContractorVo> voList = new ArrayList<>();
		List<ContractorDto> dtoList = contractorRepository.findByStatus(active);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractorMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ContractorVo> findByEmailOrMobileOrGstNoOrStatus(String email, String mobile, String gstNo,
			boolean active) {
		List<ContractorVo> voList = new ArrayList<>();
		List<ContractorDto> dtoList = contractorRepository.findByEmailOrMobileOrGstNoOrStatus(email, mobile, gstNo, active);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractorMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public ContractorVo findById(Long id) {
		ContractorVo contractorVo = null;
		Optional<ContractorDto> optionDto = contractorRepository.findById(id);
		if(optionDto.isPresent()) {
			contractorVo = contractorMapper.convert(optionDto.get());
		}
		return contractorVo;
	}

}
