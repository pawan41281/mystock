package org.mystock.service;

import org.mystock.vo.ColorVo;

import java.util.List;
import java.util.Set;

public interface ColorService {

	ColorVo save(ColorVo colorVo);

	Set<ColorVo> saveAll(Set<ColorVo> colorVos);

	List<ColorVo> getAll();

	List<ColorVo> getAll(Boolean active);

	List<ColorVo> findByNameIgnoreCaseLike(String colorName);

	ColorVo findByNameIgnoreCase(String colorName);

	ColorVo getById(Long id);

	ColorVo updateStatus(Long id, boolean status);

}