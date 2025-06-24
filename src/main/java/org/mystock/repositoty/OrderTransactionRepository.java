package org.mystock.repositoty;

import org.mystock.dto.OrderTransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderTransactionRepository extends JpaRepository<OrderTransactionDto, Long>{

}
