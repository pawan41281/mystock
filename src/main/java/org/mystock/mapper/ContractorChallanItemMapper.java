package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ContractorChallanItemEntity;
import org.mystock.vo.ContractorChallanItemVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ContractorChallanItemMapper {

	private final ModelMapper modelMapper;

	public ContractorChallanItemVo toVo(ContractorChallanItemEntity entity) {
		return modelMapper.map(entity, ContractorChallanItemVo.class);
	}

	public ContractorChallanItemEntity toEntity(ContractorChallanItemVo contractorChallanItemVo) {
		return modelMapper.map(contractorChallanItemVo, ContractorChallanItemEntity.class);
	}

}
