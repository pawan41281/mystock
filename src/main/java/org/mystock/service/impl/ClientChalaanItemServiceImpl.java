package org.mystock.service.impl;

import org.mystock.repositoty.ClientChalaanItemRepository;
import org.mystock.service.ClientChalaanItemService;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientChalaanItemServiceImpl implements ClientChalaanItemService {

	private final ClientChalaanItemRepository clientChalaanItemRepository;
	
	
}
