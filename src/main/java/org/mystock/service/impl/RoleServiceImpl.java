package org.mystock.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.mystock.entity.RoleEntity;
import org.mystock.mapper.RoleMapper;
import org.mystock.repository.RoleRepository;
import org.mystock.service.RoleService;
import org.mystock.vo.RoleVo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

	private final RoleRepository repository;
	private final RoleMapper mapper;

	@EventListener(ApplicationReadyEvent.class)
	public void initRoles() {

		log.info("Initializing default roles...");

		RoleEntity admin = repository.findByNameIgnoreCase("ROLE_ADMIN");
		log.info("Admin Role Found :: {}", admin!=null?admin:"Not exist");

		RoleEntity user = repository.findByNameIgnoreCase("ROLE_USER");
		log.info("User Role Found :: {}", user!=null?user:"Not exist");

		if (admin == null) {
			admin = repository.save(new RoleEntity(null, "ROLE_ADMIN"));
			log.info("Role Created :: {}", admin);
		}

		if (user == null) {
			user = repository.save(new RoleEntity(null, "ROLE_USER"));
			log.info("Role Created :: {}", user);
		}
	}

	@Override
	public List<RoleVo> getAll() {
		return repository.findAll().stream().map(mapper::toVo).collect(Collectors.toList());
	}
}
