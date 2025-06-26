package org.mystock.repositoty;

import java.util.List;

import org.mystock.dto.ContractorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ContractorRepository extends JpaRepository<ContractorDto, Long> {

	public List<ContractorDto> findByContractorNameIgnoreCase(String name);

	public List<ContractorDto> findByEmailIgnoreCase(String email);

	public List<ContractorDto> findByMobile(String mobile);

	public List<ContractorDto> findByGstNoIgnoreCase(String gstNo);

	@Query(value = "SELECT * FROM contractormaster where active = :active", nativeQuery = true)
	public List<ContractorDto> findByStatus(boolean active);

	@Query(value = "SELECT * FROM contractormaster where upper(email) = upper(:email) or mobile = :mobile or upper(gstno) = :gstNo or active = :active", nativeQuery = true)
	public List<ContractorDto> findByEmailOrMobileOrGstNoOrStatus(String email, String mobile, String gstNo,
			boolean active);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE contractormaster SET active = :status where id = :id", nativeQuery = true)
	public void updateStatus(@Param("status") boolean status, @Param("id") Long id);
}
