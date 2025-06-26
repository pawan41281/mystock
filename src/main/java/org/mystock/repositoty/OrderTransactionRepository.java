package org.mystock.repositoty;

import java.sql.Date;
import java.util.List;

import org.mystock.dto.OrderTransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTransactionRepository extends JpaRepository<OrderTransactionDto, Long> {

	public List<OrderTransactionDto> findByChalaanNumber(Integer chalaanNumber);

	public List<OrderTransactionDto> findByDesign(String design);
	
	public List<OrderTransactionDto> findByOrderNumber(Integer orderNumber);

	public List<OrderTransactionDto> findByChalaanDateBetween(Date fromDate, Date toDate);

	public List<OrderTransactionDto> findByChalaanDateBetweenAndClient(Date fromChalaanDate, Date toChalaanDate,
			Long client);

	public List<OrderTransactionDto> findByChalaanDateBetweenAndClientAndDesign(Date fromChalaanDate,
			Date toChalaanDate, Long client, String design);

	public List<OrderTransactionDto> findByChalaanDateBetweenAndClientAndDesignAndColor(Date fromChalaanDate,
			Date toChalaanDate, Long client, String design, String color);

	public List<OrderTransactionDto> findByChalaanDateBetweenAndOrderNumber(Date fromChalaanDate, Date toChalaanDate,
			Integer orderNumber);

	public List<OrderTransactionDto> findByChalaanDateBetweenAndOrderNumberAndDesign(Date fromChalaanDate,
			Date toChalaanDate, Integer orderNumber, String design);

	public List<OrderTransactionDto> findByChalaanDateBetweenAndOrderNumberAndDesignAndColor(Date fromChalaanDate,
			Date toChalaanDate, Integer orderNumber, String design, String color);

	public List<OrderTransactionDto> findByChalaanDateBetweenAndClientAndOrderNumber(Date fromChalaanDate,
			Date toChalaanDate, Long client, Integer orderNumber);

	public List<OrderTransactionDto> findByChalaanDateBetweenAndClientAndOrderNumberAndDesign(Date fromChalaanDate,
			Date toChalaanDate, Long client, Integer orderNumber, String design);

	public List<OrderTransactionDto> findByChalaanDateBetweenAndClientAndOrderNumberAndDesignAndColor(
			Date fromChalaanDate, Date toChalaanDate, Long client, Integer orderNumber, String design, String color);


	public void deleteByOrderNumber(Integer orderNumber);
	
	public void deleteByChalaanNumber(Integer chalaanNumber);
	
	public void deleteByDesign(String design);
	
}
