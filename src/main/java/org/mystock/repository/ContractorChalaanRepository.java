package org.mystock.repository;

import java.time.LocalDate;
import java.util.List;

import org.mystock.entity.ContractorChalaanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorChalaanRepository extends JpaRepository<ContractorChalaanEntity, Long> {

	@Query("""
			    SELECT c
			    FROM ContractorChalaanEntity c
			    WHERE (:chalaanNumber IS NULL OR c.chalaanNumber = :chalaanNumber)
			      AND (:contractorId IS NULL OR c.contractor.id = :contractorId)
			      AND (:fromChalaanDate IS NULL OR c.chalaanDate >= :fromChalaanDate)
			      AND (:toChalaanDate IS NULL OR c.chalaanDate <= :toChalaanDate)
			      AND (:chalaanType IS NULL OR c.chalaanType = :chalaanType)
			    ORDER BY c.chalaanDate DESC
			""")
	List<ContractorChalaanEntity> findAll(@Param("chalaanNumber") Integer chalaanNumber,
			@Param("contractorId") Long contractorId, @Param("fromChalaanDate") LocalDate fromChalaanDate,
			@Param("toChalaanDate") LocalDate toChalaanDate, @Param("chalaanType") String chalaanType);

}
