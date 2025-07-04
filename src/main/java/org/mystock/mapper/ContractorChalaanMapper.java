package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.ContractorChalaanEntity;
import org.mystock.entity.ContractorChalaanItemEntity;
import org.mystock.vo.ContractorChalaanVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ContractorChalaanMapper {

	private final ModelMapper modelMapper;
	private ContractorMapper contractorMapper;

	public ContractorChalaanVo toVo(ContractorChalaanEntity contractorChalaanEntity) {
		ContractorChalaanVo contractorChalaanVo = modelMapper.map(contractorChalaanEntity, ContractorChalaanVo.class);
		contractorChalaanVo.setContractor(contractorMapper.toVo(contractorChalaanEntity.getContractor()));
		return contractorChalaanVo;
	}

	public ContractorChalaanEntity toEntity(ContractorChalaanVo contractorChalaanVo) {
		ContractorChalaanEntity entity = modelMapper.map(contractorChalaanVo, ContractorChalaanEntity.class);

	    if (entity.getChalaanItems() != null) {
	        for (ContractorChalaanItemEntity item : entity.getChalaanItems()) {
	            // Set parent reference for bi-directional relationship
	            item.setChalaan(entity);

	            // If ID is 0 or present (and you want to create new), null it to avoid detached entity error
	            if (item.getId() != null && item.getId() > 0) {
	                item.setId(null); // Ensure it's treated as a new item
	            }
	        }
	    }

	    return entity;
	}

}
