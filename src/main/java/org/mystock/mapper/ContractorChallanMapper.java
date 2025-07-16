package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ContractorChallanEntity;
import org.mystock.entity.ContractorChallanItemEntity;
import org.mystock.vo.ContractorChallanVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ContractorChallanMapper {

	private final ModelMapper modelMapper;
	private ContractorMapper contractorMapper;

	public ContractorChallanVo toVo(ContractorChallanEntity contractorChallanEntity) {
		ContractorChallanVo contractorChallanVo = modelMapper.map(contractorChallanEntity, ContractorChallanVo.class);
		contractorChallanVo.setContractor(contractorMapper.toVo(contractorChallanEntity.getContractor()));
		return contractorChallanVo;
	}

	public ContractorChallanEntity toEntity(ContractorChallanVo contractorChallanVo) {
		ContractorChallanEntity entity = modelMapper.map(contractorChallanVo, ContractorChallanEntity.class);
		entity.setContractor(contractorMapper.toEntity(contractorChallanVo.getContractor()));
	    if (entity.getChallanItems() != null) {
	        for (ContractorChallanItemEntity item : entity.getChallanItems()) {
	            // Set parent reference for bi-directional relationship
	            item.setChallan(entity);

	            // If ID is 0 or present (and you want to create new), null it to avoid detached entity error
	            if (item.getId() != null && item.getId() > 0) {
	                item.setId(null); // Ensure it's treated as a new item
	            }
	        }
	    }

	    return entity;
	}

}
