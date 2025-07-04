package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ContractorEntity;
import org.mystock.vo.ContractorVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ContractorMapper {

	private final ModelMapper modelMapper;

	public ContractorVo toVo(ContractorEntity contractorEntity) {
		return modelMapper.map(contractorEntity, ContractorVo.class);
	}

	public ContractorEntity toEntity(ContractorVo contractorVo) {
		return modelMapper.map(contractorVo, ContractorEntity.class);
	}

}
