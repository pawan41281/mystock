package org.mystock.repositoty;

import org.mystock.dto.ContractTransactionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractTransactionRepository extends JpaRepository<ContractTransactionDto, Long>{

}
