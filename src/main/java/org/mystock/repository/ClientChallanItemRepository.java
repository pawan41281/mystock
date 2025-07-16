package org.mystock.repository;

import org.mystock.entity.ClientChallanItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientChallanItemRepository extends JpaRepository<ClientChallanItemEntity, Long>{

}
