package org.mystock.service;

import java.util.List;

import org.mystock.vo.DesignVo;

public interface DesignService {

	public DesignVo save(DesignVo vo);

	public DesignVo getById(Long id);
	
	public List<DesignVo> getByStatus(boolean status);

	public List<DesignVo> getAll();

	public DesignVo update(Long id, boolean status);


}
