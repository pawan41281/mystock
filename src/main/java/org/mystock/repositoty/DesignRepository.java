package org.mystock.repositoty;

import java.util.List;

import org.mystock.dto.DesignDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignRepository extends JpaRepository<DesignDto, Long> {

	public List<DesignDto> findByDesignIgnoreCase(String design);

	public List<DesignDto> findByColorIgnoreCase(String color);

	@Query(value = "SELECT * FROM designmaster where active = :active", nativeQuery = true)
	public List<DesignDto> findByStatusIgnoreCase(boolean active);

	@Query(value = "SELECT * FROM designmaster where upper(design) = upper(:design) or upper(color) = upper(:color) or active = :active ", nativeQuery = true)
	public List<DesignDto> findByDesignIgnoreCaseOrColorIgnoreCaseOrStatus(String design, String color, boolean active);

}
