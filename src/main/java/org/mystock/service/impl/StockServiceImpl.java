package org.mystock.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.StockEntity;
import org.mystock.mapper.ColorMapper;
import org.mystock.mapper.DesignMapper;
import org.mystock.mapper.StockMapper;
import org.mystock.repository.StockRepository;
import org.mystock.service.ColorService;
import org.mystock.service.DesignService;
import org.mystock.service.StockService;
import org.mystock.vo.StockVo;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StockServiceImpl implements StockService {

	private final StockRepository stockRepository;
	private final DesignService designService;
	private final ColorService colorService;
	private final StockMapper stockMapper;
	private final DesignMapper designMapper;
	private final ColorMapper colorMapper;

	@Override
	public StockVo save(StockVo stockVo) {
		StockEntity entity = stockRepository.save(stockMapper.toEntity(stockVo));
		return stockMapper.toVo(entity);
	}

	@Override
	public StockVo getById(Long id) {
		Optional<StockEntity> optionalEntity = stockRepository.findById(id);
		return optionalEntity.isPresent() ? stockMapper.toVo(optionalEntity.get()) : null;
	}

	@Override
	public List<StockVo> getAll() {
		return stockRepository.findAll().stream().map(stockMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public List<StockVo> getAllNonZero() {
		return stockRepository.findAll().stream().filter(vo -> !vo.getBalance().equals(0)).map(stockMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<StockVo> getAll(Long designId) {
		return stockRepository.findByDesign_Id(designId).stream().map(stockMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public StockVo get(Long designId, Long colorId) {
		StockEntity stockEntity = stockRepository.findByDesign_IdAndColor_Id(designId, colorId);
		if (stockEntity != null)
			return stockMapper.toVo(stockEntity);

		return null;
	}

	@Override
	public int increaseBalance(Long designId, Long colorId, Integer quantity) {
		return stockRepository.increaseBalance(designId, colorId, quantity);
	}

	@Override
	public int reduceBalance(Long designId, Long colorId, Integer quantity) {
		return stockRepository.reduceBalance(designId, colorId, quantity);
	}

	@Override
	@Transactional
	public StockVo addOpenningBalance(Long designId, Long colorId, Integer quantity) {
		StockVo existingStockVo = get(designId, colorId);// design id + color id - should be unique
		if (existingStockVo == null) {
			StockVo stockVo = new StockVo();
			stockVo.setOpeningBalance(quantity);
			stockVo.setBalance(quantity);
			stockVo.setColor(colorService.getById(colorId));
			stockVo.setDesign(designService.getById(designId));
			return save(stockVo);

		} else {
			int existingopeningbalance = existingStockVo.getOpeningBalance();
			int existingbalance = existingStockVo.getBalance();
			existingStockVo.setOpeningBalance(quantity);
			existingStockVo.setBalance(existingbalance - existingopeningbalance + quantity);
			existingStockVo.setUpdatedOn(LocalDateTime.now());
		}
		return save(existingStockVo);
	}

	@Override
	@Transactional
	public List<StockVo> addOpenningBalance(Set<StockVo> vos) {
		List<StockEntity> entities = new ArrayList<>();
		vos.stream().forEach(vo -> {
			StockEntity entity = stockRepository.findByDesign_IdAndColor_Id(vo.getDesign().getId(),
					vo.getColor().getId());
			if (entity == null) {
				entity = new StockEntity();
				entity.setBalance(vo.getOpeningBalance());
			} else {
				int existingopeningbalance = entity.getOpeningBalance();
				int existingbalance = entity.getBalance();
				entity.setBalance(existingbalance - existingopeningbalance + vo.getOpeningBalance());
			}

			entity.setOpeningBalance(vo.getOpeningBalance());
			entity.setDesign(designMapper.toEntity(vo.getDesign()));
			entity.setColor(colorMapper.toEntity(vo.getColor()));
			entity.setUpdatedOn(LocalDateTime.now());
			entities.add(entity);
		});
		return stockRepository.saveAll(entities).stream().map(stockMapper::toVo).collect(Collectors.toList());
	}

}
