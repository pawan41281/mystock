package org.mystock.service;

import org.mystock.vo.ClientVo;

import java.util.List;
import java.util.Set;

public interface ClientService {

	ClientVo save(ClientVo clientVo);

	Set<ClientVo> saveAll(Set<ClientVo> clientVos);

	List<ClientVo> getAll();

	ClientVo getById(Long id);

	ClientVo updateStatus(Long id, boolean status);

	List<ClientVo> findByClientNameIgnoreCase(String clientName);

	List<ClientVo> findByCityIgnoreCase(String city);

	List<ClientVo> findByStateIgnoreCase(String state);

	List<ClientVo> findByCountryIgnoreCase(String country);

	List<ClientVo> findByEmailIgnoreCase(String email);

	List<ClientVo> findByMobile(String mobile);

	List<ClientVo> findByGstNoIgnoreCase(String gstNo);

	List<ClientVo> findByActive(boolean active);
	
	List<ClientVo> find(String clientName, String city, String state, String mobile, String email, String gstNo, Boolean active);

}