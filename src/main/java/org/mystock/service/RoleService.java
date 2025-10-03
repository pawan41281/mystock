package org.mystock.service;

import org.mystock.vo.RoleVo;

import java.util.List;

public interface RoleService {
	
	public List<RoleVo> getAll();

	public void initRoles();
	
}
