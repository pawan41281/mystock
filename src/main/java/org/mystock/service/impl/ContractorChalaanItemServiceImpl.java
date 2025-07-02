package org.mystock.service.impl;

import org.mystock.repositoty.ContractorChalaanItemRepository;
import org.mystock.service.ContractorChalaanItemService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorChalaanItemServiceImpl implements ContractorChalaanItemService{

	private final ContractorChalaanItemRepository contractorChalaanItemRepository;
	
	
}
