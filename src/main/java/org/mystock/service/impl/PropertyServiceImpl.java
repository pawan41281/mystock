package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import org.mystock.entity.PropertyEntity;
import org.mystock.exception.BusinessException;
import org.mystock.mapper.PropertyMapper;
import org.mystock.repository.PropertyRepository;
import org.mystock.service.PropertyService;
import org.mystock.vo.PropertyVo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PropertyServiceImpl implements PropertyService {

	private final PropertyRepository propertyRepository;
	private final PropertyMapper propertyMapper;

	@Override
	public PropertyVo save(PropertyVo propertyVo) {
		PropertyEntity propertyEntity = propertyMapper.toEntity(propertyVo);
		PropertyEntity saved = propertyRepository.save(propertyEntity);
		propertyVo = propertyMapper.toVo(saved);
		return propertyVo;
	}

	@Override
	public Set<PropertyVo> saveAll(Set<PropertyVo> propertyVos) {
		
		//check duplicate design name
		Set<String> propertyNames = new HashSet<>();
		List<String> duplicates = propertyVos
									.stream()
									.map(PropertyVo::getName)
									.map(String::toUpperCase)
									.filter(name -> !propertyNames.add(name))
									.toList();
		
		if(!duplicates.isEmpty()) {
			throw new BusinessException("Duplicate values in payload :: " + duplicates);
		}
						
		List<PropertyEntity> entities = propertyVos.stream().map(propertyMapper::toEntity).collect(Collectors.toList());
		entities = propertyRepository.saveAll(entities);
		Set<PropertyVo> saved = entities.stream().map(propertyMapper::toVo).collect(Collectors.toSet());
		return saved;
	}

	@Override
	public List<PropertyVo> getAll() {
		return propertyRepository.findAll().stream().map(propertyMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<PropertyVo> findByNameIgnoreCaseLike(String propertyName) {
		return propertyRepository.findByNameIgnoreCaseLike(propertyName).stream().map(propertyMapper::toVo).collect(Collectors.toList());
	}
	
	@Override
	public PropertyVo findByNameIgnoreCase(String propertyName) {
	    PropertyEntity entity = propertyRepository.findByNameIgnoreCase(propertyName);
	    return entity != null ? propertyMapper.toVo(entity) : null;
	}

	@Override
	public PropertyVo getById(Long id) {
		return propertyRepository.findById(id).map(propertyMapper::toVo).orElse(null);
	}

}