package org.mystock.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mystock.entity.ContractorStockEntity;
import org.mystock.mapper.ColorMapper;
import org.mystock.mapper.ContractorMapper;
import org.mystock.mapper.ContractorStockMapper;
import org.mystock.mapper.DesignMapper;
import org.mystock.repository.ContractorStockRepository;
import org.mystock.service.ColorService;
import org.mystock.service.ContractorService;
import org.mystock.service.ContractorStockService;
import org.mystock.service.DesignService;
import org.mystock.vo.ContractorStockVo;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContractorStockServiceImpl implements ContractorStockService {

	private final ContractorStockRepository contractorStockRepository;
	private final DesignService designService;
	private final ColorService colorService;
	private final ContractorService contractorService;
	private final ContractorStockMapper contractorStockMapper;
	private final ContractorMapper contractorMapper;
	private final DesignMapper designMapper;
	private final ColorMapper colorMapper;

	@Override
	public ContractorStockVo save(ContractorStockVo contractorStockVo) {
		ContractorStockEntity entity = contractorStockRepository
				.save(contractorStockMapper.toEntity(contractorStockVo));
		return contractorStockMapper.toVo(entity);
	}

	@Override
	public ContractorStockVo getById(Long id) {
		Optional<ContractorStockEntity> optionalEntity = contractorStockRepository.findById(id);
		return optionalEntity.isPresent() ? contractorStockMapper.toVo(optionalEntity.get()) : null;
	}

	@Override
	public List<ContractorStockVo> getAll() {
		return contractorStockRepository.findAll().stream().map(contractorStockMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorStockVo> getAll(Long designId) {
		return contractorStockRepository.findByDesign_Id(designId).stream().map(contractorStockMapper::toVo)
				.collect(Collectors.toList());
	}

	@Override
	public List<ContractorStockVo> getAll(Long contractorId, Long designId) {
		return contractorStockRepository.findByContractor_IdAndDesign_Id(contractorId, designId).stream()
				.map(contractorStockMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public ContractorStockVo get(Long contractorId, Long designId, Long colorId) {
		ContractorStockEntity entity = contractorStockRepository.findByContractor_IdAndDesign_IdAndColor_Id(contractorId,
				designId, colorId);
		if (entity != null)
			return contractorStockMapper.toVo(entity);

		return null;
	}

	@Override
	public List<ContractorStockVo> getAllDesignAndColor(Long designId, Long colorId) {
		return contractorStockRepository.findByDesign_IdAndColor_Id(designId, colorId).stream()
				.map(contractorStockMapper::toVo).collect(Collectors.toList());
	}

	@Override
	public int increaseBalance(Long contractorId, Long designId, Long colorId, Integer quantity) {
		return contractorStockRepository.increaseBalance(contractorId, designId, colorId, quantity);
	}

	@Override
	public int reduceBalance(Long contractorId, Long designId, Long colorId, Integer quantity) {
		return contractorStockRepository.reduceBalance(contractorId, designId, colorId, quantity);
	}

	@Override
	@Transactional
	public ContractorStockVo addOpenningBalance(Long contractorId, Long designId, Long colorId, Integer quantity) {
		ContractorStockVo existingContractorStockVo = get(contractorId, designId, colorId);
		if (existingContractorStockVo != null) {
			
			Integer existingOpeningBalance = existingContractorStockVo.getOpeningBalance();
			Integer existingClosingBalance = existingContractorStockVo.getBalance();
			
			existingContractorStockVo.setOpeningBalance(quantity);
			existingContractorStockVo.setBalance(existingClosingBalance - existingOpeningBalance + quantity);
			existingContractorStockVo.setUpdatedOn(LocalDateTime.now());
			return save(existingContractorStockVo);
		} else {
			ContractorStockVo contractorStockVo = new ContractorStockVo();
			contractorStockVo.setOpeningBalance(quantity);
			contractorStockVo.setBalance(quantity);
			contractorStockVo.setColor(colorService.getById(colorId));
			contractorStockVo.setContractor(contractorService.getById(contractorId));
			contractorStockVo.setDesign(designService.getById(designId));
			return save(contractorStockVo);
		}

	}

	@Override
	@Transactional
	public List<ContractorStockVo> addOpenningBalance(Set<ContractorStockVo> vos) {
		List<ContractorStockEntity> entities = new ArrayList<>();
		vos.stream().forEach(vo -> {
			ContractorStockEntity entity = contractorStockRepository.findByContractor_IdAndDesign_IdAndColor_Id(
					vo.getContractor().getId(), vo.getDesign().getId(), vo.getColor().getId());
			if (entity == null) {
				entity = new ContractorStockEntity();
				entity.setBalance(vo.getOpeningBalance());
				entity.setBalance(vo.getOpeningBalance());
			}else {
				
				Integer existingOpeningBalance = entity.getOpeningBalance();
				Integer existingClosingBalance = entity.getBalance();
				
				entity.setOpeningBalance(vo.getOpeningBalance());
				entity.setBalance(existingClosingBalance - existingOpeningBalance + vo.getOpeningBalance());
				
			}
			entity.setContractor(contractorMapper.toEntity(vo.getContractor()));
			entity.setDesign(designMapper.toEntity(vo.getDesign()));
			entity.setColor(colorMapper.toEntity(vo.getColor()));
			entity.setUpdatedOn(LocalDateTime.now());
			entities.add(entity);
		});
		return contractorStockRepository.saveAll(entities).stream().map(contractorStockMapper::toVo)
				.collect(Collectors.toList());
	}

}
