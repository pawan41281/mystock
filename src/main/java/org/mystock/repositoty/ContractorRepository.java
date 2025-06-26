package org.mystock.repositoty;

import java.util.List;

import org.mystock.dto.ContractorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
}
