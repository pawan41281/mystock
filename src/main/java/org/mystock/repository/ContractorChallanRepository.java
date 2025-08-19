package org.mystock.repository;

import java.time.LocalDate;
import java.util.List;

import org.mystock.entity.ContractorChallanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
	List<ContractorChallanEntity> getRecentChallans(
			@Param("challanDate") LocalDate challanDate,
			@Param("challanType") String challanType);

}
