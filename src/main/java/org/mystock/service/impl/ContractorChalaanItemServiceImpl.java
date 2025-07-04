package org.mystock.service.impl;

import java.util.Optional;

import org.mystock.entity.ContractorChalaanItemEntity;
import org.mystock.mapper.ContractorChalaanItemMapper;
import org.mystock.repository.ContractorChalaanItemRepository;
import org.mystock.service.ContractorChalaanItemService;
import org.mystock.vo.ContractorChalaanItemVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorChalaanItemServiceImpl implements ContractorChalaanItemService{

	private final ContractorChalaanItemRepository contractorChalaanItemRepository;
	private final ContractorChalaanItemMapper mapper;

	@Override
	public ContractorChalaanItemVo getById(Long id) {
		Optional<ContractorChalaanItemEntity> optional = contractorChalaanItemRepository.findById(id);
		if(optional.isPresent()) {
			return mapper.toVo(optional.get());
		}
		return null;
	}
	
	
}
