package org.mystock.service;

import org.mystock.vo.PropertyVo;

import java.util.List;
import java.util.Set;

public interface PropertyService {

	PropertyVo save(PropertyVo propertyVo);

	Set<PropertyVo> saveAll(Set<PropertyVo> propertyVos);

	List<PropertyVo> getAll();

	List<PropertyVo> findByNameIgnoreCaseLike(String propertyName);

	PropertyVo findByNameIgnoreCase(String propertyName);

	PropertyVo getById(Long id);

}