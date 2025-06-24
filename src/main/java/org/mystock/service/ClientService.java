package org.mystock.service;

import java.util.List;

import org.mystock.vo.ClientVo;

public interface ClientService {

	public List<ClientVo> list();
	public ClientVo save(ClientVo clientVo);
}
