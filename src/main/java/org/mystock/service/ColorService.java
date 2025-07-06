package org.mystock.service;

import java.util.List;
import java.util.Set;

import org.mystock.vo.ColorVo;

public interface ColorService {

	public ColorVo save(ColorVo colorVo);

	public Set<ColorVo> saveAll(Set<ColorVo> colorVos);

	public List<ColorVo> getAll();

	public ColorVo getById(Long id);

}
