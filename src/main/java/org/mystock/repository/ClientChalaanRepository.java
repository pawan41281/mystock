package org.mystock.repository;

import java.sql.Date;
import java.util.List;

import org.mystock.entity.ClientChalaanEntity;
import org.mystock.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientChalaanRepository extends JpaRepository<ClientChalaanEntity, Long> {

	// 🔢 By Chalaan Number
	List<ClientChalaanEntity> findByChalaanNumber(Integer chalaanNumber);

	// 📅 By Chalaan Date
	List<ClientChalaanEntity> findByChalaanDate(Date chalaanDate);

	List<ClientChalaanEntity> findByChalaanDateBetween(Date startDate, Date endDate);

	// 👷 By Client
	List<ClientChalaanEntity> findByClient(ClientEntity client);

	List<ClientChalaanEntity> findByClient_Id(Long clientId);

	List<ClientChalaanEntity> findByClient_ClientNameIgnoreCase(String clientName); // assuming field in
																									// ClientEntity

	// 🔀 By Chalaan Type
	List<ClientChalaanEntity> findByChalaanType(String chalaanType);

	// ✅ By Active Date Range + Client
	List<ClientChalaanEntity> findByChalaanDateBetweenAndClient_Id(Date start, Date end, Long clientId);

	// 🧩 Combinations
	List<ClientChalaanEntity> findByChalaanTypeAndClient_Id(String type, Long clientId);

	List<ClientChalaanEntity> findByChalaanNumberAndClient_Id(Integer chalaanNumber, Long clientId);

	List<ClientChalaanEntity> findByChalaanNumberAndChalaanDate(Integer chalaanNumber, Date chalaanDate);

	List<ClientChalaanEntity> findByChalaanNumberAndChalaanDateBetween(Integer chalaanNumber, Date start, Date end);

	List<ClientChalaanEntity> findByChalaanDateBetweenAndChalaanType(Date start, Date end, String chalaanType);

	List<ClientChalaanEntity> findByChalaanDateBetweenAndChalaanTypeAndClient_Id(Date start, Date end,
			String chalaanType, Long clientId);

}
