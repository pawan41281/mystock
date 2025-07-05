package org.mystock.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.mystock.vo.ContractorChalaanVo;

public interface ContractorChalaanService {

	public ContractorChalaanVo save(ContractorChalaanVo vo);

	public Set<ContractorChalaanVo> saveAll(Set<ContractorChalaanVo> vos);

	public ContractorChalaanVo findById(Long id);

	public ContractorChalaanVo deleteById(Long id);

	public List<ContractorChalaanVo> findAll(Integer chalaanNumber, Long contractorId,
	        LocalDate fromChalaanDate, LocalDate toChalaanDate, String chalaanType);

}
