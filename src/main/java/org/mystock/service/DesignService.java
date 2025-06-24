package org.mystock.service;

import java.util.List;

import org.mystock.vo.DesignVo;

public interface DesignService {

	public List<DesignVo> list();

	public DesignVo save(DesignVo designVo);
	
	public DesignVo findById(Long Id);

	public List<DesignVo> findByDesignIgnoreCase(String design);

	public List<DesignVo> findByColorIgnoreCase(String color);

	public List<DesignVo> findByStatusIgnoreCase(boolean active);

	public List<DesignVo> findByDesignIgnoreCaseOrColorIgnoreCaseOrStatus(String design, String color, boolean active);

}
