package org.mystock.service;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import org.mystock.vo.ClientChalaanVo;

public interface ClientChalaanService {

	public ClientChalaanVo save(ClientChalaanVo vo);

	public Set<ClientChalaanVo> saveAll(Set<ClientChalaanVo> vos);

	public List<ClientChalaanVo> findAll();

	public ClientChalaanVo findById(Long id);

	public ClientChalaanVo deleteById(Long id);

	// 🔢 By Chalaan Number
	public List<ClientChalaanVo> findByChalaanNumber(Integer chalaanNumber);

	// 📅 By Chalaan Date
	public List<ClientChalaanVo> findByChalaanDate(Date chalaanDate);

	public List<ClientChalaanVo> findByChalaanDateBetween(Date startDate, Date endDate);

	// 👷 By Client
	public List<ClientChalaanVo> findByClient_Id(Long clientId);

	// 🔀 By Chalaan Type
	public List<ClientChalaanVo> findByChalaanType(String chalaanType);

	// ✅ By Active Date Range + Client
	public List<ClientChalaanVo> findByChalaanDateBetweenAndClient_Id(Date start, Date end, Long clientId);

	// 🧩 Combinations
	public List<ClientChalaanVo> findByChalaanTypeAndClient_Id(String type, Long clientId);

	public List<ClientChalaanVo> findByChalaanDateBetweenAndChalaanType(Date start, Date end, String chalaanType);

	public List<ClientChalaanVo> findByChalaanDateBetweenAndChalaanTypeAndClient_Id(Date start, Date end,
			String chalaanType, Long clientId);

}
