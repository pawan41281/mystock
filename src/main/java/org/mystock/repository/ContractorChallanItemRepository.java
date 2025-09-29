package org.mystock.repository;

import org.mystock.entity.ContractorChallanItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorChallanItemRepository extends JpaRepository<ContractorChallanItemEntity, Long>{

}
