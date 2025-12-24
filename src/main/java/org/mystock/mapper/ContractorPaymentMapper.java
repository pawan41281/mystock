package org.mystock.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mystock.entity.ContractorPaymentEntity;
import org.mystock.vo.ContractorPaymentVo;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ContractorPaymentMapper {

	private final ModelMapper modelMapper;
	private ContractorMapper contractorMapper;

	public ContractorPaymentVo toVo(ContractorPaymentEntity contractorPaymentEntity) {
		ContractorPaymentVo contractorPaymentVo = modelMapper.map(contractorPaymentEntity, ContractorPaymentVo.class);
		contractorPaymentVo.setContractor(contractorMapper.toVo(contractorPaymentEntity.getContractor()));
		return contractorPaymentVo;
	}

	public ContractorPaymentEntity toEntity(ContractorPaymentVo contractorPaymentVo) {
		ContractorPaymentEntity entity = modelMapper.map(contractorPaymentVo, ContractorPaymentEntity.class);
		entity.setContractor(contractorMapper.toEntity(contractorPaymentVo.getContractor()));
	    return entity;
	}

}