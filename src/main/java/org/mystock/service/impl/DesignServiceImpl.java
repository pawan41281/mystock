package org.mystock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mystock.dto.DesignDto;
import org.mystock.mapper.DesignMapper;
import org.mystock.repositoty.DesignRepository;
import org.mystock.service.DesignService;
import org.mystock.vo.DesignVo;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DesignServiceImpl implements DesignService {

	private final DesignMapper designMapper;
	private final DesignRepository designRepository;

	@Override
	public List<DesignVo> list() {
		List<DesignVo> voList = new ArrayList<>();
		List<DesignDto> dtoList = designRepository.findAll();
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(designMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public DesignVo save(DesignVo designVo) {
		DesignDto designDto = designMapper.convert(designVo);
		designDto = designRepository.save(designDto);
		designVo = designMapper.convert(designDto);
		return designVo;

	}

	@Override
	public List<DesignVo> findByDesignIgnoreCase(String design) {
		List<DesignVo> voList = new ArrayList<>();
		List<DesignDto> dtoList = designRepository.findByDesignIgnoreCase(design);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(designMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<DesignVo> findByColorIgnoreCase(String color) {
		List<DesignVo> voList = new ArrayList<>();
		List<DesignDto> dtoList = designRepository.findByColorIgnoreCase(color);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(designMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<DesignVo> findByStatusIgnoreCase(boolean active) {
		List<DesignVo> voList = new ArrayList<>();
		List<DesignDto> dtoList = designRepository.findByStatusIgnoreCase(active);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(designMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public List<DesignVo> findByDesignIgnoreCaseOrColorIgnoreCaseOrStatus(String design, String color,
			boolean active) {
		List<DesignVo> voList = new ArrayList<>();
		List<DesignDto> dtoList = designRepository.findByDesignIgnoreCaseOrColorIgnoreCaseOrStatus(design, color, active);
		if (dtoList != null && dtoList.size() > 0) {
			dtoList.stream().forEach(dto -> {
				voList.add(designMapper.convert(dto));
			});
		}
		return voList;
	}

	@Override
	public DesignVo findById(Long Id) {
		DesignVo designVo = null;
		Optional<DesignDto> optionalDto = designRepository.findById(Id);
		if(optionalDto.isPresent()) {
			designVo = designMapper.convert(optionalDto.get());
		}
		return designVo;
	}

}
