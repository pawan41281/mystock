package org.mystock.repository;

import java.util.List;

import org.mystock.entity.ContractorStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

@Repository
public interface ContractorStockRepository extends JpaRepository<ContractorStockEntity, Long> {

	public List<ContractorStockEntity> findByContractor_Id(Long contractorId);

	public List<ContractorStockEntity> findByContractor_IdAndDesign_Id(Long contractorId, Long designId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public ContractorStockEntity findByContractor_IdAndDesign_IdAndColor_Id(Long contractorId, Long designId,
			Long colorId);

	public List<ContractorStockEntity> findByDesign_IdAndColor_Id(Long designId, Long colorId);

	public List<ContractorStockEntity> findByDesign_Id(Long designId);

	@Modifying
	@Transactional
	@Query("""
			UPDATE ContractorStockEntity d
			SET d.balance = d.balance + :quantity,
			d.updatedOn = CURRENT_TIMESTAMP
			WHERE d.contractor.id = :contractorId
			and d.design.id = :designId
			AND d.color.id = :colorId
			""")
	public int increaseBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

	@Modifying
	@Transactional
	@Query("""
			UPDATE ContractorStockEntity d
			SET d.balance = d.balance - :quantity,
			d.updatedOn = CURRENT_TIMESTAMP
			WHERE d.contractor.id = :contractorId
			and d.design.id = :designId
			AND d.color.id = :colorId
			""")
	public int reduceBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

}
