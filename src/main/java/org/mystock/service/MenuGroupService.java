package org.mystock.service;

import org.mystock.exception.UnableToProcessException;
import org.mystock.vo.MenuGroupVo;

import java.util.List;
import java.util.Set;

public interface MenuGroupService {
	List<MenuGroupVo> findAll(Set<String> roles) throws UnableToProcessException;
}