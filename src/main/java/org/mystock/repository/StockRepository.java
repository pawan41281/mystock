package org.mystock.repository;

import java.util.List;

import org.mystock.entity.StockEntity;
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
			SELECT
			    UPPER(d.design) AS designName,
			    UPPER(c.colorname) AS colorName,
			    COALESCE(s.obalance, 0) AS openingBalance,
			    COALESCE(s.balance, 0) AS closingBalance
			FROM
			    designinfo d
			CROSS JOIN
			    colorinfo c
			LEFT JOIN
			    stockinfo s ON s.design_id = d.id AND s.color_id = c.id
			WHERE
			    d.description LIKE :designName
			    AND
			    c.colorname LIKE :colorName
			""", nativeQuery = true)
	public List<DesignStockReportVo> getDesignStockReport(@Param("designName") String designName,
			@Param("colorName") String colorName);
	
	@Query(value = """
			SELECT
			    UPPER(d.design) AS designName,
			    UPPER(c.colorname) AS colorName,
			    COALESCE(s.obalance, 0) AS openingBalance,
			    COALESCE(s.balance, 0) AS closingBalance
			FROM
			    designinfo d
			CROSS JOIN
			    colorinfo c
			LEFT JOIN
			    stockinfo s ON s.design_id = d.id AND s.color_id = c.id
			WHERE
			    d.description LIKE :designName
			    AND
			    c.colorname LIKE :colorName
			    AND
			    s.balance<>0
			""", nativeQuery = true)
	public List<DesignStockReportVo> getDesignStockNonZeroReport(@Param("designName") String designName,
			@Param("colorName") String colorName);

	@Query(value = """
			SELECT COUNT(*)
			FROM designinfo d
			CROSS JOIN colorinfo c
			LEFT JOIN stockinfo s ON s.design_id = d.id AND s.color_id = c.id
			WHERE d.description LIKE :designName
			  AND c.colorname LIKE :colorName
			""", nativeQuery = true)
	public int getDesignStockCount(@Param("designName") String designName, @Param("colorName") String colorName);

	@Query(value = """
			SELECT
			    UPPER(d.design) AS designName,
			    UPPER(c.colorname) AS colorName,
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
			    c.colorname LIKE :colorName
			LIMIT :pageSize OFFSET :pageCount
			""", nativeQuery = true)
	public List<DesignStockReportVo> getDesignStockReport(@Param("designName") String designName,
			@Param("colorName") String colorName, @Param("pageSize") Integer pageSize,
			@Param("pageCount") Integer pageCount);

}
