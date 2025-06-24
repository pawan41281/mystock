package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.dto.ContractTransactionDto;
import org.mystock.vo.ContractTransactionVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ContractTransactionMapper {

	private final ModelMapper modelMapper;
	
	public ContractTransactionVo convert(ContractTransactionDto contractTransactionDto) {
		return modelMapper.map(contractTransactionDto, ContractTransactionVo.class);
	}
	
	public ContractTransactionDto convert(ContractTransactionVo contractTransactionVo) {
		return modelMapper.map(contractTransactionVo, ContractTransactionDto.class);
	}
	
}
