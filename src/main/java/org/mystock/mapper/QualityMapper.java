package org.mystock.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.mystock.entity.QualityEntity;
import org.mystock.vo.QualityVo;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QualityMapper {

	private final ModelMapper modelMapper;

	public QualityVo toVo(QualityEntity qualityEntity) {

		return modelMapper.map(qualityEntity, QualityVo.class);
	}

	public QualityEntity toEntity(QualityVo qualityVo) {
		return modelMapper.map(qualityVo, QualityEntity.class);
	}

}