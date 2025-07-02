package org.mystock.service;

import java.util.List;

import org.mystock.vo.ClientVo;

public interface ClientService {

	public ClientVo save(ClientVo clientVo);

	public List<ClientVo> getAll();

	public ClientVo getById(Long id);

	public ClientVo updateStatus(Long id, boolean status);
	
	List<ClientVo> findByClientNameIgnoreCase(String clientName);

    List<ClientVo> findByCityIgnoreCase(String city);

    List<ClientVo> findByStateIgnoreCase(String state);

    List<ClientVo> findByCountryIgnoreCase(String country);

    List<ClientVo> findByEmailIgnoreCase(String email);

    List<ClientVo> findByMobile(String mobile);

    List<ClientVo> findByGstNoIgnoreCase(String gstNo);

    List<ClientVo> findByActive(boolean active);

}
