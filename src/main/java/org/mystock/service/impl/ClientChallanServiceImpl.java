package org.mystock.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.ClientChallanEntity;
import org.mystock.mapper.ClientChallanMapper;
import org.mystock.mapper.ColorMapper;
import org.mystock.mapper.DesignMapper;
import org.mystock.repository.ClientChallanRepository;
import org.mystock.service.ClientChallanService;
import org.mystock.service.StockService;
import org.mystock.vo.ClientChallanVo;
import org.mystock.vo.StockVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientChallanServiceImpl implements ClientChallanService {

	private final ClientChallanRepository repository;
	private final ClientChallanMapper mapper;
	private final StockService stockService;
	private final DesignMapper designMapper;
	private final ColorMapper colorMapper;

	@Transactional
	@Override
	public ClientChallanVo save(ClientChallanVo vo) {

		ClientChallanEntity entity = mapper.toEntity(vo);
		final ClientChallanEntity savedEntity = repository.save(entity);

		if (entity.getId() != null) {
			savedEntity.getChallanItems().stream().forEach(item -> {

				final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChallanType());
				final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChallanType());

				if (isReceive) {

					//received finished products from client because of rejection or any other reason
					
					//update the available stock balance :: increase the available stock :: plus entry in StockInfo
					StockVo stockVo = stockService.get(item.getDesign().getId(),
							item.getColor().getId());
					if (stockVo != null) {
						stockService.increaseBalance(item.getDesign().getId(), item.getColor().getId(),
								item.getQuantity());
					} else {
						stockVo = new StockVo();
						stockVo.setDesign(designMapper.toVo(item.getDesign()));
						stockVo.setColor(colorMapper.toVo(item.getColor()));
						stockVo.setBalance(item.getQuantity());
						stockService.save(stockVo);
					}
					
					//update the contractor stock balance :: reduce the pending balance of contractor :: minus entry in ContractorStockInfo
					
				}
				if (isIssue) {
					//issuing finished products to client
					
					//update the available stock balance :: reduce the available stock :: minus entry in StockInfo
					StockVo stockVo = stockService.get(item.getDesign().getId(),
							item.getColor().getId());
					if (stockVo != null) {
						stockService.reduceBalance(item.getDesign().getId(), item.getColor().getId(),
								item.getQuantity());
					} else {
						stockVo = new StockVo();
						stockVo.setDesign(designMapper.toVo(item.getDesign()));
						stockVo.setColor(colorMapper.toVo(item.getColor()));
						stockVo.setBalance(0 - item.getQuantity());
						stockService.save(stockVo);
					}

				}
			});
		}

		return mapper.toVo(savedEntity);
	}

	@Transactional
	@Override
	public Set<ClientChallanVo> saveAll(Set<ClientChallanVo> vos) {
		if (vos == null || vos.isEmpty())
			return Collections.emptySet();
		List<ClientChallanEntity> entities = vos.stream().map(mapper::toEntity).collect(Collectors.toList());
		entities = repository.saveAll(entities);
		entities.parallelStream().forEach(savedEntity -> {

			if (savedEntity.getId() != null) {
				savedEntity.getChallanItems().stream().forEach(item -> {

					final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChallanType());
					final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChallanType());

					if (isReceive) {

						//received finished products from client because of rejection or any other reason
						
						//update the available stock balance :: increase the available stock :: plus entry in StockInfo
						

						StockVo stockVo = stockService.get(item.getDesign().getId(),
								item.getColor().getId());
						if (stockVo != null) {
							stockService.increaseBalance(item.getDesign().getId(), item.getColor().getId(),
									item.getQuantity());
						} else {
							stockVo = new StockVo();
							stockVo.setDesign(designMapper.toVo(item.getDesign()));
							stockVo.setColor(colorMapper.toVo(item.getColor()));
							stockVo.setBalance(item.getQuantity());
							stockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
							stockService.save(stockVo);
						}
					}
					if (isIssue) {
						//issuing finished products to client
						
						//update the available stock balance :: reduce the available stock :: minus entry in StockInfo
						
						StockVo stockVo = stockService.get(item.getDesign().getId(),
								item.getColor().getId());
						if (stockVo != null) {
							stockService.reduceBalance(item.getDesign().getId(), item.getColor().getId(),
									item.getQuantity());
						} else {
							stockVo = new StockVo();
							stockVo.setDesign(designMapper.toVo(item.getDesign()));
							stockVo.setColor(colorMapper.toVo(item.getColor()));
							stockVo.setBalance(0 - item.getQuantity());
							stockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
							stockService.save(stockVo);
						}

					}
				});
			}

		});
		return entities.stream().map(mapper::toVo).collect(Collectors.toSet());
	}

	@Override
	public ClientChallanVo findById(Long id) {
		ClientChallanVo clientChallanVo = null;
		Optional<ClientChallanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			clientChallanVo = mapper.toVo(optional.get());
		}
		return clientChallanVo;
	}

	@Transactional
	public ClientChallanVo deleteById(Long id) {

		ClientChallanVo challanVo = findById(id);
		if (challanVo != null) {
			final ClientChallanEntity entity = mapper.toEntity(challanVo);
			repository.deleteById(id);

			if (entity.getId() != null) {
				entity.getChallanItems().stream().forEach(item -> {

					final boolean isReceive = "R".equalsIgnoreCase(entity.getChallanType());
					final boolean isIssue = "I".equalsIgnoreCase(entity.getChallanType());

					if (isReceive) {

						StockVo stockVo = stockService.get(item.getDesign().getId(),
								item.getColor().getId());
						if (stockVo != null) {
							// reverse entry
							stockService.reduceBalance(item.getDesign().getId(), item.getColor().getId(),
									item.getQuantity());
						} else {
							stockVo = new StockVo();
							stockVo.setDesign(designMapper.toVo(item.getDesign()));
							stockVo.setColor(colorMapper.toVo(item.getColor()));
							stockVo.setBalance(item.getQuantity());
							stockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
							stockService.save(stockVo);
						}
					}
					if (isIssue) {
						StockVo stockVo = stockService.get(item.getDesign().getId(),
								item.getColor().getId());
						if (stockVo != null) {// reverse entry
							stockService.increaseBalance(item.getDesign().getId(), item.getColor().getId(),
									item.getQuantity());
						} else {
							stockVo = new StockVo();
							stockVo.setDesign(designMapper.toVo(item.getDesign()));
							stockVo.setColor(colorMapper.toVo(item.getColor()));
							stockVo.setBalance(item.getQuantity());
							stockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
							stockService.save(stockVo);
						}

					}
				});
			}
		}
		return challanVo;
	}

	@Override
	public List<ClientChallanVo> findAll(Integer challanNumber, Long clientId, LocalDate fromChallanDate,
			LocalDate toChallanDate, String challanType) {

		return repository.findAll(challanNumber, clientId, fromChallanDate, toChallanDate,
				challanType).stream()
				.map(mapper::toVo).collect(Collectors.toList());
	}

	private LocalDateTime toLocalDateTime(Long epochMillis) {
		if (epochMillis == null)
			return null;
		return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
