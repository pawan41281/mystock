package org.mystock.service;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.mystock.vo.ContractorChalaanVo;

public interface ContractorChalaanService {

	public ContractorChalaanVo save(ContractorChalaanVo vo);
	
	public Set<ContractorChalaanVo> saveAll(Set<ContractorChalaanVo> vos);

	public List<ContractorChalaanVo> findAll();

	public ContractorChalaanVo findById(Long id);
	
	public ContractorChalaanVo deleteById(Long id);

	// 🔢 By Chalaan Number
	public List<ContractorChalaanVo> findByChalaanNumber(Integer chalaanNumber);

	// 📅 By Chalaan Date
	public List<ContractorChalaanVo> findByChalaanDate(Date chalaanDate);

	public List<ContractorChalaanVo> findByChalaanDateBetween(Date startDate, Date endDate);

	// 👷 By Contractor
	public List<ContractorChalaanVo> findByContractor_Id(Long contractorId);

	// 🔀 By Chalaan Type
	public List<ContractorChalaanVo> findByChalaanType(String chalaanType);

	// ✅ By Active Date Range + Contractor
	public List<ContractorChalaanVo> findByChalaanDateBetweenAndContractor_Id(Date start, Date end, Long contractorId);

	// 🧩 Combinations
	public List<ContractorChalaanVo> findByChalaanTypeAndContractor_Id(String type, Long contractorId);

	public List<ContractorChalaanVo> findByChalaanDateBetweenAndChalaanType(Date start, Date end, String chalaanType);

	public List<ContractorChalaanVo> findByChalaanDateBetweenAndChalaanTypeAndContractor_Id(Date start, Date end,
			String chalaanType, Long contractorId);

}
