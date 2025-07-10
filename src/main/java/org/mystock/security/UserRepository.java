package org.mystock.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

	public UserEntity findByUserName(String userName);

	public UserEntity findByEmail(String email);

	public List<UserEntity> findByMobile(String mobile);

	public Boolean existsByUserName(String userName);

	public Boolean existsByEmail(String email);
	
	public List<UserEntity> findByUserNameOrEmailOrMobile(String userName, String email, String mobile);
}
