package org.mystock.repository;

import org.mystock.entity.QualityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QualityRepository extends JpaRepository<QualityEntity, Long> {

	@Query("SELECT q FROM QualityEntity q WHERE UPPER(q.qualityName) LIKE UPPER(:name)")
	List<QualityEntity> findByNameIgnoreCaseLike(@Param("name") String name);

	@Query("SELECT q FROM QualityEntity q WHERE UPPER(q.qualityName) = UPPER(:name)")
	QualityEntity findByNameIgnoreCase(@Param("name") String name);
}