package org.mystock.repository;

import java.time.LocalDate;
import java.util.List;

import org.mystock.entity.ClientChallanEntity;
import org.mystock.vo.DashboardClientGraphVo;
import org.mystock.vo.DashboardCurrentMonthClientCardVo;
import org.mystock.vo.DashboardPreviousDayClientCardVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientChallanRepository extends JpaRepository<ClientChallanEntity, Long> {

	@Query("""
			    SELECT c
			    FROM ClientChallanEntity c
			    WHERE (:challanNumber IS NULL OR c.challanNumber = :challanNumber)
			      AND (:clientId IS NULL OR c.client.id = :clientId)
			      AND (:fromChallanDate IS NULL OR c.challanDate >= :fromChallanDate)
			      AND (:toChallanDate IS NULL OR c.challanDate <= :toChallanDate)
			      AND (:challanType IS NULL OR c.challanType = :challanType)
			    ORDER BY c.challanDate DESC
			""")
	public List<ClientChallanEntity> findAll(@Param("challanNumber") Integer challanNumber,
			@Param("clientId") Long clientId, @Param("fromChallanDate") LocalDate fromChallanDate,
			@Param("toChallanDate") LocalDate toChallanDate, @Param("challanType") String challanType);

	@Query("""
			    SELECT c
			    FROM ClientChallanEntity c
			    WHERE (c.challanDate = :challanDate)
			      AND (c.challanType like :challanType)
			    ORDER BY c.id DESC
			""")
	public List<ClientChallanEntity> getRecentChallans(@Param("challanDate") LocalDate challanDate,
			@Param("challanType") String challanType);

	@Query(value = "SELECT COUNT(*) FROM CLIENTCHALLANINFO WHERE CHALLANDATE >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-01') AND CHALLANDATE < DATE_FORMAT(CURRENT_DATE + INTERVAL 1 MONTH, '%Y-%m-01') AND CHALLANTYPE = :challanType", nativeQuery = true)
	Integer getCurrentMonthChallanCount(@Param("challanType") String challanType);

	@Query(value = """
			SELECT challantype as challanType,
			count(*) as ChallanCount
			FROM CLIENTCHALLANINFO
			WHERE CHALLANDATE >= DATE_FORMAT(CURRENT_DATE, '%Y-%m-01')
			AND CHALLANDATE < DATE_FORMAT(CURRENT_DATE + INTERVAL 1 MONTH, '%Y-%m-01')
			GROUP BY CHALLANTYPE
			""", nativeQuery = true)
	public List<DashboardCurrentMonthClientCardVo> getCurrentMonthChallanCount();

	@Query(value = """
			SELECT challantype as challanType,
			count(*) as ChallanCount
			FROM CLIENTCHALLANINFO
			WHERE CHALLANDATE = (CURRENT_DATE-1)
			GROUP BY CHALLANTYPE
			""", nativeQuery = true)
	public List<DashboardPreviousDayClientCardVo> getPreviousDayChallanCount();

	@Query(value = """
						SELECT
			    d.challan_day as ChallanDate,
			    COALESCE(SUM(CASE WHEN src.movement_type = 'CLIENT_ISSUED' THEN src.total_items END), 0) AS IssuedQuantity,
			    COALESCE(SUM(CASE WHEN src.movement_type = 'CLIENT_RECEIVED' THEN src.total_items END), 0) AS ReceivedQuantity
			FROM (
			    -- CLIENT ISSUED
			    SELECT DATE(c.challandate) AS challan_day, 'CLIENT_ISSUED' AS movement_type, SUM(i.quantity) AS total_items
			    FROM clientchallaninfo c
			    JOIN clientchallaniteminfo i ON c.id = i.challan_id
			    WHERE c.challandate >= CURRENT_DATE - INTERVAL 7 DAY AND c.challantype = 'I'
			    GROUP BY challan_day

			    UNION ALL
			    -- CLIENT RECEIVED
			    SELECT DATE(c.challandate), 'CLIENT_RECEIVED', SUM(i.quantity)
			    FROM clientchallaninfo c
			    JOIN clientchallaniteminfo i ON c.id = i.challan_id
			    WHERE c.challandate >= CURRENT_DATE - INTERVAL 7 DAY AND c.challantype = 'R'
			    GROUP BY DATE(c.challandate)
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
	public List<DashboardClientGraphVo> getDashboardClientGraphData();

}
