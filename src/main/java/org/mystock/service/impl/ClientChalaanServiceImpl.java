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

import org.mystock.entity.ClientChalaanEntity;
import org.mystock.mapper.ClientChalaanMapper;
import org.mystock.mapper.ColorMapper;
import org.mystock.mapper.DesignMapper;
import org.mystock.repository.ClientChalaanRepository;
import org.mystock.service.ClientChalaanService;
import org.mystock.service.StockService;
import org.mystock.vo.ClientChalaanVo;
import org.mystock.vo.StockVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ClientChalaanServiceImpl implements ClientChalaanService {

	private final ClientChalaanRepository repository;
	private final ClientChalaanMapper mapper;
	private final StockService stockService;
	private final DesignMapper designMapper;
	private final ColorMapper colorMapper;

	@Transactional
	@Override
	public ClientChalaanVo save(ClientChalaanVo vo) {

		ClientChalaanEntity entity = mapper.toEntity(vo);
		final ClientChalaanEntity savedEntity = repository.save(entity);

		if (entity.getId() != null) {
			savedEntity.getChalaanItems().stream().forEach(item -> {

				final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChalaanType());
				final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChalaanType());

				if (isReceive) {

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
				}
				if (isIssue) {
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
	public Set<ClientChalaanVo> saveAll(Set<ClientChalaanVo> vos) {
		if (vos == null || vos.isEmpty())
			return Collections.emptySet();
		List<ClientChalaanEntity> entities = vos.stream().map(mapper::toEntity).collect(Collectors.toList());
		entities = repository.saveAll(entities);
		entities.parallelStream().forEach(savedEntity -> {

			if (savedEntity.getId() != null) {
				savedEntity.getChalaanItems().stream().forEach(item -> {

					final boolean isReceive = "R".equalsIgnoreCase(savedEntity.getChalaanType());
					final boolean isIssue = "I".equalsIgnoreCase(savedEntity.getChalaanType());

					if (isReceive) {

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
	public ClientChalaanVo findById(Long id) {
		ClientChalaanVo clientChalaanVo = null;
		Optional<ClientChalaanEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			clientChalaanVo = mapper.toVo(optional.get());
		}
		return clientChalaanVo;
	}

	@Transactional
	public ClientChalaanVo deleteById(Long id) {

		ClientChalaanVo chalaanVo = findById(id);
		if (chalaanVo != null) {
			final ClientChalaanEntity entity = mapper.toEntity(chalaanVo);
			repository.deleteById(id);

			if (entity.getId() != null) {
				entity.getChalaanItems().stream().forEach(item -> {

					final boolean isReceive = "R".equalsIgnoreCase(entity.getChalaanType());
					final boolean isIssue = "I".equalsIgnoreCase(entity.getChalaanType());

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
		return chalaanVo;
	}

	@Override
	public List<ClientChalaanVo> findAll(Integer chalaanNumber, Long clientId, LocalDate fromChalaanDate,
			LocalDate toChalaanDate, String chalaanType) {

		return repository.findAll(chalaanNumber, clientId, fromChalaanDate, toChalaanDate, chalaanType).stream()
				.map(mapper::toVo).collect(Collectors.toList());
	}

	private LocalDateTime toLocalDateTime(Long epochMillis) {
		if (epochMillis == null)
			return null;
		return Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
