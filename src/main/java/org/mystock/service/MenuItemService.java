package org.mystock.service;

import org.mystock.exception.UnableToProcessException;
import org.mystock.vo.MenuItemVo;

import java.util.List;

public interface MenuItemService {
	public List<MenuItemVo> findAll() throws UnableToProcessException;
}