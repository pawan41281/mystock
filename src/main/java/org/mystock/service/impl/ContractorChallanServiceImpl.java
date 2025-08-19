package org.mystock.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.ContractorChallanEntity;
import org.mystock.exception.ResourceNotFoundException;
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

			final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChallanType());
			final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChallanType());

			savedEntity.getChallanItems().stream().forEach(item -> {

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
//						stockVo.setUpdatedOn(LocalDateTime.now());
//						stockService.save(stockVo);
//					}

					// update the contractor stock balance :: increase the pending balance of
					// contractor :: plus entry in ContractorStockInfo
					// pending balance + receive pieces
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
						contractorStockVo.setUpdatedOn(LocalDateTime.now());
						contractorStockService.save(contractorStockVo);
					}
				}

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
						stockVo.setUpdatedOn(LocalDateTime.now());
						stockService.save(stockVo);
					}

					// update the contractor stock balance :: reduce the pending balance of
					// contractor :: minus entry in ContractorStockInfo
					// pending balance - receive pieces
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
						contractorStockVo.setUpdatedOn(LocalDateTime.now());
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

				final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChallanType());
				final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChallanType());

				savedEntity.getChallanItems().stream().forEach(item -> {

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
//							stockVo.setUpdatedOn(LocalDateTime.now());
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
							contractorStockVo.setUpdatedOn(LocalDateTime.now());
							contractorStockService.save(contractorStockVo);
						}

					}

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
							stockVo.setUpdatedOn(LocalDateTime.now());
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
							contractorStockVo.setUpdatedOn(LocalDateTime.now());
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

			final ContractorChallanEntity contractorChallanEntity = mapper.toEntity(challanVo);

			if (contractorChallanEntity.getId() != null) {

				final boolean isReceive = "R".equalsIgnoreCase(contractorChallanEntity.getChallanType());
				final boolean isIssue = "I".equalsIgnoreCase(contractorChallanEntity.getChallanType());

				repository.deleteById(id);

				contractorChallanEntity.getChallanItems().stream().forEach(item -> {

					if (isIssue) {

						// reverse entry for issued products from contractor against cancel challan

						// update the available stock balance :: reduce the available stock :: minus
						// entry in StockInfo

//						StockVo stockVo = stockService.get(item.getDesign().getId(), item.getColor().getId());
//
//						if (stockVo != null) {// reverse entry
//							stockService.increaseBalance(item.getDesign().getId(), item.getColor().getId(),
//									item.getQuantity());
//						} else {
//							stockVo = new StockVo();
//							stockVo.setDesign(designMapper.toVo(item.getDesign()));
//							stockVo.setColor(colorMapper.toVo(item.getColor()));
//							stockVo.setBalance(item.getQuantity());
//							stockVo.setUpdatedOn(LocalDateTime.now());
//							stockService.save(stockVo);
//						}

						// update the contractor stock balance :: increase the pending balance of
						// contractor :: plus entry in ContractorStockInfo
						// reduce the pending balance of the contractor

						ContractorStockVo contractorStockVo = contractorStockService.get(
								contractorChallanEntity.getContractor().getId(), item.getDesign().getId(),
								item.getColor().getId());

						if (contractorStockVo != null) {
							// reverse entry
							contractorStockService.reduceBalance(contractorChallanEntity.getContractor().getId(),
									item.getDesign().getId(), item.getColor().getId(), item.getQuantity());
						} else {
							contractorStockVo = new ContractorStockVo();
							contractorStockVo.setBalance((0 - item.getQuantity()));
							contractorStockVo.setColor(colorMapper.toVo(item.getColor()));
							contractorStockVo.setDesign(designMapper.toVo(item.getDesign()));
							contractorStockVo
									.setContractor(contractorMapper.toVo(contractorChallanEntity.getContractor()));
							contractorStockVo.setUpdatedOn(LocalDateTime.now());
							contractorStockService.save(contractorStockVo);
						}

					}

					if (isReceive) {

						// reverse entry for received finished products from contractor

						// update the available stock balance :: reduce the available stock :: minus
						// entry in StockInfo
						// reduce the available stock balance
						StockVo stockVo = stockService.get(item.getDesign().getId(), item.getColor().getId());

						if (stockVo != null) {
							// reverse entry
							stockService.reduceBalance(item.getDesign().getId(), item.getColor().getId(),
									item.getQuantity());
						} else {
							stockVo = new StockVo();
							stockVo.setDesign(designMapper.toVo(item.getDesign()));
							stockVo.setColor(colorMapper.toVo(item.getColor()));
							stockVo.setBalance((0 - item.getQuantity()));
							stockVo.setUpdatedOn(LocalDateTime.now());
							stockService.save(stockVo);
						}

						// update the contractor stock balance :: increase the pending balance of
						// contractor :: plus entry in ContractorStockInfo

						ContractorStockVo contractorStockVo = contractorStockService.get(
								contractorChallanEntity.getContractor().getId(), item.getDesign().getId(),
								item.getColor().getId());

						if (contractorStockVo != null) {
							// reverse entry
							contractorStockService.increaseBalance(contractorChallanEntity.getContractor().getId(),
									item.getDesign().getId(), item.getColor().getId(), item.getQuantity());
						} else {
							contractorStockVo = new ContractorStockVo();
							contractorStockVo.setBalance(item.getQuantity());
							contractorStockVo.setColor(colorMapper.toVo(item.getColor()));
							contractorStockVo.setDesign(designMapper.toVo(item.getDesign()));
							contractorStockVo
									.setContractor(contractorMapper.toVo(contractorChallanEntity.getContractor()));
							contractorStockVo.setUpdatedOn(LocalDateTime.now());
							contractorStockService.save(contractorStockVo);
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

	@Override
	public List<ContractorChallanVo> getRecentChallans(String challanType) {
		
		if(challanType!=null && !challanType.equalsIgnoreCase("I") && !challanType.equalsIgnoreCase("R"))
			throw new ResourceNotFoundException("Challan type is invalid :: " + challanType);
		
		challanType = challanType==null?"%":challanType;
		
		return repository.getRecentChallans(LocalDate.now(), challanType).stream().map(mapper::toVo).collect(Collectors.toList());
	}

}
