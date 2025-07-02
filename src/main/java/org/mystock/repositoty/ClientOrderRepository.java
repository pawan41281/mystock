package org.mystock.repositoty;

import java.sql.Date;
import java.util.List;

import org.mystock.dto.OrderDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderDto, Long> {

	public List<OrderDto> findByOrderNumber(Integer orderNumber);

	public List<OrderDto> findByClient(Long client);

	public List<OrderDto> findByDesign(String design);

	public List<OrderDto> findByOrderDateBetween(Date fromDate, Date toDate);

	public List<OrderDto> findByOrderDateBetweenAndClient(Date fromDate, Date toDate, Long client);

	public List<OrderDto> findByOrderDateBetweenAndClientAndDesign(Date fromDate, Date toDate, Long client,
			String design);

	public List<OrderDto> findByOrderDateBetweenAndClientAndDesignAndColor(Date fromDate, Date toDate, Long client,
			String design, String color);

	public List<OrderDto> findByOrderDateBetweenAndDesign(Date fromDate, Date toDate, String design);

	public List<OrderDto> findByOrderDateBetweenAndDesignAndColor(Date fromDate, Date toDate, String design,
			String color);

	@Modifying
	@Transactional
	@Query(value = "UPDATE ordermaster SET active = :status where id = :id", nativeQuery = true)
	public void updateStatus(@Param("status") boolean status, @Param("id") Long id);
}
