package org.mystock.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mystock.dto.ContractTransactionDto;
import org.mystock.mapper.ContractTransactionMapper;
import org.mystock.repositoty.ContractTransactionRepository;
import org.mystock.service.ContractTransactionService;
import org.mystock.vo.ContractTransactionVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractTransactionServiceImpl implements ContractTransactionService {

	private final ContractTransactionMapper contractTransactionMapper;
	private final ContractTransactionRepository contractTransactionRepository;

	@Override
	public List<ContractTransactionVo> list() {
		List<ContractTransactionVo> voList = new ArrayList<>();
		List<ContractTransactionDto> dtoList = contractTransactionRepository.findAll();
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public ContractTransactionVo save(ContractTransactionVo contractTransactionVo) {
		ContractTransactionDto contractTransactionDto = contractTransactionMapper.convert(contractTransactionVo);
		contractTransactionDto = contractTransactionRepository.save(contractTransactionDto);
		contractTransactionVo = contractTransactionMapper.convert(contractTransactionDto);
		return contractTransactionVo;

	}

	@Override
	public ContractTransactionVo findById(Long id) {
		ContractTransactionVo contractTransactionVo = null;
		Optional<ContractTransactionDto> optionalDto = contractTransactionRepository.findById(id);
		if (optionalDto.isPresent()) {
			contractTransactionVo = contractTransactionMapper.convert(optionalDto.get());
		}
		return contractTransactionVo;
	}

	@Override
	public List<ContractTransactionVo> findByChalaanDateBetween(Long fromDate, Long toDate) {
		List<ContractTransactionVo> voList = new ArrayList<>();
		List<ContractTransactionDto> dtoList = contractTransactionRepository.findByChalaanDateBetween(new Date(fromDate), new Date(toDate));
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ContractTransactionVo> findByChalaanNumber(Integer chalaanNumber) {
		List<ContractTransactionVo> voList = new ArrayList<>();
		List<ContractTransactionDto> dtoList = contractTransactionRepository.findByChalaanNumber(chalaanNumber);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ContractTransactionVo> findByChalaanDateBetweenAndContractor(Long fromChalaanDate, Long toChalaanDate,
			Long contractor) {
		List<ContractTransactionVo> voList = new ArrayList<>();
		List<ContractTransactionDto> dtoList = contractTransactionRepository
				.findByChalaanDateBetweenAndContractor(new Date(fromChalaanDate), new Date(toChalaanDate), contractor);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ContractTransactionVo> findByChalaanDateBetweenAndContractorAndDesign(Long fromChalaanDate,
			Long toChalaanDate, Long contractor, String design) {
		List<ContractTransactionVo> voList = new ArrayList<>();
		List<ContractTransactionDto> dtoList = contractTransactionRepository
				.findByChalaanDateBetweenAndContractorAndDesign(new Date(fromChalaanDate), new Date(toChalaanDate), contractor, design);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ContractTransactionVo> findByChalaanDateBetweenAndContractorAndDesignAndColor(Long fromChalaanDate,
			Long toChalaanDate, Long contractor, String design, String color) {
		List<ContractTransactionVo> voList = new ArrayList<>();
		List<ContractTransactionDto> dtoList = contractTransactionRepository
				.findByChalaanDateBetweenAndContractorAndDesignAndColor(new Date(fromChalaanDate), new Date(toChalaanDate), contractor,
						design, color);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(contractTransactionMapper.convert(dto));
			});
		}
		return voList;
	}

}
