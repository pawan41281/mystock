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

import org.mystock.entity.ContractorChallanEntity;
import org.mystock.mapper.ColorMapper;
import org.mystock.mapper.ContractorChallanMapper;
import org.mystock.mapper.ContractorMapper;
import org.mystock.mapper.DesignMapper;
import org.mystock.repository.ContractorChallanRepository;
import org.mystock.service.ContractorChallanService;
import org.mystock.service.ContractorStockService;
import org.mystock.service.StockService;
import org.mystock.vo.ContractorChallanVo;
import org.mystock.vo.ContractorStockVo;
import org.mystock.vo.StockVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorChallanServiceImpl implements ContractorChallanService {

	private final ContractorChallanRepository repository;
	private final ContractorChallanMapper mapper;

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
	public ContractorChallanVo save(ContractorChallanVo vo) {

		ContractorChallanEntity entity = mapper.toEntity(vo);
		final ContractorChallanEntity savedEntity = repository.save(entity);

		if (entity.getId() != null) {
			savedEntity.getChallanItems().stream().forEach(item -> {

				final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChallanType());
				final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChallanType());

				if (isReceive) {

					// received finished products from contractor

					// update the available stock balance :: increase the available stock :: plus
					// entry in StockInfo
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

					// update the contractor stock balance :: reduce the pending balance of
					// contractor :: minus entry in ContractorStockInfo
					ContractorStockVo contractorStockVo = contractorStockService.get(entity.getContractor().getId(),
							item.getDesign().getId(), item.getColor().getId());
					if (contractorStockVo != null) {
						contractorStockService.reduceBalance(entity.getContractor().getId(), item.getDesign().getId(),
								item.getColor().getId(), item.getQuantity());
					} else {
						contractorStockVo = new ContractorStockVo();
						contractorStockVo.setContractor(contractorMapper.toVo(entity.getContractor()));
						contractorStockVo.setDesign(designMapper.toVo(item.getDesign()));
						contractorStockVo.setColor(colorMapper.toVo(item.getColor()));
						contractorStockVo.setBalance((0 - item.getQuantity()));
						contractorStockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
						contractorStockService.save(contractorStockVo);
					}
				}
				if (isIssue) {

					// issuing raw material to contractor

					// update the available stock balance :: no impact on available stock :: no
					// entry in StockInfo
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

					// update the contractor stock balance :: increase the pending balance of
					// contractor :: plus entry in ContractorStockInfo
					ContractorStockVo contractorStockVo = contractorStockService.get(entity.getContractor().getId(),
							item.getDesign().getId(), item.getColor().getId());
					if (contractorStockVo != null) {
						contractorStockService.increaseBalance(entity.getContractor().getId(), item.getDesign().getId(),
								item.getColor().getId(), item.getQuantity());
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
	public Set<ContractorChallanVo> saveAll(Set<ContractorChallanVo> vos) {
		if (vos == null || vos.isEmpty())
			return Collections.emptySet();
		List<ContractorChallanEntity> entities = vos.stream().map(mapper::toEntity).collect(Collectors.toList());
		entities = repository.saveAll(entities);
		entities.stream().forEach(savedEntity -> {

			if (savedEntity.getId() != null) {
				savedEntity.getChallanItems().stream().forEach(item -> {

					final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChallanType());
					final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChallanType());

					if (isReceive) {

						// received finished products from contractor

						// update the available stock balance :: increase the available stock :: plus
						// entry in StockInfo

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

						// update the contractor stock balance :: reduce the pending balance of
						// contractor :: minus entry in ContractorStockInfo
						ContractorStockVo contractorStockVo = contractorStockService.get(
								savedEntity.getContractor().getId(), item.getDesign().getId(), item.getColor().getId());
						if (contractorStockVo != null) {
							contractorStockService.reduceBalance(savedEntity.getContractor().getId(),
									item.getDesign().getId(), item.getColor().getId(), item.getQuantity());
						} else {
							contractorStockVo = new ContractorStockVo();
							contractorStockVo.setContractor(contractorMapper.toVo(savedEntity.getContractor()));
							contractorStockVo.setDesign(designMapper.toVo(item.getDesign()));
							contractorStockVo.setColor(colorMapper.toVo(item.getColor()));
							contractorStockVo.setBalance((0 - item.getQuantity()));
							contractorStockVo.setUpdatedOn(toLocalDateTime(System.currentTimeMillis()));
							contractorStockService.save(contractorStockVo);
						}
					}
					if (isIssue) {

						// issuing raw material to contractor

						// update the available stock balance :: no impact on available stock :: no
						// entry in StockInfo

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

						// update the contractor stock balance :: increase the pending balance of
						// contractor :: plus entry in ContractorStockInfo
						ContractorStockVo contractorStockVo = contractorStockService.get(
								savedEntity.getContractor().getId(), item.getDesign().getId(), item.getColor().getId());
						if (contractorStockVo != null) {
							contractorStockService.increaseBalance(savedEntity.getContractor().getId(),
									item.getDesign().getId(), item.getColor().getId(), item.getQuantity());
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
	public ContractorChallanVo findById(Long id) {
		ContractorChallanVo contractorChallanVo = null;
		Optional<ContractorChallanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			contractorChallanVo = mapper.toVo(optional.get());
		}
		return contractorChallanVo;
	}

	@Transactional
	public ContractorChallanVo deleteById(Long id) {

		ContractorChallanVo challanVo = findById(id);
		if (challanVo != null) {
			final ContractorChallanEntity entity = mapper.toEntity(challanVo);
			repository.deleteById(id);

			if (entity.getId() != null) {
				entity.getChallanItems().stream().forEach(item -> {

					final boolean isReceive = "R".equalsIgnoreCase(entity.getChallanType());
					final boolean isIssue = "I".equalsIgnoreCase(entity.getChallanType());

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
		return challanVo;
	}

	@Override
	public List<ContractorChallanVo> findAll(Integer challanNumber, Long contractorId, LocalDate fromChallanDate,
			LocalDate toChallanDate, String challanType) {

		return repository.findAll(challanNumber, contractorId, fromChallanDate, toChallanDate, challanType).stream()
				.map(mapper::toVo).collect(Collectors.toList());
	}

	private LocalDateTime toLocalDateTime(Long epochMillis) {
		if (epochMillis == null)
			return null;
		return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
