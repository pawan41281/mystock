package org.mystock.repositoty;

import org.mystock.dto.ClientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientDto, Long>{

}
