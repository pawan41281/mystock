package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import org.mystock.entity.ContractorPaymentEntity;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.mapper.ContractorPaymentMapper;
import org.mystock.repository.ContractorPaymentRepository;
import org.mystock.service.ContractorPaymentService;
import org.mystock.vo.ContractorPaymentVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContractorPaymentServiceImpl implements ContractorPaymentService {

	private final ContractorPaymentRepository repository;
	private final ContractorPaymentMapper mapper;

	@Transactional
	@Override
	public ContractorPaymentVo save(ContractorPaymentVo vo) {
		ContractorPaymentEntity entity = mapper.toEntity(vo);
		final ContractorPaymentEntity savedEntity = repository.save(entity);
		return mapper.toVo(savedEntity);
	}

	@Transactional
	@Override
	public Set<ContractorPaymentVo> saveAll(Set<ContractorPaymentVo> vos) {
		if (vos == null || vos.isEmpty())
			return Collections.emptySet();
		List<ContractorPaymentEntity> entities = vos.stream().map(mapper::toEntity).collect(Collectors.toList());
		entities = repository.saveAll(entities);
		return entities.stream().map(mapper::toVo).collect(Collectors.toSet());
	}

	@Override
	public ContractorPaymentVo findById(Long id) {
		ContractorPaymentVo contractorPaymentVo = null;
		Optional<ContractorPaymentEntity> optional = repository.findById(id);
		if (optional.isPresent()) {
			contractorPaymentVo = mapper.toVo(optional.get());
		}
		return contractorPaymentVo;
	}

	@Transactional
	public ContractorPaymentVo deleteById(Long id) {

		ContractorPaymentVo paymentVo = findById(id);
		if (paymentVo != null) {
			repository.deleteById(id);
		}else{
			throw new ResourceNotFoundException("Payment transaction not exist");
		}
		return paymentVo;
	}

	@Override
	public List<ContractorPaymentVo> findAll(LocalDate paymentDateStart, LocalDate paymentDateEnd, Integer paymentAmountStart, Integer paymentAmountEnd, Long id) {
		return repository.findByPaymentDateBetweenOrPaymentAmountBetweenOrContractor_IdOrderByPaymentDateDescContractor_IdAscPaymentAmountDesc(paymentDateStart, paymentDateEnd, paymentAmountStart, paymentAmountEnd, id).stream()
				.map(mapper::toVo).collect(Collectors.toList());
	}

}