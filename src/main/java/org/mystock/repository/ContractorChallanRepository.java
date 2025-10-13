package org.mystock.repository;

import org.mystock.entity.ContractorChallanEntity;
import org.mystock.vo.DashboardContractorGraphVo;
import org.mystock.vo.DashboardCurrentMonthContractorCardVo;
import org.mystock.vo.DashboardPreviousDayContractorCardVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContractorChallanRepository extends JpaRepository<ContractorChallanEntity, Long> {

	@Query("""
			    SELECT c
			    FROM ContractorChallanEntity c
			    WHERE (:challanNumber IS NULL OR c.challanNumber = :challanNumber)
			      AND (:contractorId IS NULL OR c.contractor.id = :contractorId)
			      AND (:fromChallanDate IS NULL OR c.challanDate >= :fromChallanDate)
			      AND (:toChallanDate IS NULL OR c.challanDate <= :toChallanDate)
			      AND (:challanType IS NULL OR c.challanType = :challanType)
			    ORDER BY c.challanDate DESC
			""")
	List<ContractorChallanEntity> findAll(@Param("challanNumber") Integer challanNumber,
			@Param("contractorId") Long contractorId, @Param("fromChallanDate") LocalDate fromChallanDate,
			@Param("toChallanDate") LocalDate toChallanDate, @Param("challanType") String challanType);

	@Query("""
			    SELECT c
			    FROM ContractorChallanEntity c
			    WHERE (c.challanDate = :challanDate)
			      AND (c.challanType like :challanType)
			    ORDER BY c.id DESC
			""")
	List<ContractorChallanEntity> getRecentChallans(@Param("challanDate") LocalDate challanDate,
			@Param("challanType") String challanType);

	@Query(value = "SELECT COUNT(*) FROM CONTRACTOR_CHALLAN_INFO WHERE CHALLAN_DATE >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-01') AND CHALLAN_DATE < DATE_FORMAT(CURRENT_DATE + INTERVAL 1 MONTH, '%Y-%m-01') AND CHALLANTYPE = :challanType", nativeQuery = true)
	Integer getCurrentMonthChallanCount(@Param("challanType") String challanType);

	@Query(value = """
			SELECT challan_type as ChallanType, count(*) as ChallanCount FROM CONTRACTOR_CHALLAN_INFO
			WHERE CHALLAN_DATE >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-01')
			AND CHALLAN_DATE < DATE_FORMAT(CURRENT_DATE + INTERVAL 1 MONTH, '%Y-%m-01')
			GROUP BY CHALLANTYPE
			""", nativeQuery = true)
	List<DashboardCurrentMonthContractorCardVo> getCurrentMonthChallanCount();

	@Query(value = """
			SELECT challan_type as ChallanType, count(*) as ChallanCount FROM CONTRACTOR_CHALLAN_INFO
			WHERE CHALLAN_DATE = (CURRENT_DATE-1) 
			GROUP BY CHALLANTYPE
			""", nativeQuery = true)
	List<DashboardPreviousDayContractorCardVo> getPreviousDayChallanCount();

	@Query(value = """
						SELECT
			    d.challan_day as ChallanDate,
			    COALESCE(SUM(CASE WHEN src.movement_type = 'CONTRACTOR_ISSUED' THEN src.total_items END), 0) AS IssuedQuantity,
			    COALESCE(SUM(CASE WHEN src.movement_type = 'CONTRACTOR_RECEIVED' THEN src.total_items END), 0) AS ReceivedQuantity
			FROM (
			    -- CONTRACTOR ISSUED
			    SELECT DATE(c.challan_date) AS challan_day, 'CONTRACTOR_ISSUED' AS movement_type, SUM(i.quantity) AS total_items
			    FROM contractor_challan_info c
			    JOIN contractor_challan_item_info i ON c.id = i.challan_id
			    WHERE c.challan_date >= CURRENT_DATE - INTERVAL 7 DAY AND c.challan_type = 'I'
			    GROUP BY challan_day

			    UNION ALL
			    -- CONTRACTOR RECEIVED
			    SELECT DATE(c.challan_date), 'CONTRACTOR_RECEIVED', SUM(i.quantity)
			    FROM contractor_challan_info c
			    JOIN contractor_challan_item_info i ON c.id = i.challan_id
			    WHERE c.challan_date >= CURRENT_DATE - INTERVAL 7 DAY AND c.challan_type = 'R'
			    GROUP BY DATE(c.challan_date)
			) src
			RIGHT JOIN (
			    -- generate last 7 days (so days with 0 items still appear)
			    SELECT CURDATE() - INTERVAL n DAY AS challan_day
			    FROM (
			        SELECT 0 AS n UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3
			        UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6
			    ) days
			) d ON d.challan_day = src.challan_day
			GROUP BY d.challan_day
			ORDER BY d.challan_day
			""", nativeQuery = true)
	public List<DashboardContractorGraphVo> getDashboardContractorGraphData();

}