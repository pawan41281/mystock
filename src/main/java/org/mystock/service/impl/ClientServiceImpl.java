package org.mystock.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.mystock.dto.ClientDto;
import org.mystock.mapper.ClientMapper;
import org.mystock.repositoty.ClientRepository;
import org.mystock.service.ClientService;
import org.mystock.vo.ClientVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

	private final ClientMapper clientMapper;
	private final ClientRepository clientRepository;

	@Override
	public List<ClientVo> list() {
		List<ClientVo> voList = new ArrayList<>();
		List<ClientDto> dtoList = clientRepository.findAll();
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(clientMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public ClientVo save(ClientVo clientVo) {
		ClientDto clientDto = clientMapper.convert(clientVo);
		clientDto = clientRepository.save(clientDto);
		clientVo = clientMapper.convert(clientDto);
		return clientVo;

	}

}
