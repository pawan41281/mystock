package org.mystock.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.ClientEntity;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.mapper.ClientMapper;
import org.mystock.repository.ClientRepository;
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
		if(clientVo.getId()!=null) {//update request
			ClientVo existingVo = getById(clientVo.getId());
			if (existingVo == null)
				throw new ResourceNotFoundException("Invalid ID :: ".concat(String.valueOf(clientVo.getId())));
			if(clientVo.getActive()==null) clientVo.setActive(existingVo.getActive());
			if(clientVo.getAddress()==null) clientVo.setAddress(existingVo.getAddress());
			if(clientVo.getCity()==null) clientVo.setCity(existingVo.getCity());
			if(clientVo.getClientName()==null) clientVo.setClientName(existingVo.getClientName());
			if(clientVo.getCountry()==null) clientVo.setCountry(existingVo.getCountry());
			clientVo.setCreatedOn(existingVo.getCreatedOn());
			if(clientVo.getEmail()==null) clientVo.setEmail(existingVo.getEmail());
			if(clientVo.getGstNo()==null) clientVo.setGstNo(existingVo.getGstNo());
			if(clientVo.getMobile()==null) clientVo.setMobile(existingVo.getMobile());
			if(clientVo.getState()==null) clientVo.setState(existingVo.getState());
		}else {//new request
			clientVo.setCreatedOn(LocalDateTime.now());
			clientVo.setActive(Boolean.TRUE);
		}
		ClientEntity saved = clientRepository.save(clientMapper.toEntity(clientVo));
		return clientMapper.toVo(saved);
	}

	@Override
	public Set<ClientVo> saveAll(Set<ClientVo> clientVos) {
		List<ClientEntity> entities = clientVos.stream().map(clientMapper::toEntity).collect(Collectors.toList());
		entities = clientRepository.saveAll(entities);
		Set<ClientVo> saved = entities.stream().map(clientMapper::toVo).collect(Collectors.toSet());
		return saved;
	}

	@Override
	public List<ClientVo> getAll() {
		return clientRepository.findAll().stream().map(clientMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public ClientVo getById(Long id) {
		return clientRepository.findById(id).map(clientMapper::toVo).orElse(null);
	}

	@Override
	public ClientVo updateStatus(Long id, boolean status) {
		Optional<ClientEntity> existing = clientRepository.findById(id);
		if (existing.isPresent()) {
			ClientEntity entity = existing.get();
			entity.setActive(status);
			ClientEntity saved = clientRepository.save(entity);
			return clientMapper.toVo(saved);
		}
		return null;
	}

	@Override
	public List<ClientVo> findByClientNameIgnoreCase(String clientName) {
		return clientRepository.findByClientNameContainingIgnoreCase(clientName).stream().map(clientMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByCityIgnoreCase(String city) {
		return clientRepository.findByCityContainingIgnoreCase(city).stream().map(clientMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByStateIgnoreCase(String state) {
		return clientRepository.findByStateContainingIgnoreCase(state).stream().map(clientMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByCountryIgnoreCase(String country) {
		return clientRepository.findByCountryIgnoreCase(country).stream().map(clientMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByEmailIgnoreCase(String email) {
		return clientRepository.findByEmailIgnoreCase(email).stream().map(clientMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByMobile(String mobile) {
		return clientRepository.findByMobile(mobile).stream().map(clientMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByGstNoIgnoreCase(String gstNo) {
		return clientRepository.findByGstNoContainingIgnoreCase(gstNo).stream().map(clientMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> findByActive(boolean active) {
		return clientRepository.findByActive(active).stream().map(clientMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ClientVo> find(String clientName, String city,
			String state, String mobile, String email, String gstNo, Boolean active) {
		return clientRepository.find(clientName, city, state,
				mobile, email, gstNo, active).stream().map(clientMapper::toVo).collect(Collectors.toList());
	}

}
