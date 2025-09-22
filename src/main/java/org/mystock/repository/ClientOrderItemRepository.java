package org.mystock.repository;

import org.mystock.entity.ClientOrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientOrderItemRepository extends JpaRepository<ClientOrderItemEntity, Long>{

}
