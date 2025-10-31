package org.mystock.service;

import org.mystock.vo.DesignVo;

import java.util.List;
import java.util.Set;

public interface DesignService {

	DesignVo save(DesignVo vo);
	
	Set<DesignVo> saveAll(Set<DesignVo> vos);

	DesignVo getById(Long id);
	
	List<DesignVo> getAllByName(String name);
	
	DesignVo getByName(String name);
	
	List<DesignVo> getByStatus(boolean status);

	List<DesignVo> getAll();

	DesignVo updateStatus(Long id, boolean status);


}