package org.mystock.service;

import java.util.List;
import java.util.Set;

import org.mystock.vo.DesignVo;

public interface DesignService {

	public DesignVo save(DesignVo vo);
	
	public Set<DesignVo> saveAll(Set<DesignVo> vos);

	public DesignVo getById(Long id);
	
	public List<DesignVo> getAllByName(String name);
	
	public DesignVo getByName(String name);
	
	public List<DesignVo> getByStatus(boolean status);

	public List<DesignVo> getAll();

	public DesignVo updateStatus(Long id, boolean status);


}
