package org.mystock.service;

import java.util.List;

import org.mystock.vo.ClientVo;

public interface ClientService {

	public List<ClientVo> list();

	public ClientVo save(ClientVo clientVo);

	public ClientVo findById(Long id);

	public List<ClientVo> findByEmailIgnoreCase(String email);

	public List<ClientVo> findByMobile(String mobile);

	public List<ClientVo> findByGstNoIgnoreCase(String gstNo);

	public List<ClientVo> findByStatus(boolean active);

	public List<ClientVo> findByEmailOrMobileOrGstNoOrStatus(String email, String mobile, String gstNo, boolean active);

}
