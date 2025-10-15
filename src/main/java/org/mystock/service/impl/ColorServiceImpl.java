package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import org.mystock.entity.ColorEntity;
import org.mystock.exception.BusinessException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.mapper.ColorMapper;
import org.mystock.repository.ColorRepository;
import org.mystock.service.ColorService;
import org.mystock.vo.ColorVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

	private final ColorRepository colorRepository;
	private final ColorMapper colorMapper;

	@Override
	public ColorVo save(ColorVo colorVo) {

		if (colorVo.getId() != null && !colorVo.getId().equals(0L)) {// update request
			ColorVo existingVoById = getById(colorVo.getId());
			if (existingVoById == null)
				throw new ResourceNotFoundException("Invalid ID :: ".concat(String.valueOf(colorVo.getId())));
			if (colorVo.getColorName() == null)
				colorVo.setColorName(existingVoById.getColorName());
			if (colorVo.getActive() == null)
				colorVo.setActive(existingVoById.getActive());
			colorVo.setCreatedOn(existingVoById.getCreatedOn());
			colorVo.setUser(existingVoById.getUser());
			ColorEntity colorEntity = colorMapper.toEntity(colorVo);
			ColorEntity saved = colorRepository.save(colorEntity);
			colorVo = colorMapper.toVo(saved);
			return colorVo;
		}

		ColorVo existingVoByName = findByNameIgnoreCase(colorVo.getColorName());
		if (existingVoByName != null) {
			existingVoByName.setActive(colorVo.getActive());
			ColorEntity colorEntity = colorMapper.toEntity(existingVoByName);
			ColorEntity saved = colorRepository.save(colorEntity);
			colorVo = colorMapper.toVo(saved);
			return colorVo;
		}

		//New Request
		colorVo.setId(null);
		colorVo.setActive(Boolean.TRUE);
		colorVo.setCreatedOn(LocalDateTime.now());
		ColorEntity colorEntity = colorMapper.toEntity(colorVo);
		ColorEntity saved = colorRepository.save(colorEntity);
		colorVo = colorMapper.toVo(saved);
		return colorVo;
	}

	@Override
	public Set<ColorVo> saveAll(Set<ColorVo> colorVos) {
		
		//check duplicate design name
		Set<String> colorNames = new HashSet<>();
		List<String> duplicates = colorVos
									.stream()
									.map(ColorVo::getColorName)
									.map(String::toUpperCase)
									.filter(name -> !colorNames.add(name))
									.toList();
		
		if(!duplicates.isEmpty()) {
			throw new BusinessException("Duplicate values in payload :: " + duplicates);
		}
						
		List<ColorEntity> entities = colorVos.stream().map(colorMapper::toEntity).collect(Collectors.toList());
		entities = colorRepository.saveAll(entities);
		Set<ColorVo> saved = entities.stream().map(colorMapper::toVo).collect(Collectors.toSet());
		return saved;
	}

	@Override
	public List<ColorVo> getAll() {
		return colorRepository.findAll().stream().map(colorMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<ColorVo> findByNameIgnoreCaseLike(String colorName) {
		return colorRepository.findByNameIgnoreCaseLike(colorName).stream().map(colorMapper::toVo).collect(Collectors.toList());
	}
	
	@Override
	public ColorVo findByNameIgnoreCase(String colorName) {
	    ColorEntity entity = colorRepository.findByNameIgnoreCase(colorName);
	    return entity != null ? colorMapper.toVo(entity) : null;
	}

	@Override
	public ColorVo getById(Long id) {
		return colorRepository.findById(id).map(colorMapper::toVo).orElse(null);
	}

	@Override
	public ColorVo updateStatus(Long id, boolean status) {
		Optional<ColorEntity> optional = colorRepository.findById(id);
		if (optional.isPresent()) {
			ColorEntity existing = optional.get();
			existing.setActive(status);
			ColorEntity updated = colorRepository.save(existing);
			return colorMapper.toVo(updated);
		}
		return null;
	}

}