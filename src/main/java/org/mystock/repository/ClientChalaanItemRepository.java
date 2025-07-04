package org.mystock.repository;

import org.mystock.entity.ClientChalaanItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientChalaanItemRepository extends JpaRepository<ClientChalaanItemEntity, Long>{

}
