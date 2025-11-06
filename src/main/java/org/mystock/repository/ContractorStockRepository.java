package org.mystock.repository;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.mystock.entity.ContractorStockEntity;
import org.mystock.vo.ContractorStockReportVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractorStockRepository extends JpaRepository<ContractorStockEntity, Long> {

	//List<ContractorStockEntity> findByContractor_Id(Long contractorId);

	//List<ContractorStockEntity> findByContractor_IdAndDesign_Id(Long contractorId, Long designId);

	//@Lock(LockModeType.PESSIMISTIC_WRITE)
	//ContractorStockEntity findByContractor_IdAndDesign_IdAndColor_Id(Long contractorId, Long designId, Long colorId);

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	ContractorStockEntity findByContractor_IdAndDesign_IdAndColor_IdAndQuality_Id(Long contractorId, Long designId, Long colorId, Long qualityId);

	//List<ContractorStockEntity> findByDesign_IdAndColor_Id(Long designId, Long colorId);

	//List<ContractorStockEntity> findByDesign_IdAndColor_IdAndQuality_Id(Long designId, Long colorId, Long qualityId);

	//List<ContractorStockEntity> findByDesign_Id(Long designId);

	@Modifying
	@Transactional
	@Query("""
			UPDATE ContractorStockEntity d
			SET d.balance = d.balance + :quantity,
			d.updatedOn = CURRENT_TIMESTAMP
			WHERE d.contractor.id = :contractorId
			and d.design.id = :designId
			AND d.color.id = :colorId
			AND d.quality.id = :qualityId
			""")
	int increaseBalance(Long contractorId, Long designId, Long colorId, Long qualityId, Integer quantity);

//	@Modifying
//	@Transactional
//	@Query("""
//			UPDATE ContractorStockEntity d
//			SET d.balance = d.balance + :quantity,
//			d.updatedOn = CURRENT_TIMESTAMP
//			WHERE d.contractor.id = :contractorId
//			and d.design.id = :designId
//			AND d.color.id = :colorId
//			""")
//	int increaseBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

//	@Modifying
//	@Transactional
//	@Query("""
//			UPDATE ContractorStockEntity d
//			SET d.balance = d.balance - :quantity,
//			d.updatedOn = CURRENT_TIMESTAMP
//			WHERE d.contractor.id = :contractorId
//			and d.design.id = :designId
//			AND d.color.id = :colorId
//			""")
//	int reduceBalance(Long contractorId, Long designId, Long colorId, Integer quantity);

	@Modifying
	@Transactional
	@Query("""
			UPDATE ContractorStockEntity d
			SET d.balance = d.balance - :quantity,
			d.updatedOn = CURRENT_TIMESTAMP
			WHERE d.contractor.id = :contractorId
			and d.design.id = :designId
			AND d.color.id = :colorId
			AND d.quality.id = :qualityId
			""")
	int reduceBalance(Long contractorId, Long designId, Long colorId, Long qualityId, Integer quantity);

//	@Query(value = """
//			SELECT
//			  c.id AS contractorId,
//			  UPPER(c.contractor_name) AS contractorName,
//			  d.id AS designId,
//			  UPPER(d.design) AS designName,
//			  UPPER(clr.color_name) AS colorName,
//			  COALESCE(cs.obalance, 0) AS openingBalance,
//			  COALESCE(cs.balance, 0) AS closingBalance
//			FROM
//			  contractor_info c
//			CROSS JOIN
//			  design_info d
//			CROSS JOIN
//			  color_info clr
//			LEFT JOIN
//			  contractor_stock_info cs ON cs.contractor_id = c.id AND cs.design_id = d.id AND cs.color_id = clr.id
//			WHERE
//			  c.contractor_name like :contractorName
//			  AND
//			  d.description LIKE :designName
//			  AND
//			  clr.color_name LIKE :colorName
//			""", nativeQuery = true)
//	List<ContractorStockReportVo> getContractorStockReport(String contractorName, String designName, String colorName);

	@Query(value = """
			SELECT
			  c.id AS contractorId,
			  UPPER(c.contractor_name) AS contractorName,
			  d.id AS designId,
			  UPPER(d.design) AS designName,
			  UPPER(clr.color_name) AS colorName,
			  UPPER(q.quality_name) AS qualityName,
			  COALESCE(cs.obalance, 0) AS openingBalance,
			  COALESCE(cs.balance, 0) AS closingBalance
			FROM
			  contractor_info c
			CROSS JOIN
			  design_info d
			CROSS JOIN
			  color_info clr
			CROSS JOIN
			  quality_info q
			LEFT JOIN
			  contractor_stock_info cs ON cs.contractor_id = c.id AND cs.design_id = d.id AND cs.color_id = clr.id AND cs.quality_id = q.id
			WHERE
			  c.contractor_name like :contractorName
			  AND
			  d.design LIKE :designName
			  AND
			  clr.color_name LIKE :colorName
			  AND
			  q.quality_name LIKE :qualityName
			""", nativeQuery = true)
	List<ContractorStockReportVo> getContractorStockReport(String contractorName, String designName, String colorName, String qualityName);

//	@Query(value = """
//			SELECT
//			  c.id AS contractorId,
//			  UPPER(c.contractor_name) AS contractorName,
//			  d.id AS designId,
//			  UPPER(d.design) AS designName,
//			  UPPER(clr.color_name) AS colorName,
//			  COALESCE(cs.obalance, 0) AS openingBalance,
//			  COALESCE(cs.balance, 0) AS closingBalance
//			FROM
//			  contractor_info c
//			CROSS JOIN
//			  design_info d
//			CROSS JOIN
//			  color_info clr
//			LEFT JOIN
//			  contractor_stock_info cs ON cs.contractor_id = c.id AND cs.design_id = d.id AND cs.color_id = clr.id
//			WHERE
//			  c.contractor_name like :contractorName
//			  AND
//			  d.description LIKE :designName
//			  AND
//			  clr.color_name LIKE :colorName
//			  AND
//			  cs.balance<>0
//			""", nativeQuery = true)
//	List<ContractorStockReportVo> getContractorNonZeroStockReport(String contractorName, String designName, String colorName);

	@Query(value = """
			SELECT
			  c.id AS contractorId,
			  UPPER(c.contractor_name) AS contractorName,
			  d.id AS designId,
			  UPPER(d.design) AS designName,
			  UPPER(clr.color_name) AS colorName,
			  UPPER(q.quality_name) AS qualityName,
			  COALESCE(cs.obalance, 0) AS openingBalance,
			  COALESCE(cs.balance, 0) AS closingBalance
			FROM
			  contractor_info c
			CROSS JOIN
			  design_info d
			CROSS JOIN
			  color_info clr
			CROSS JOIN
			  quality_info q
			LEFT JOIN
			  contractor_stock_info cs ON cs.contractor_id = c.id AND cs.design_id = d.id AND cs.color_id = clr.id AND cs.quality_id = q.id
			WHERE
			  c.contractor_name like :contractorName
			  AND
			  d.design LIKE :designName
			  AND
			  clr.color_name LIKE :colorName
			  AND
			  q.quality_name LIKE :qualityName
			  AND
			  cs.balance<>0
			""", nativeQuery = true)
	List<ContractorStockReportVo> getContractorNonZeroStockReport(String contractorName, String designName, String colorName, String qualityName);

//	@Query(value = """
//			SELECT
//			  c.id AS contractorId,
//			  UPPER(c.contractor_name) AS contractorName,
//			  d.id AS designId,
//			  UPPER(d.design) AS designName,
//			  UPPER(clr.color_name) AS colorName,
//			  UPPER(q.color_name) AS qualityName,
//			  COALESCE(cs.obalance, 0) AS openingBalance,
//			  COALESCE(cs.balance, 0) AS closingBalance
//			FROM
//			  contractor_info c
//			CROSS JOIN
//			  design_info d
//			CROSS JOIN
//			  color_info clr
//			LEFT JOIN
//			  contractor_stock_info cs ON cs.contractor_id = c.id AND cs.design_id = d.id AND cs.color_id = clr.id
//			WHERE
//			  c.contractor_name like :contractorName
//			  AND
//			  d.description LIKE :designName
//			  AND
//			  clr.color_name LIKE :colorName
//			LIMIT :pageSize OFFSET :pageCount
//			""", nativeQuery = true)
//	List<ContractorStockReportVo> getContractorStockReport(String contractorName, String designName, String colorName, Integer pageSize, Integer pageCount);

	@Query(value = """
			SELECT
			  c.id AS contractorId,
			  UPPER(c.contractor_name) AS contractorName,
			  d.id AS designId,
			  UPPER(d.design) AS designName,
			  UPPER(clr.color_name) AS colorName,
			  UPPER(q.quality_name) AS qualityName,
			  COALESCE(cs.obalance, 0) AS openingBalance,
			  COALESCE(cs.balance, 0) AS closingBalance
			FROM
			  contractor_info c
			CROSS JOIN
			  design_info d
			CROSS JOIN
			  color_info clr
			CROSS JOIN
			  quality_info q
			LEFT JOIN
			  contractor_stock_info cs ON cs.contractor_id = c.id AND cs.design_id = d.id AND cs.color_id = clr.id AND cs.quality_id = q.id
			WHERE
			  c.contractor_name like :contractorName
			  AND
			  d.design LIKE :designName
			  AND
			  clr.color_name LIKE :colorName
			  AND
			  q.quality_name LIKE :qualityName
			LIMIT :pageSize OFFSET :pageCount
			""", nativeQuery = true)
	List<ContractorStockReportVo> getContractorStockReport(String contractorName, String designName, String colorName, String qualityName, Integer pageSize, Integer pageCount);

//	@Query(value = """
//			SELECT
//			  count(*)
//			FROM
//			  contractor_info c
//			CROSS JOIN
//			  design_info d
//			CROSS JOIN
//			  color_info clr
//			LEFT JOIN
//			  contractor_stock_info cs ON cs.contractor_id = c.id AND cs.design_id = d.id AND cs.color_id = clr.id
//			WHERE
//			  c.contractor_name like :contractorName
//			  AND
//			  d.description LIKE :designName
//			  AND
//			  clr.color_name LIKE :colorName
//			""", nativeQuery = true)
//	int getContractorStockCount(String contractorName, String designName, String colorName);

	@Query(value = """
			SELECT
			  count(*)
			FROM
			  contractor_info c
			CROSS JOIN
			  design_info d
			CROSS JOIN
			  color_info clr
			CROSS JOIN
			  quality_info q
			LEFT JOIN
			  contractor_stock_info cs ON cs.contractor_id = c.id AND cs.design_id = d.id AND cs.color_id = clr.id AND cs.quality_id = q.id
			WHERE
			  c.contractor_name like :contractorName
			  AND
			  d.design LIKE :designName
			  AND
			  clr.color_name LIKE :colorName
			  AND
			  q.quality_name LIKE :qualityName
			""", nativeQuery = true)
	int getContractorStockCount(String contractorName, String designName, String colorName, String qualityName);

}