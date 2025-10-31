package org.mystock.service;

import org.mystock.vo.QualityVo;

import java.util.List;
import java.util.Set;

public interface QualityService {

	QualityVo save(QualityVo qualityVo);

	Set<QualityVo> saveAll(Set<QualityVo> qualityVos);

	List<QualityVo> getAll();

	List<QualityVo> findByNameIgnoreCaseLike(String qualityName);

	QualityVo findByNameIgnoreCase(String qualityName);

	QualityVo getById(Long id);

	QualityVo updateStatus(Long id, boolean status);

}