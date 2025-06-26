package org.mystock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	@Override
	public ClientVo findById(Long id) {
		ClientVo clientVo = null;
		Optional<ClientDto> optionDto = clientRepository.findById(id);
		if (optionDto.isPresent()) {
			clientVo = clientMapper.convert(optionDto.get());
		}
		return clientVo;
	}

	@Override
	public List<ClientVo> findByEmailIgnoreCase(String email) {
		List<ClientVo> voList = new ArrayList<>();
		List<ClientDto> dtoList = clientRepository.findByEmailIgnoreCase(email);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(clientMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ClientVo> findByMobile(String mobile) {
		List<ClientVo> voList = new ArrayList<>();
		List<ClientDto> dtoList = clientRepository.findByMobile(mobile);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(clientMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ClientVo> findByGstNoIgnoreCase(String gstNo) {
		List<ClientVo> voList = new ArrayList<>();
		List<ClientDto> dtoList = clientRepository.findByGstNoIgnoreCase(gstNo);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(clientMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ClientVo> findByStatus(boolean active) {
		List<ClientVo> voList = new ArrayList<>();
		List<ClientDto> dtoList = clientRepository.findByStatus(active);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(clientMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<ClientVo> findByEmailOrMobileOrGstNoOrStatus(String email, String mobile, String gstNo,
			boolean active) {
		List<ClientVo> voList = new ArrayList<>();
		List<ClientDto> dtoList = clientRepository.findByEmailOrMobileOrGstNoOrStatus(email, mobile, gstNo, active);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(clientMapper.convert(dto));
			});
		}
		return voList;
	}

}
