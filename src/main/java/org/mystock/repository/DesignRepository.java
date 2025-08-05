package org.mystock.repository;

import java.util.List;

import org.mystock.entity.DesignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepository extends JpaRepository<DesignEntity, Long>{
	
	public List<DesignEntity> findByActive(boolean active);
	
	@Query("SELECT d FROM DesignEntity d WHERE UPPER(d.designName) LIKE UPPER(:designName)")
	List<DesignEntity> findByDesignNameIgnoreCaseLike(@Param("designName") String designName);
	
}
