package org.mystock.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.mystock.vo.ClientChalaanVo;

public interface ClientChalaanService {

	public ClientChalaanVo save(ClientChalaanVo vo);

	public Set<ClientChalaanVo> saveAll(Set<ClientChalaanVo> vos);

	public ClientChalaanVo findById(Long id);

	public ClientChalaanVo deleteById(Long id);

	public List<ClientChalaanVo> findAll(Integer chalaanNumber, Long clientId,
	        LocalDate fromChalaanDate, LocalDate toChalaanDate, String chalaanType);

}
