package org.mystock.service.impl;

import java.util.Optional;

import org.mystock.entity.ContractorChallanItemEntity;
import org.mystock.mapper.ContractorChallanItemMapper;
import org.mystock.repository.ContractorChallanItemRepository;
import org.mystock.service.ContractorChallanItemService;
import org.mystock.vo.ContractorChallanItemVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorChallanItemServiceImpl implements ContractorChallanItemService{

	private final ContractorChallanItemRepository contractorChallanItemRepository;
	private final ContractorChallanItemMapper mapper;

	@Override
	public ContractorChallanItemVo getById(Long id) {
		Optional<ContractorChallanItemEntity> optional = contractorChallanItemRepository.findById(id);
		if(optional.isPresent()) {
			return mapper.toVo(optional.get());
		}
		return null;
	}
	
	
}
