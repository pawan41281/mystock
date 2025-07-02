package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ContractorChalaanItemEntity;
import org.mystock.vo.ContractorChalaanItemVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ContractorChalaanItemMapper {

	private final ModelMapper modelMapper;

	public ContractorChalaanItemVo convert(ContractorChalaanItemEntity contractorChalaanItemEntity) {
		return modelMapper.map(contractorChalaanItemEntity, ContractorChalaanItemVo.class);
	}

	public ContractorChalaanItemEntity convert(ContractorChalaanItemVo contractorChalaanItemVo) {
		return modelMapper.map(contractorChalaanItemVo, ContractorChalaanItemEntity.class);
	}

}
