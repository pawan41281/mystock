package org.mystock.repositoty;

import java.util.List;

import org.mystock.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

	public List<ClientEntity> findByClientNameContainingIgnoreCase(String clientName);

	public List<ClientEntity> findByCityContainingIgnoreCase(String city);

	public List<ClientEntity> findByStateContainingIgnoreCase(String state);

	public List<ClientEntity> findByCountryIgnoreCase(String country);

	public List<ClientEntity> findByEmailIgnoreCase(String email);

	public List<ClientEntity> findByMobile(String mobile);

	public List<ClientEntity> findByGstNoContainingIgnoreCase(String gstNo);

	public List<ClientEntity> findByActive(boolean active);

}
