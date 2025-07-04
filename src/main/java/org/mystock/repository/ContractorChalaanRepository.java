package org.mystock.repository;

import java.sql.Date;
import java.util.List;

import org.mystock.entity.ContractorChalaanEntity;
import org.mystock.entity.ContractorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorChalaanRepository extends JpaRepository<ContractorChalaanEntity, Long> {

	// 🔢 By Chalaan Number
	List<ContractorChalaanEntity> findByChalaanNumber(Integer chalaanNumber);

	// 📅 By Chalaan Date
	List<ContractorChalaanEntity> findByChalaanDate(Date chalaanDate);

	List<ContractorChalaanEntity> findByChalaanDateBetween(Date startDate, Date endDate);

	// 👷 By Contractor
	List<ContractorChalaanEntity> findByContractor(ContractorEntity contractor);

	List<ContractorChalaanEntity> findByContractor_Id(Long contractorId);

	List<ContractorChalaanEntity> findByContractor_ContractorNameIgnoreCase(String contractorName); // assuming field in
																									// ContractorEntity

	// 🔀 By Chalaan Type
	List<ContractorChalaanEntity> findByChalaanType(String chalaanType);

	// ✅ By Active Date Range + Contractor
	List<ContractorChalaanEntity> findByChalaanDateBetweenAndContractor_Id(Date start, Date end, Long contractorId);

	// 🧩 Combinations
	List<ContractorChalaanEntity> findByChalaanTypeAndContractor_Id(String type, Long contractorId);

	List<ContractorChalaanEntity> findByChalaanNumberAndContractor_Id(Integer chalaanNumber, Long contractorId);

	List<ContractorChalaanEntity> findByChalaanNumberAndChalaanDate(Integer chalaanNumber, Date chalaanDate);

	List<ContractorChalaanEntity> findByChalaanNumberAndChalaanDateBetween(Integer chalaanNumber, Date start, Date end);

	List<ContractorChalaanEntity> findByChalaanDateBetweenAndChalaanType(Date start, Date end, String chalaanType);

	List<ContractorChalaanEntity> findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(Date start, Date end,
			String chalaanType, Long contractorId);

}
