package org.mystock.repositoty;

import org.mystock.entity.ContractorChalaanItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorChalaanItemRepository extends JpaRepository<ContractorChalaanItemEntity, Long>{

}
