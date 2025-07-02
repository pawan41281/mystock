package org.mystock.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mystock.entity.ClientEntity;
import org.mystock.mapper.ClientMapper;
import org.mystock.repositoty.ClientRepository;
import org.mystock.service.ClientService;
import org.mystock.vo.ClientVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;

	@Override
	public ClientVo save(ClientVo clientVo) {
		ClientEntity saved = clientRepository.save(clientMapper.convert(clientVo));
		return clientMapper.convert(saved);
	}

	@Override
	public List<ClientVo> getAll() {
		return clientRepository.findAll().stream().map(clientMapper::convert).collect(Collectors.toList());
	}

	@Override
	public ClientVo getById(Long id) {
		return clientRepository.findById(id).map(clientMapper::convert).orElse(null);
	}

	@Override
	public ClientVo updateStatus(Long id, boolean status) {
		Optional<ClientEntity> existing = clientRepository.findById(id);
		if (existing.isPresent()) {
			ClientEntity entity = existing.get();
			entity.setActive(status);
			ClientEntity saved = clientRepository.save(entity);
			return clientMapper.convert(saved);
		}
		return null;
	}

	@Override
	public List<ClientVo> findByClientNameIgnoreCase(String clientName) {
		return clientRepository.findByClientNameContainingIgnoreCase(clientName).stream().map(clientMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByCityIgnoreCase(String city) {
		return clientRepository.findByCityContainingIgnoreCase(city).stream().map(clientMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByStateIgnoreCase(String state) {
		return clientRepository.findByStateContainingIgnoreCase(state).stream().map(clientMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByCountryIgnoreCase(String country) {
		return clientRepository.findByCountryIgnoreCase(country).stream().map(clientMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByEmailIgnoreCase(String email) {
		return clientRepository.findByEmailIgnoreCase(email).stream().map(clientMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByMobile(String mobile) {
		return clientRepository.findByMobile(mobile).stream().map(clientMapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByGstNoIgnoreCase(String gstNo) {
		return clientRepository.findByGstNoContainingIgnoreCase(gstNo).stream().map(clientMapper::convert)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByActive(boolean active) {
		return clientRepository.findByActive(active).stream().map(clientMapper::convert).collect(Collectors.toList());
	}

}
