package org.mystock.repository;

import java.time.LocalDate;
import java.util.List;

import org.mystock.entity.ClientChallanEntity;
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
	List<ClientChallanEntity> findAll(@Param("challanNumber") Integer challanNumber, @Param("clientId") Long clientId,
			@Param("fromChallanDate") LocalDate fromChallanDate, @Param("toChallanDate") LocalDate toChallanDate,
			@Param("challanType") String challanType);

	@Query("""
			    SELECT c
			    FROM ClientChallanEntity c
			    WHERE (c.challanDate = :challanDate)
			      AND (c.challanType like :challanType)
			    ORDER BY c.id DESC
			""")
	List<ClientChallanEntity> getRecentChallans(@Param("challanDate") LocalDate challanDate,
			@Param("challanType") String challanType);

}
