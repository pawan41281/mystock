package org.mystock.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.mystock.entity.DesignEntity;
import org.mystock.mapper.DesignMapper;
import org.mystock.repositoty.DesignRepository;
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
		DesignEntity designEntity = designMapper.convert(designVo);
		designEntity = designRepository.save(designEntity);
		return designMapper.convert(designEntity);
	}

	@Override
	public DesignVo getById(Long id) {
		Optional<DesignEntity> optional = designRepository.findById(id);
		return optional.map(designMapper::convert).orElse(null);
	}

	@Override
	public List<DesignVo> getByStatus(boolean status) {
		List<DesignEntity> entities = designRepository.findByActive(status);
		return entities.stream().map(designMapper::convert).collect(Collectors.toList());
	}

	@Override
	public List<DesignVo> getAll() {
		List<DesignEntity> entities = designRepository.findAll();
		return entities.stream().map(designMapper::convert).collect(Collectors.toList());
	}

	@Override
	public DesignVo update(Long id, boolean status) {
		Optional<DesignEntity> optional = designRepository.findById(id);
		if (optional.isPresent()) {
			DesignEntity existing = optional.get();
			existing.setActive(status);
			DesignEntity updated = designRepository.save(existing);
			return designMapper.convert(updated);
		}
		return null;
	}

}
