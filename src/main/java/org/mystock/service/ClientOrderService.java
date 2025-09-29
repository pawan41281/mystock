package org.mystock.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.mystock.vo.ClientOrderVo;

public interface ClientOrderService {

	public ClientOrderVo save(ClientOrderVo vo);

	public Set<ClientOrderVo> saveAll(Set<ClientOrderVo> vos);

	public ClientOrderVo findById(Long id);

	public ClientOrderVo deleteById(Long id);

	public List<ClientOrderVo> findAll(Integer orderNumber, Long clientId, LocalDate fromOrderDate,
			LocalDate toOrderDate);

}
