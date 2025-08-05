package org.mystock.repository;

import java.util.List;

import org.mystock.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<ColorEntity, Long> {
	
	@Query("SELECT c FROM ColorEntity c WHERE UPPER(c.colorName) LIKE UPPER(:name)")
	List<ColorEntity> findByNameIgnoreCaseLike(@Param("name") String name);

}
