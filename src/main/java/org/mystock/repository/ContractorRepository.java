package org.mystock.repository;

import java.util.List;

import org.mystock.entity.ContractorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorRepository extends JpaRepository<ContractorEntity, Long>{

	public List<ContractorEntity> findByContractorNameContainingIgnoreCase(String ContractorName);

	public List<ContractorEntity> findByCityContainingIgnoreCase(String city);

	public List<ContractorEntity> findByStateContainingIgnoreCase(String state);

	public List<ContractorEntity> findByCountryIgnoreCase(String country);

	public List<ContractorEntity> findByEmailIgnoreCase(String email);

	public List<ContractorEntity> findByMobile(String mobile);

	public List<ContractorEntity> findByGstNoContainingIgnoreCase(String gstNo);

	public List<ContractorEntity> findByActive(boolean active);
	
	@Query("SELECT c FROM ContractorEntity c WHERE " +
		       "(:contractorName IS NULL OR c.contractorName like %:contractorName%) AND " +
		       "(:city IS NULL OR c.city like :city) AND " +
		       "(:state IS NULL OR c.state like :state) AND " +
		       "(:mobile IS NULL OR c.mobile like :mobile) AND " +
		       "(:email IS NULL OR c.email like :email) AND " +
		       "(:gstNo IS NULL OR c.gstNo like :gstNo) AND " +
		       "(:active IS NULL OR c.active = :active)")
		List<ContractorEntity> find(
		    @Param("contractorName") String contractorName,
		    @Param("city") String city,
		    @Param("state") String state,
		    @Param("mobile") String mobile,
		    @Param("email") String email,
		    @Param("gstNo") String gstNo,
		    @Param("active") Boolean active);



}
