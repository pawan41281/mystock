package org.mystock.repository;

import java.util.List;

import org.mystock.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {

	public List<StockEntity> findByDesign_Id(Long designId);

	public StockEntity findByDesign_IdAndColor_Id(Long designId, Long colorId);

	@Modifying
	@Transactional
	@Query("""
			UPDATE StockEntity d 
			SET d.balance = d.balance + :quantity, 
			d.updatedOn = CURRENT_TIMESTAMP 
			WHERE d.design.id = :designId 
			AND d.color.id = :colorId		       
			""")
	public int increaseBalance(Long designId, Long colorId, Integer quantity);

	@Modifying
	@Transactional
	@Query("""
			UPDATE StockEntity d 
			SET d.balance = d.balance - :quantity, 
			d.updatedOn = CURRENT_TIMESTAMP 
			WHERE d.design.id = :designId 
			AND d.color.id = :colorId		       
			""")
	public int reduceBalance(Long designId, Long colorId, Integer quantity);

}
