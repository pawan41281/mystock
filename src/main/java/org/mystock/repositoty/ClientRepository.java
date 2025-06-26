package org.mystock.repositoty;

import java.util.List;

import org.mystock.dto.ClientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClientRepository extends JpaRepository<ClientDto, Long> {

	public List<ClientDto> findByClientNameIgnoreCase(String clientName);

	public List<ClientDto> findByEmailIgnoreCase(String email);

	public List<ClientDto> findByMobile(String mobile);

	public List<ClientDto> findByGstNoIgnoreCase(String gstNo);

	@Query(value = "SELECT * FROM clientmaster where active = :active", nativeQuery = true)
	public List<ClientDto> findByStatus(boolean active);

	@Query(value = "SELECT * FROM clientmaster where upper(email) = upper(:email) or mobile = :mobile or upper(gstno) = :gstNo or active = :active", nativeQuery = true)
	public List<ClientDto> findByEmailOrMobileOrGstNoOrStatus(String email, String mobile, String gstNo,
			boolean active);

	@Modifying
	@Transactional
	@Query(value = "UPDATE CLIENTMASTER SET active = :status where id = :id", nativeQuery = true)
	public void updateStatus(@Param("status") boolean status, @Param("id") Long id);
}
