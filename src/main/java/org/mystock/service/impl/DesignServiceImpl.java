package org.mystock.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.DesignEntity;
import org.mystock.exception.BusinessException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.mapper.DesignMapper;
import org.mystock.repository.DesignRepository;
import org.mystock.service.DesignService;
import org.mystock.vo.DesignVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DesignServiceImpl implements DesignService {

	private final DesignRepository designRepository;
	private final DesignMapper designMapper;

	@Override
	public DesignVo save(DesignVo designVo) {
		if(designVo.getId()!=null) {//update request
			DesignVo existingVo = getById(designVo.getId());
			if (existingVo == null)
				throw new ResourceNotFoundException("Invalid ID :: ".concat(String.valueOf(designVo.getId())));
			if(designVo.getDesignName()==null) designVo.setDesignName(existingVo.getDesignName());
			if(designVo.getDescription()==null) designVo.setDescription(existingVo.getDescription());
			if(designVo.getActive()==null) designVo.setActive(existingVo.getActive());
			designVo.setCreatedOn(existingVo.getCreatedOn());
		}else if(designVo.getDesignName()!=null) {//update request where name already exist in DB
			DesignVo existingVo = getByName(designVo.getDesignName());
			if(existingVo != null) {//update request
				designVo.setId(existingVo.getId());
			}else {//new request
				designVo.setId(null);
				designVo.setActive(Boolean.TRUE);
				designVo.setCreatedOn(LocalDateTime.now());
			}
		}else {//new request
			designVo.setId(null);
			designVo.setActive(Boolean.TRUE);
			designVo.setCreatedOn(LocalDateTime.now());
		}
		DesignEntity designEntity = designMapper.toEntity(designVo);
		designEntity = designRepository.save(designEntity);
		return designMapper.toVo(designEntity);
	}

	@Override
	public Set<DesignVo> saveAll(Set<DesignVo> designVos) {
		
		//check duplicate design name
		Set<String> designNames = new HashSet<>();
		List<String> duplicates = designVos
									.stream()
									.map(DesignVo::getDesignName)
									.map(String::toUpperCase) // case-insensitive
									.filter(name -> !designNames.add(name))
						            .toList();
		if(!duplicates.isEmpty()) {
			throw new BusinessException("Duplicate values in payload :: " + duplicates);
		}
		
		List<DesignEntity> entities = designVos.stream().map(designMapper::toEntity).collect(Collectors.toList());
		entities = designRepository.saveAll(entities);
		return entities.stream().map(designMapper::toVo).collect(Collectors.toSet());
	}

	@Override
	public DesignVo getById(Long id) {
		Optional<DesignEntity> optional = designRepository.findById(id);
		return optional.map(designMapper::toVo).orElse(null);
	}

	@Override
	public List<DesignVo> getAllByName(String name) {
		List<DesignEntity> entities = designRepository.findByDesignNameIgnoreCaseLike("%"+name+"%");
		return entities.stream().map(designMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public DesignVo getByName(String name) {
		DesignEntity entity = designRepository.findByDesignNameIgnoreCase(name);
		if(entity==null)
			throw new ResourceNotFoundException("Design not exists :: "+name);
		return designMapper.toVo(entity);
	}

	@Override
	public List<DesignVo> getByStatus(boolean status) {
		List<DesignEntity> entities = designRepository.findByActive(status);
		return entities.stream().map(designMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<DesignVo> getAll() {
		List<DesignEntity> entities = designRepository.findAll();
		return entities.stream().map(designMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public DesignVo updateStatus(Long id, boolean status) {
		Optional<DesignEntity> optional = designRepository.findById(id);
		if (optional.isPresent()) {
			DesignEntity existing = optional.get();
			existing.setActive(status);
			DesignEntity updated = designRepository.save(existing);
			return designMapper.toVo(updated);
		}
		return null;
	}

}
