package org.mystock.repositoty;

import java.util.List;

import org.mystock.dto.DesignDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DesignRepository extends JpaRepository<DesignDto, Long> {

	public List<DesignDto> findByDesignIgnoreCase(String design);

	public List<DesignDto> findByColorIgnoreCase(String color);

	@Query(value = "SELECT * FROM designmaster where active = :active", nativeQuery = true)
	public List<DesignDto> findByStatusIgnoreCase(boolean active);

	@Query(value = "SELECT * FROM designmaster where upper(design) = upper(:design) or upper(color) = upper(:color) or active = :active ", nativeQuery = true)
	public List<DesignDto> findByDesignIgnoreCaseOrColorIgnoreCaseOrStatus(String design, String color, boolean active);

	@Modifying
	@Transactional
	@Query(value = "UPDATE designmaster SET active = :status where id = :id", nativeQuery = true)
	public void updateStatus(@Param("status") boolean status, @Param("id") Long id);
	
}
