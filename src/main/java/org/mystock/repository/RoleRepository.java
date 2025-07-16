package org.mystock.repository;

import org.mystock.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	public RoleEntity findByNameIgnoreCase(String name);
}