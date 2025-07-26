package org.mystock.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.ColorEntity;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.mapper.ColorMapper;
import org.mystock.repository.ColorRepository;
import org.mystock.service.ColorService;
import org.mystock.vo.ColorVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

	private final ColorRepository colorRepository;
	private final ColorMapper colorMapper;

	@Override
	public ColorVo save(ColorVo colorVo) {
		if (colorVo.getId() != null) {// update request
			ColorVo existingVo = getById(colorVo.getId());
			if (existingVo == null)
				throw new ResourceNotFoundException("Invalid ID :: ".concat(String.valueOf(colorVo.getId())));
			if (colorVo.getColorName() == null)
				colorVo.setColorName(existingVo.getColorName());
			if (colorVo.getActive() == null)
				colorVo.setActive(existingVo.getActive());
			colorVo.setCreatedOn(existingVo.getCreatedOn());
		} else {// new request
			colorVo.setId(null);
			if (colorVo.getActive() == null)
				colorVo.setActive(Boolean.TRUE);
			if (colorVo.getCreatedOn() == null)
				colorVo.setCreatedOn(LocalDateTime.now());
		}
		ColorEntity saved = colorRepository.save(colorMapper.toEntity(colorVo));
		return colorMapper.toVo(saved);
	}

	@Override
	public Set<ColorVo> saveAll(Set<ColorVo> colorVos) {
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
