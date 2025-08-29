package org.mystock.repository;

import java.util.List;

import org.mystock.entity.ContractorStockEntity;
import org.mystock.vo.ContractorStockReportVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

	@Query(value = """
			SELECT
			  c.id AS contractorId,
			  UPPER(c.contractorname) AS contractorName,
			  d.id AS designId,
			  UPPER(d.design) AS designName,
			  UPPER(clr.colorname) AS colorName,
			  COALESCE(cs.obalance, 0) AS openingBalance,
			  COALESCE(cs.balance, 0) AS closingBalance
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
			  clr.colorname LIKE :colorName
			""", nativeQuery = true)
	public List<ContractorStockReportVo> getContractorStockReport(String contractorName, String designName,
			String colorName);

	@Query(value = """
			SELECT
			  c.id AS contractorId,
			  UPPER(c.contractorname) AS contractorName,
			  d.id AS designId,
			  UPPER(d.design) AS designName,
			  UPPER(clr.colorname) AS colorName,
			  COALESCE(cs.obalance, 0) AS openingBalance,
			  COALESCE(cs.balance, 0) AS closingBalance
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
			  clr.colorname LIKE :colorName
			  AND
			  cs.balance<>0
			""", nativeQuery = true)
	public List<ContractorStockReportVo> getContractorNonZeroStockReport(String contractorName, String designName,
			String colorName);

	@Query(value = """
			SELECT
			  c.id AS contractorId,
			  UPPER(c.contractorname) AS contractorName,
			  d.id AS designId,
			  UPPER(d.design) AS designName,
			  UPPER(clr.colorname) AS colorName,
			  COALESCE(cs.obalance, 0) AS openingBalance,
			  COALESCE(cs.balance, 0) AS closingBalance
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
			  clr.colorname LIKE :colorName
			LIMIT :pageSize OFFSET :pageCount
			""", nativeQuery = true)
	public List<ContractorStockReportVo> getContractorStockReport(@Param("contractorName") String contractorName,
			@Param("designName") String designName, @Param("colorName") String colorName,
			@Param("pageSize") Integer pageSize, @Param("pageCount") Integer pageCount);

	@Query(value = """
			SELECT
			  count(*)
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
			  clr.colorname LIKE :colorName
			""", nativeQuery = true)
	public int getContractorStockCount(String contractorName, String designName, String colorName);

}
