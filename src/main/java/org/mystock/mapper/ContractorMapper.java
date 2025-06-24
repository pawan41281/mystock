package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.dto.ContractorDto;
import org.mystock.vo.ContractorVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class ContractorMapper {

	private final ModelMapper modelMapper;
	
	public ContractorVo convert(ContractorDto contractorDto) {
		return modelMapper.map(contractorDto, ContractorVo.class);
	}
	
	public ContractorDto convert(ContractorVo contractorVo) {
		return modelMapper.map(contractorVo, ContractorDto.class);
	}
	
}
