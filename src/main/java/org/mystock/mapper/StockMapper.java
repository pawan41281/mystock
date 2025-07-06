package org.mystock.mapper;

import org.modelmapper.ModelMapper;
import org.mystock.entity.StockEntity;
import org.mystock.vo.StockVo;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class StockMapper {

	private final ModelMapper modelMapper;

	public StockVo toVo(StockEntity stockEntity) {
		return modelMapper.map(stockEntity, StockVo.class);
	}

	public StockEntity toEntity(StockVo stockVo) {
		return modelMapper.map(stockVo, StockEntity.class);
	}

}
