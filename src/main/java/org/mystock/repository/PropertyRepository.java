package org.mystock.repository;

import org.mystock.entity.PropertyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {

	@Query("SELECT p FROM PropertyEntity p WHERE UPPER(p.name) LIKE UPPER(:name) order by p.name")
	List<PropertyEntity> findByNameIgnoreCaseLike(@Param("name") String name);

	@Query("SELECT p FROM PropertyEntity p WHERE UPPER(p.name) = UPPER(:name) order by p.name")
	PropertyEntity findByNameIgnoreCase(@Param("name") String name);
}