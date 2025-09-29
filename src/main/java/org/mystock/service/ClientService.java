package org.mystock.service;

import java.util.List;
import java.util.Set;

import org.mystock.vo.ClientVo;

public interface ClientService {

	public ClientVo save(ClientVo clientVo);

	public Set<ClientVo> saveAll(Set<ClientVo> clientVos);

	public List<ClientVo> getAll();

	public ClientVo getById(Long id);

	public ClientVo updateStatus(Long id, boolean status);

	public List<ClientVo> findByClientNameIgnoreCase(String clientName);

	public List<ClientVo> findByCityIgnoreCase(String city);

	public List<ClientVo> findByStateIgnoreCase(String state);

	public List<ClientVo> findByCountryIgnoreCase(String country);

	public List<ClientVo> findByEmailIgnoreCase(String email);

	public List<ClientVo> findByMobile(String mobile);

	public List<ClientVo> findByGstNoIgnoreCase(String gstNo);

	public List<ClientVo> findByActive(boolean active);
	
	public List<ClientVo> find(String clientName, String city, String state, String mobile, String email, String gstNo, Boolean active);

}
