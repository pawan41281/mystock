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

import org.mystock.entity.ContractorChalaanEntity;
import org.mystock.mapper.ColorMapper;
import org.mystock.mapper.ContractorChalaanMapper;
import org.mystock.mapper.ContractorMapper;
import org.mystock.mapper.DesignMapper;
import org.mystock.repository.ContractorChalaanRepository;
import org.mystock.service.ContractorChalaanService;
import org.mystock.service.ContractorStockService;
import org.mystock.service.StockService;
import org.mystock.vo.ContractorChalaanVo;
import org.mystock.vo.ContractorStockVo;
import org.mystock.vo.StockVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorChalaanServiceImpl implements ContractorChalaanService {

	private final ContractorChalaanRepository repository;
	private final ContractorChalaanMapper mapper;
	
	private final ContractorStockService contractorStockService;
	private final StockService stockService;
//	private final ColorService colorService;
//	private final DesignService designService;
//	private final ContractorService contractorService;
	
	private final DesignMapper designMapper;
	private final ContractorMapper contractorMapper;
	private final ColorMapper colorMapper;
//	private final ContractorStockMapper contractorStockMapper;
//	private final StockMapper stockMapper;

	@Transactional
	@Override
	public ContractorChalaanVo save(ContractorChalaanVo vo) {

		ContractorChalaanEntity entity = mapper.toEntity(vo);
		final ContractorChalaanEntity savedEntity = repository.save(entity);

		if (entity.getId() != null) {
			savedEntity.getChalaanItems().stream().forEach(item -> {

				final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChalaanType());
				final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChalaanType());

				if (isReceive) {
					
					//received finished products from contractor
					
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
					

					//update the contractor stock balance :: reduce the pending balance of contractor :: minus entry in ContractorStockInfo
					ContractorStockVo contractorStockVo = contractorStockService.get(entity.getContractor().getId(), item.getDesign().getId(),
							item.getColor().getId());
					if (contractorStockVo != null) {
						contractorStockService.reduceBalance(entity.getContractor().getId(), item.getDesign().getId(), item.getColor().getId(),
								item.getQuantity());
					} else {
						contractorStockVo = new ContractorStockVo();
						contractorStockVo.setContractor(contractorMapper.toVo(entity.getContractor()));
						contractorStockVo.setDesign(designMapper.toVo(item.getDesign()));
						contractorStockVo.setColor(colorMapper.toVo(item.getColor()));
						contractorStockVo.setBalance((0-item.getQuantity()));
						contractorStockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
						contractorStockService.save(contractorStockVo);
					}
				}
				if (isIssue) {
					
					//issuing raw material to contractor
					
					//update the available stock balance :: no impact on available stock :: no entry in StockInfo
//					StockVo stockVo = stockService.get(item.getDesign().getId(),
//							item.getColor().getId());
//					if (stockVo != null) {
//						stockService.reduceBalance(item.getDesign().getId(), item.getColor().getId(),
//								item.getQuantity());
//					} else {
//						stockVo = new StockVo();
//						stockVo.setDesign(designMapper.toVo(item.getDesign()));
//						stockVo.setColor(colorMapper.toVo(item.getColor()));
//						stockVo.setBalance(0 - item.getQuantity());
//						stockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
//						stockService.save(stockVo);
//					}
					
					

					//update the contractor stock balance :: increase the pending balance of contractor :: plus entry in ContractorStockInfo
					ContractorStockVo contractorStockVo = contractorStockService.get(entity.getContractor().getId(), item.getDesign().getId(),
							item.getColor().getId());
					if (contractorStockVo != null) {
						contractorStockService.increaseBalance(entity.getContractor().getId(), item.getDesign().getId(), item.getColor().getId(),
								item.getQuantity());
					} else {
						contractorStockVo = new ContractorStockVo();
						contractorStockVo.setContractor(contractorMapper.toVo(entity.getContractor()));
						contractorStockVo.setDesign(designMapper.toVo(item.getDesign()));
						contractorStockVo.setColor(colorMapper.toVo(item.getColor()));
						contractorStockVo.setBalance(item.getQuantity());
						contractorStockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
						contractorStockService.save(contractorStockVo);
					}
				}
			});
		}

		return mapper.toVo(savedEntity);
	}

	@Transactional
	@Override
	public Set<ContractorChalaanVo> saveAll(Set<ContractorChalaanVo> vos) {
		if (vos == null || vos.isEmpty())
			return Collections.emptySet();
		List<ContractorChalaanEntity> entities = vos.stream().map(mapper::toEntity).collect(Collectors.toList());
		entities = repository.saveAll(entities);
		entities.stream().forEach(savedEntity -> {

			if (savedEntity.getId() != null) {
				savedEntity.getChalaanItems().stream().forEach(item -> {

					final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChalaanType());
					final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChalaanType());

					if (isReceive) {
						
						//received finished products from contractor
						
						//update the available stock balance :: increase the available stock :: plus entry in StockInfo

						StockVo stockVo = stockService.get(item.getDesign().getId(), item.getColor().getId());
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
						

						//update the contractor stock balance :: reduce the pending balance of contractor :: minus entry in ContractorStockInfo
						ContractorStockVo contractorStockVo = contractorStockService.get(savedEntity.getContractor().getId(), item.getDesign().getId(),
								item.getColor().getId());
						if (contractorStockVo != null) {
							contractorStockService.reduceBalance(savedEntity.getContractor().getId(), item.getDesign().getId(), item.getColor().getId(),
									item.getQuantity());
						} else {
							contractorStockVo = new ContractorStockVo();
							contractorStockVo.setContractor(contractorMapper.toVo(savedEntity.getContractor()));
							contractorStockVo.setDesign(designMapper.toVo(item.getDesign()));
							contractorStockVo.setColor(colorMapper.toVo(item.getColor()));
							contractorStockVo.setBalance((0-item.getQuantity()));
							contractorStockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
							contractorStockService.save(contractorStockVo);
						}
					}
					if (isIssue) {
						
						//issuing raw material to contractor
						
						//update the available stock balance :: no impact on available stock :: no entry in StockInfo
						
//						StockVo stockVo = stockService.get(item.getDesign().getId(), item.getColor().getId());
//						if (stockVo != null) {
//							stockService.reduceBalance(item.getDesign().getId(), item.getColor().getId(),
//									item.getQuantity());
//						} else {
//							stockVo = new StockVo();
//							stockVo.setDesign(designMapper.toVo(item.getDesign()));
//							stockVo.setColor(colorMapper.toVo(item.getColor()));
//							stockVo.setBalance(0 - item.getQuantity());
//							stockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
//							stockService.save(stockVo);
//						}
						
						

						//update the contractor stock balance :: increase the pending balance of contractor :: plus entry in ContractorStockInfo
						ContractorStockVo contractorStockVo = contractorStockService.get(savedEntity.getContractor().getId(), item.getDesign().getId(),
								item.getColor().getId());
						if (contractorStockVo != null) {
							contractorStockService.increaseBalance(savedEntity.getContractor().getId(), item.getDesign().getId(), item.getColor().getId(),
									item.getQuantity());
						} else {
							contractorStockVo = new ContractorStockVo();
							contractorStockVo.setContractor(contractorMapper.toVo(savedEntity.getContractor()));
							contractorStockVo.setDesign(designMapper.toVo(item.getDesign()));
							contractorStockVo.setColor(colorMapper.toVo(item.getColor()));
							contractorStockVo.setBalance(item.getQuantity());
							contractorStockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
							contractorStockService.save(contractorStockVo);
						}

					}
				});
			}

		});
		return entities.stream().map(mapper::toVo).collect(Collectors.toSet());
	}

	@Override
	public ContractorChalaanVo findById(Long id) {
		ContractorChalaanVo contractorChalaanVo = null;
		Optional<ContractorChalaanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			contractorChalaanVo = mapper.toVo(optional.get());
		}
		return contractorChalaanVo;
	}

	@Transactional
	public ContractorChalaanVo deleteById(Long id) {

		ContractorChalaanVo chalaanVo = findById(id);
		if (chalaanVo != null) {
			final ContractorChalaanEntity entity = mapper.toEntity(chalaanVo);
			repository.deleteById(id);

			if (entity.getId() != null) {
				entity.getChalaanItems().stream().forEach(item -> {

					final boolean isReceive = "R".equalsIgnoreCase(entity.getChalaanType());
					final boolean isIssue = "I".equalsIgnoreCase(entity.getChalaanType());

					if (isReceive) {

						StockVo stockVo = stockService.get(item.getDesign().getId(), item.getColor().getId());
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
						StockVo stockVo = stockService.get(item.getDesign().getId(), item.getColor().getId());
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
		return chalaanVo;
	}

	@Override
	public List<ContractorChalaanVo> findAll(Integer chalaanNumber, Long contractorId, LocalDate fromChalaanDate,
			LocalDate toChalaanDate, String chalaanType) {

		return repository.findAll(chalaanNumber, contractorId, fromChalaanDate, toChalaanDate, chalaanType).stream()
				.map(mapper::toVo).collect(Collectors.toList());
	}

	private LocalDateTime toLocalDateTime(Long epochMillis) {
		if (epochMillis == null)
			return null;
		return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
