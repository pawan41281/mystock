package org.mystock.repository;

import org.mystock.entity.MenuGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface MenuGroupRepository extends JpaRepository<MenuGroupEntity, String> {

    boolean existsByIdIgnoreCase(String id);

    boolean existsByTitleIgnoreCase(String title);

    boolean existsByIdIgnoreCaseAndTitleIgnoreCase(String id, String title);

    List<MenuGroupEntity> findByIdNotIgnoreCase(String id);

    List<MenuGroupEntity> findByRoleNull();

    List<MenuGroupEntity> findDistinctByRole_NameInIgnoreCase(Collection<String> names);


}