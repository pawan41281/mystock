package org.mystock.service;

import org.mystock.exception.UnableToProcessException;
import org.mystock.vo.MenuGroupVo;

import java.util.List;

public interface MenuGroupService {
	public List<MenuGroupVo> findAll() throws UnableToProcessException;
	public List<MenuGroupVo> findByIdNotIgnoreCase(String id) throws UnableToProcessException;
	public void initMenuGroups();
}