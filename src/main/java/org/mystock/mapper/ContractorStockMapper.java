package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ContractorStockEntity;
import org.mystock.vo.ContractorStockVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ContractorStockMapper {

	private final ModelMapper modelMapper;

	public ContractorStockVo toVo(ContractorStockEntity contractorStockEntity) {
		return modelMapper.map(contractorStockEntity, ContractorStockVo.class);
	}

	public ContractorStockEntity toEntity(ContractorStockVo contractorStockVo) {
		return modelMapper.map(contractorStockVo, ContractorStockEntity.class);
	}

}
