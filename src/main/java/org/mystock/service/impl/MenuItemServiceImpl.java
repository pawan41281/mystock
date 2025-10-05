package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.entity.MenuItemEntity;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.mapper.MenuItemMapper;
import org.mystock.repository.MenuItemRepository;
import org.mystock.service.MenuItemService;
import org.mystock.vo.MenuItemVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

	private final MenuItemRepository menuItemRepository;

	private final MenuItemMapper menuItemMapper;

	@Override
	public List<MenuItemVo> findAll() throws UnableToProcessException {
		List<MenuItemEntity> list = menuItemRepository.findAll();

		if (!list.isEmpty())
			return list.stream().map(menuItemMapper::convert).collect(Collectors.toList());
		else
			throw new ResourceNotFoundException("Record not exists");
	}
}