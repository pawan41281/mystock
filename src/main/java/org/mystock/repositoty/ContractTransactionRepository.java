package org.mystock.repositoty;

import java.sql.Date;
import java.util.List;

import org.mystock.dto.ContractTransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractTransactionRepository extends JpaRepository<ContractTransactionDto, Long> {

	public List<ContractTransactionDto> findByChalaanDateBetween(Date fromDate, Date toDate);

	public List<ContractTransactionDto> findByChalaanNumber(Integer chalaanNumber);

	public List<ContractTransactionDto> findByChalaanDateBetweenAndContractor(Date fromChalaanDate, Date toChalaanDate,
			Long contractor);

	public List<ContractTransactionDto> findByChalaanDateBetweenAndContractorAndDesign(Date fromChalaanDate,
			Date toChalaanDate, Long contractor, String design);

	public List<ContractTransactionDto> findByChalaanDateBetweenAndContractorAndDesignAndColor(Date fromChalaanDate,
			Date toChalaanDate, Long contractor, String design, String color);

	public void deleteByChalaanNumber(Integer chalaanNumber);

}
