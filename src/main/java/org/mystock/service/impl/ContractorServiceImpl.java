package org.mystock.service.impl;

import java.util.ArrayList;
import java.util.List;

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

}
