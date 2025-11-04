package org.mystock.repository;

import org.mystock.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

	List<ClientEntity> findByClientNameContainingIgnoreCase(String clientName);

	List<ClientEntity> findByCityContainingIgnoreCase(String city);

	List<ClientEntity> findByStateContainingIgnoreCase(String state);

	List<ClientEntity> findByCountryIgnoreCase(String country);

	List<ClientEntity> findByEmailIgnoreCase(String email);

	List<ClientEntity> findByMobile(String mobile);

	List<ClientEntity> findByGstNoContainingIgnoreCase(String gstNo);

	List<ClientEntity> findByActive(boolean active);
	
	@Query("SELECT c FROM ClientEntity c WHERE " +
		       "(:clientName IS NULL OR c.clientName like %:clientName%) AND " +
		       "(:city IS NULL OR c.city like :city) AND " +
		       "(:state IS NULL OR c.state like :state) AND " +
		       "(:mobile IS NULL OR c.mobile like :mobile) AND " +
		       "(:email IS NULL OR c.email like :email) AND " +
		       "(:gstNo IS NULL OR c.gstNo like :gstNo) AND " +
		       "(:active IS NULL OR c.active = :active)")
		List<ClientEntity> find(
		    @Param("clientName") String clientName,
		    @Param("city") String city,
		    @Param("state") String state,
		    @Param("mobile") String mobile,
		    @Param("email") String email,
		    @Param("gstNo") String gstNo,
		    @Param("active") Boolean active);
}