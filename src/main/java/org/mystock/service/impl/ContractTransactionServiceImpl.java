package org.mystock.service.impl;

import java.util.ArrayList;
import java.util.List;

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

}
