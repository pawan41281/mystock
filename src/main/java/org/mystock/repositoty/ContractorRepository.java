package org.mystock.repositoty;

import java.util.List;

import org.mystock.entity.ContractorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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



}
