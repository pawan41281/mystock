package org.mystock.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.entity.MenuGroupEntity;
import org.mystock.entity.MenuItemEntity;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.exception.UnableToProcessException;
import org.mystock.mapper.MenuGroupMapper;
import org.mystock.repository.MenuGroupRepository;
import org.mystock.service.MenuGroupService;
import org.mystock.vo.MenuGroupVo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class MenuGroupServiceImpl implements MenuGroupService {

	private final MenuGroupRepository menuGroupRepository;

	private final MenuGroupMapper menuGroupMapper;

	@Override
	public List<MenuGroupVo> findAll() throws UnableToProcessException {
		List<MenuGroupEntity> list = menuGroupRepository.findAll();

		if (!list.isEmpty())
			return list.stream().map(menuGroupMapper::convert).collect(Collectors.toList());
		else
			throw new ResourceNotFoundException("Record not exists");
	}

	@Override
	public List<MenuGroupVo> findByIdNotIgnoreCase(String id) throws UnableToProcessException {
		List<MenuGroupEntity> list = menuGroupRepository.findByIdNotIgnoreCase(id);

		if (!list.isEmpty())
			return list.stream().map(menuGroupMapper::convert).collect(Collectors.toList());
		else
			throw new ResourceNotFoundException("Record not exists");
	}

	@EventListener(ApplicationReadyEvent.class)
	@Override
	public void initMenuGroups() {

		log.info("Initializing Menu Groups...");

		MenuGroupEntity menuGroup = new MenuGroupEntity("dashboard","Dashboard","group","icon-navigation");
		MenuItemEntity menuItem = new MenuItemEntity("default", "Home", "item", "nav-item", "/dashboard/default", "dashboard", false, menuGroup);
		List<MenuItemEntity> menuItems = new ArrayList<MenuItemEntity>();
		menuItems.add(menuItem);
		menuGroup.setChildren(menuItems);
		if(!menuGroupRepository.existsByIdIgnoreCaseAndTitleIgnoreCase(menuGroup.getId(),menuGroup.getTitle()))
			menuGroupRepository.save(menuGroup);


		menuGroup = new MenuGroupEntity("daybook","Day Book","group","icon-navigation");
		menuItem = new MenuItemEntity("newcontractorchallan", "New Challan (Contractor)", "item", "nav-item", "/newcontractorchallan", "ant-design", true, menuGroup);
		MenuItemEntity menuItem2 = new MenuItemEntity("newpartychallan", "New Challan (Party)", "item", "nav-item", "/newpartychallan", "ant-design", true, menuGroup);
		MenuItemEntity menuItem3 = new MenuItemEntity("newpartyorder", "New Order (Party)", "item", "nav-item", "/newpartyorder", "ant-design", true, menuGroup);
		menuItems = new ArrayList<MenuItemEntity>();
		menuItems.add(menuItem);
		menuItems.add(menuItem2);
		menuItems.add(menuItem3);
		menuGroup.setChildren(menuItems);
		if(!menuGroupRepository.existsByIdIgnoreCaseAndTitleIgnoreCase(menuGroup.getId(),menuGroup.getTitle()))
			menuGroupRepository.save(menuGroup);


		menuGroup = new MenuGroupEntity("register","Register","group","icon-navigation");
		menuItem = new MenuItemEntity("stockregister", "Stock Register", "item", "nav-item", "/stockregister", "ant-design", true, menuGroup);
		menuItem2 = new MenuItemEntity("contractorstockregister", "Contractor Stock Register", "item", "nav-item", "/contractorstockregister", "ant-design", true, menuGroup);
		menuItem3 = new MenuItemEntity("partychallanregister", "Party Challan Register", "item", "nav-item", "/partychallanregister", "ant-design", true, menuGroup);
		MenuItemEntity menuItem4 = new MenuItemEntity("contractorchallanregister", "Contractor Challan Register", "item", "nav-item", "/contractorchallanregister", "ant-design", true, menuGroup);
		menuItems = new ArrayList<MenuItemEntity>();
		menuItems.add(menuItem);
		menuItems.add(menuItem2);
		menuItems.add(menuItem3);
		menuItems.add(menuItem4);
		menuGroup.setChildren(menuItems);
		if(!menuGroupRepository.existsByIdIgnoreCaseAndTitleIgnoreCase(menuGroup.getId(),menuGroup.getTitle()))
			menuGroupRepository.save(menuGroup);


		menuGroup = new MenuGroupEntity("masterdata","Master Data","group","icon-navigation");
		menuItem = new MenuItemEntity("color", "Color", "item", "nav-item", "/color", "ant-design", true, menuGroup);
		menuItem2 = new MenuItemEntity("design", "Design", "item", "nav-item", "/design", "ant-design", true, menuGroup);
		menuItem3 = new MenuItemEntity("party", "Party", "item", "nav-item", "/party", "ant-design", true, menuGroup);
		menuItem4 = new MenuItemEntity("contractor", "Contractor", "item", "nav-item", "/contractor", "ant-design", true, menuGroup);
		MenuItemEntity menuItem5 = new MenuItemEntity("openingStock", "Design Opening Stock", "item", "nav-item", "/openingstock", "ant-design", true, menuGroup);
		MenuItemEntity menuItem6 = new MenuItemEntity("contractoropeningstock", "Contractor Opening Stock", "item", "nav-item", "/contractoropeningstock", "ant-design", true, menuGroup);
		menuItems = new ArrayList<MenuItemEntity>();
		menuItems.add(menuItem);
		menuItems.add(menuItem2);
		menuItems.add(menuItem3);
		menuItems.add(menuItem4);
		menuItems.add(menuItem5);
		menuItems.add(menuItem6);
		menuGroup.setChildren(menuItems);
		if(!menuGroupRepository.existsByIdIgnoreCaseAndTitleIgnoreCase(menuGroup.getId(),menuGroup.getTitle()))
			menuGroupRepository.save(menuGroup);


		menuGroup = new MenuGroupEntity("usermanagement","User Management","group","icon-navigation");
		menuItem = new MenuItemEntity("newuser", "New User", "item", "nav-item", "/newuser", "ant-design", false, menuGroup);
		menuItem2 = new MenuItemEntity("searchuser", "Search User", "item", "nav-item", "/searchuser", "ant-design", false, menuGroup);
		menuItems = new ArrayList<MenuItemEntity>();
		menuItems.add(menuItem);
		menuItems.add(menuItem2);
		menuGroup.setChildren(menuItems);
		if(!menuGroupRepository.existsByIdIgnoreCaseAndTitleIgnoreCase(menuGroup.getId(),menuGroup.getTitle()))
			menuGroupRepository.save(menuGroup);


		menuGroup = new MenuGroupEntity("reports","Reports","group","icon-navigation");
		menuItem = new MenuItemEntity("contractoraccounstatement", "Contractor Account Statement", "item", "nav-item", "/contractoraccountstatement", "ant-design", false, menuGroup);
		menuItems = new ArrayList<MenuItemEntity>();
		menuItems.add(menuItem);
		menuGroup.setChildren(menuItems);
		if(!menuGroupRepository.existsByIdIgnoreCaseAndTitleIgnoreCase(menuGroup.getId(),menuGroup.getTitle()))
			menuGroupRepository.save(menuGroup);


	}
}