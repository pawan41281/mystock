package org.mystock.repository;

import java.util.List;

import org.mystock.entity.StockEntity;
import org.mystock.vo.ContractorStockReportVo;
import org.mystock.vo.DesignStockReportVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {

	public List<StockEntity> findByDesign_Id(Long designId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
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

	@Query(value = """
			WITH stock_balance AS(
			SELECT
			    d.description AS designName,
			    c.colorname AS colorName,
			    COALESCE(s.balance, 0) AS stockBalance
			FROM
			    designinfo d
			CROSS JOIN
			    colorinfo c
			LEFT JOIN
			    stockinfo s ON s.design_id = d.id AND s.color_id = c.id
			WHERE
			    d.description LIKE :designName
			    AND
			    c.colorname LIKE :colorName)
			SELECT * FROM stock_balance
			""", nativeQuery = true)
	public List<DesignStockReportVo> getDesignStockReport(@Param("designName") String designName,
			@Param("colorName") String colorName);

	@Query(value = """
			WITH stock_balance AS(
			SELECT
			  c.id AS contractorId,
			  c.contractorname AS contractorName,
			  d.id AS designId,
			  d.description AS designName,
			  clr.colorname AS colorName,
			  COALESCE(cs.balance, 0) AS stockBalance
			FROM
			  contractorinfo c
			CROSS JOIN
			  designinfo d
			CROSS JOIN
			  colorinfo clr
			LEFT JOIN
			  contractorstockinfo cs ON cs.contractor_id = c.id AND cs.design_id = d.id AND cs.color_id = clr.id
			WHERE
			  c.contractorname like :contractorName
			  AND
			  d.description LIKE :designName
			  AND
			  clr.colorname LIKE :colorName)
			SELECT * FROM stock_balance
			""", nativeQuery = true)
	public List<ContractorStockReportVo> getContractorStockReport(String contractorName, String designName,
			String colorName);

}
