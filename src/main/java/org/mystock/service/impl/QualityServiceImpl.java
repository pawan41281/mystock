package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import org.mystock.entity.QualityEntity;
import org.mystock.exception.BusinessException;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.mapper.QualityMapper;
import org.mystock.repository.QualityRepository;
import org.mystock.service.QualityService;
import org.mystock.vo.QualityVo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QualityServiceImpl implements QualityService {

	private final QualityRepository qualityRepository;
	private final QualityMapper qualityMapper;

	@Override
	public QualityVo save(QualityVo qualityVo) {

		if (qualityVo.getId() != null && !qualityVo.getId().equals(0L)) {// update request
			QualityVo existingVoById = getById(qualityVo.getId());
			if (existingVoById == null)
				throw new ResourceNotFoundException("Invalid ID :: ".concat(String.valueOf(qualityVo.getId())));
			if (qualityVo.getQualityName() == null)
				qualityVo.setQualityName(existingVoById.getQualityName());
			if (qualityVo.getActive() == null)
				qualityVo.setActive(existingVoById.getActive());
			qualityVo.setCreatedOn(existingVoById.getCreatedOn());
			qualityVo.setUser(existingVoById.getUser());
			QualityEntity qualityEntity = qualityMapper.toEntity(qualityVo);
			QualityEntity saved = qualityRepository.save(qualityEntity);
			qualityVo = qualityMapper.toVo(saved);
			return qualityVo;
		}

		QualityVo existingVoByName = findByNameIgnoreCase(qualityVo.getQualityName());
		if (existingVoByName != null) {
			existingVoByName.setActive(qualityVo.getActive());
			QualityEntity qualityEntity = qualityMapper.toEntity(existingVoByName);
			QualityEntity saved = qualityRepository.save(qualityEntity);
			qualityVo = qualityMapper.toVo(saved);
			return qualityVo;
		}

		//New Request
		qualityVo.setId(null);
		qualityVo.setActive(Boolean.TRUE);
		qualityVo.setCreatedOn(LocalDateTime.now());
		QualityEntity qualityEntity = qualityMapper.toEntity(qualityVo);
		QualityEntity saved = qualityRepository.save(qualityEntity);
		qualityVo = qualityMapper.toVo(saved);
		return qualityVo;
	}

	@Override
	public Set<QualityVo> saveAll(Set<QualityVo> qualityVos) {
		
		//check duplicate  name
		Set<String> qualityNames = new HashSet<>();
		List<String> duplicates = qualityVos
									.stream()
									.map(QualityVo::getQualityName)
									.map(String::toUpperCase)
									.filter(name -> !qualityNames.add(name))
									.toList();
		
		if(!duplicates.isEmpty()) {
			throw new BusinessException("Duplicate values in payload :: " + duplicates);
		}
						
		List<QualityEntity> entities = qualityVos.stream().map(qualityMapper::toEntity).collect(Collectors.toList());
		entities = qualityRepository.saveAll(entities);
		Set<QualityVo> saved = entities.stream().map(qualityMapper::toVo).collect(Collectors.toSet());
		return saved;
	}

	@Override
	public List<QualityVo> getAll() {
		return qualityRepository.findAll().stream().map(qualityMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<QualityVo> getAll(Boolean active) {
		List<QualityEntity> list = qualityRepository.findAll();
		if(active!=null && (active.equals(true) || active.equals(false)))
			list = list.stream().filter(q -> q.isActive()==active).collect(Collectors.toUnmodifiableList());
		return list.stream().map(qualityMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<QualityVo> findByNameIgnoreCaseLike(String qualityName) {
		return qualityRepository.findByNameIgnoreCaseLike(qualityName).stream().map(qualityMapper::toVo).collect(Collectors.toList());
	}
	
	@Override
	public QualityVo findByNameIgnoreCase(String qualityName) {
	    QualityEntity entity = qualityRepository.findByNameIgnoreCase(qualityName);
	    return entity != null ? qualityMapper.toVo(entity) : null;
	}

	@Override
	public QualityVo getById(Long id) {
		return qualityRepository.findById(id).map(qualityMapper::toVo).orElse(null);
	}

	@Override
	public QualityVo updateStatus(Long id, boolean status) {
		Optional<QualityEntity> optional = qualityRepository.findById(id);
		if (optional.isPresent()) {
			QualityEntity existing = optional.get();
			existing.setActive(status);
			QualityEntity updated = qualityRepository.save(existing);
			return qualityMapper.toVo(updated);
		}
		return null;
	}

}