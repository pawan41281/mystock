package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.mapper.RoleMapper;
import org.mystock.repository.RoleRepository;
import org.mystock.service.RoleService;
import org.mystock.vo.RoleVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

	private final RoleRepository repository;
	private final RoleMapper mapper;

	@Override
	public List<RoleVo> getAll() {
		List<RoleVo> list = new ArrayList<>();
		list.addAll(repository.findAll().stream().map(mapper::toVo).collect(Collectors.toList()));
		return list;
	}
}