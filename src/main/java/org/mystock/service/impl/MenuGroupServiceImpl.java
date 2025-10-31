package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.entity.MenuGroupEntity;
import org.mystock.entity.MenuItemEntity;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.mapper.MenuGroupMapper;
import org.mystock.mapper.RoleMapper;
import org.mystock.repository.MenuGroupRepository;
import org.mystock.service.MenuGroupService;
import org.mystock.service.RoleService;
import org.mystock.vo.MenuGroupVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MenuGroupServiceImpl implements MenuGroupService {

	private final MenuGroupRepository menuGroupRepository;

	private final MenuGroupMapper menuGroupMapper;

	private final RoleService roleService;

	private final RoleMapper roleMapper;

	@Override
	public List<MenuGroupVo> findAll(Set<String> roles) throws UnableToProcessException {
		List<MenuGroupEntity> list = menuGroupRepository.findDistinctByRole_NameInIgnoreCase(roles);
		List<MenuGroupEntity> tmpList = new ArrayList<>();

		if (!list.isEmpty()) {
			list.forEach(mg -> {
				List<MenuItemEntity> items = mg.getChildren()
												.stream()
												.filter(mi -> (
														mi.getRole()!=null
														&&
														mi.getRole().getName()!=null
														&&
														roles.contains(mi.getRole().getName())
														&&
														mi.getActive()!=null
														&&
														mi.getActive().equals(true)))
												.collect(Collectors.toList());
				mg.setChildren(items);
				tmpList.add(mg);
			});
			return tmpList.stream().map(menuGroupMapper::convert).collect(Collectors.toList());
		}
		else
			throw new ResourceNotFoundException("Record not exists");
	}

}