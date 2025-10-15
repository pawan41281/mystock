package org.mystock.repository;

import org.mystock.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {



	public UserEntity findByUserId(String userId);

	public UserEntity findByEmail(String email);

	public List<UserEntity> findByMobile(String mobile);

	public Boolean existsByUserId(String userId);

	public Boolean existsByEmail(String email);

	@Query("SELECT u FROM UserEntity u WHERE " +
			"(:userId IS NULL OR u.userId like %:userId%) AND " +
			"(:email IS NULL OR u.email like :email) AND " +
			"(:mobile IS NULL OR u.mobile like :mobile)")
	public List<UserEntity> find(String userId, String email, String mobile);

	@Transactional
	@Modifying
	@Query("update UserEntity u set u.locked = :status where u.id = :id")
	public void updateStatus(Long id, boolean status);
}