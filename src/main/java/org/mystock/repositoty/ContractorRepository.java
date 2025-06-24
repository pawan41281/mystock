package org.mystock.repositoty;

import org.mystock.dto.ContractorDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorRepository extends JpaRepository<ContractorDto, Long>{

}
