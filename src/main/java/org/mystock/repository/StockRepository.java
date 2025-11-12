package org.mystock.repository;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.mystock.entity.StockEntity;
import org.mystock.vo.DesignStockReportVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {

	List<StockEntity> findByDesign_Id(Long designId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	StockEntity findByDesign_IdAndColor_IdAndQuality_Id(Long qualityId, Long designId, Long colorId);

	@Modifying
	@Transactional
	@Query("""
			UPDATE StockEntity d
			SET d.balance = d.balance + :quantity,
			d.updatedOn = CURRENT_TIMESTAMP
			WHERE d.design.id = :designId
			AND d.color.id = :colorId 
			AND d.quality.id = :qualityId
			""")
	int increaseBalance(Long qualityId, Long designId, Long colorId, Integer quantity);

	@Modifying
	@Transactional
	@Query("""
			UPDATE StockEntity d
			SET d.balance = d.balance - :quantity,
			d.updatedOn = CURRENT_TIMESTAMP
			WHERE d.design.id = :designId
			AND d.color.id = :colorId 
			AND d.quality.id = :qualityId
			""")
	int reduceBalance(Long qualityId, Long designId, Long colorId, Integer quantity);

	@Query(value = """
			SELECT
			    UPPER(d.design) AS designName,
			    UPPER(c.color_name) AS colorName,
			    UPPER(q.quality_name) AS qualityName,
			    COALESCE(s.obalance, 0) AS openingBalance,
			    COALESCE(s.balance, 0) AS closingBalance
			FROM
			    design_info d
			CROSS JOIN
			    color_info c
			CROSS JOIN
			    quality_info q
			LEFT JOIN
			    stock_info s ON s.design_id = d.id AND s.color_id = c.id AND s.quality_id = q.id
			WHERE
			    d.design LIKE :designName
			    AND
			    c.color_name LIKE :colorName
			    AND
			    q.quality_name LIKE :qualityName
			""", nativeQuery = true)
	List<DesignStockReportVo> getDesignStockReport(
			@Param("qualityName") String qualityName,
			@Param("designName") String designName,
			@Param("colorName") String colorName);
	
	@Query(value = """
			SELECT
			    UPPER(d.design) AS designName,
			    UPPER(c.color_name) AS colorName,
			    UPPER(q.quality_name) AS qualityName,
			    COALESCE(s.obalance, 0) AS openingBalance,
			    COALESCE(s.balance, 0) AS closingBalance
			FROM
			    design_info d
			CROSS JOIN
			    color_info c
			CROSS JOIN
			    quality_info q
			LEFT JOIN
			    stock_info s ON s.design_id = d.id AND s.color_id = c.id AND s.quality_id = q.id
			WHERE
			    d.design LIKE :designName
			    AND
			    c.color_name LIKE :colorName
			    AND
			    q.quality_name LIKE :qualityName
			    AND
			    s.balance<>0
			""", nativeQuery = true)
	List<DesignStockReportVo> getDesignStockNonZeroReport(
			@Param("qualityName") String qualityName,
			@Param("designName") String designName,
			@Param("colorName") String colorName);

	@Query(value = """
			SELECT COUNT(*)
			FROM
			    design_info d
			CROSS JOIN
			    color_info c
			CROSS JOIN
			    quality_info q
			LEFT JOIN
			    stock_info s ON s.design_id = d.id AND s.color_id = c.id AND s.quality_id = q.id
			WHERE
			    d.design LIKE :designName
			    AND
			    c.color_name LIKE :colorName
			    AND
			    q.quality_name LIKE :qualityName
			""", nativeQuery = true)
	int getDesignStockCount(
			@Param("qualityName") String qualityName,
			@Param("designName") String designName,
			@Param("colorName") String colorName);

	@Query(value = """
			SELECT
			    UPPER(q.quality_name) AS qualityName,
			    UPPER(d.design) AS designName,
			    UPPER(c.color_name) AS colorName,
			    COALESCE(s.balance, 0) AS stockBalance
			FROM
			    design_info d
			CROSS JOIN
			    color_info c
			CROSS JOIN
			    quality_info q
			LEFT JOIN
			    stock_info s ON s.design_id = d.id AND s.color_id = c.id
			WHERE
			    d.design LIKE :designName
			    AND
			    c.color_name LIKE :colorName
			    AND
			    q.quality_name LIKE :qualityName 
			LIMIT :pageSize OFFSET :pageCount
			""", nativeQuery = true)
	List<DesignStockReportVo> getDesignStockReport(
			@Param("qualityName") String qualityName,
			@Param("designName") String designName,
			@Param("colorName") String colorName,
			@Param("pageSize") Integer pageSize,
			@Param("pageCount") Integer pageCount);

}