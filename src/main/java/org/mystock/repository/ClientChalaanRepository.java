package org.mystock.repository;

import java.time.LocalDate;
import java.util.List;

import org.mystock.entity.ClientChalaanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientChalaanRepository extends JpaRepository<ClientChalaanEntity, Long> {

	@Query("""
			    SELECT c
			    FROM ClientChalaanEntity c
			    WHERE (:chalaanNumber IS NULL OR c.chalaanNumber = :chalaanNumber)
			      AND (:clientId IS NULL OR c.client.id = :clientId)
			      AND (:fromChalaanDate IS NULL OR c.chalaanDate >= :fromChalaanDate)
			      AND (:toChalaanDate IS NULL OR c.chalaanDate <= :toChalaanDate)
			      AND (:chalaanType IS NULL OR c.chalaanType = :chalaanType)
			    ORDER BY c.chalaanDate DESC
			""")
	List<ClientChalaanEntity> findAll(@Param("chalaanNumber") Integer chalaanNumber,
			@Param("clientId") Long clientId, @Param("fromChalaanDate") LocalDate fromChalaanDate,
			@Param("toChalaanDate") LocalDate toChalaanDate, @Param("chalaanType") String chalaanType);

}
