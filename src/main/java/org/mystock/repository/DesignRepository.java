package org.mystock.repository;

import java.util.List;

import org.mystock.entity.DesignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepository extends JpaRepository<DesignEntity, Long>{
	
	public List<DesignEntity> findByActive(boolean active);

}
