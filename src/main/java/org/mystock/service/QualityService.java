package org.mystock.service;

import org.mystock.vo.QualityVo;

import java.util.List;
import java.util.Set;

public interface QualityService {

	public QualityVo save(QualityVo qualityVo);

	public Set<QualityVo> saveAll(Set<QualityVo> qualityVos);

	public List<QualityVo> getAll();

	public List<QualityVo> findByNameIgnoreCaseLike(String qualityName);

	public QualityVo findByNameIgnoreCase(String qualityName);

	public QualityVo getById(Long id);

	public QualityVo updateStatus(Long id, boolean status);

}