package org.mystock.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.entity.MenuGroupEntity;
import org.mystock.entity.MenuItemEntity;
import org.mystock.entity.RoleEntity;
import org.mystock.entity.UserEntity;
import org.mystock.repository.MenuGroupRepository;
import org.mystock.repository.RoleRepository;
import org.mystock.repository.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
@Slf4j
public class DefaultDataConfig {

    private final MenuGroupRepository menuGroupRepository;

    private final RoleRepository repository;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @EventListener(ApplicationReadyEvent.class)
    private void initializeDefaultData() {

        log.info("Initializing default roles...");

        List<RoleEntity> roleList = new ArrayList<>();
        RoleEntity adminRoleEntity = new RoleEntity(1L, "ROLE_ADMIN", true);
        RoleEntity userRoleEntity = new RoleEntity(2L, "ROLE_USER", false);
        roleList.add(adminRoleEntity);
        roleList.add(userRoleEntity);
        repository.saveAllAndFlush(roleList);

        log.info("Role initialization completed...");


        log.info("initializing default users");

        UserEntity defaultUser = new UserEntity(1L, "admin", "admin", "admin@gmail.com", "1234567890", encoder.encode("admin"), false);
        Set<RoleEntity> roles = new HashSet<>(roleList);  //resolveRoles(adminRole);
        defaultUser.setRoles(roles);
        userRepository.saveAndFlush(defaultUser);

        log.info("User initialization completed...");


        log.info("Initializing Menu and menu options...");

        MenuGroupEntity dashboardMenuGroup = new MenuGroupEntity("dashboard", "Dashboard", "group", "icon-navigation", userRoleEntity);
        List<MenuItemEntity> dashboardMenuItems = new ArrayList<>();
        dashboardMenuItems.add(new MenuItemEntity("default", "Home", "item", "nav-item", "/dashboard/default", "dashboard", false, dashboardMenuGroup, userRoleEntity));
        dashboardMenuGroup.setChildren(dashboardMenuItems);


        MenuGroupEntity dayBookMenuGroup = new MenuGroupEntity("daybook", "Day Book", "group", "icon-navigation", userRoleEntity);
        List<MenuItemEntity> daybookMenuItems = new ArrayList<>();
        daybookMenuItems.add(new MenuItemEntity("newcontractorchallan", "New Challan (Contractor)", "item", "nav-item", "/newcontractorchallan", "ant-design", true, dayBookMenuGroup, userRoleEntity));
        daybookMenuItems.add(new MenuItemEntity("newpartychallan", "New Challan (Party)", "item", "nav-item", "/newpartychallan", "ant-design", true, dayBookMenuGroup, userRoleEntity));
        daybookMenuItems.add(new MenuItemEntity("newpartyorder", "New Order (Party)", "item", "nav-item", "/newpartyorder", "ant-design", true, dayBookMenuGroup, userRoleEntity));
        daybookMenuItems.add(new MenuItemEntity("newcontractorpayment", "New Payment (Contractor)", "item", "nav-item", "/newcontractorpayment", "ant-design", true, dayBookMenuGroup, userRoleEntity));
        dayBookMenuGroup.setChildren(daybookMenuItems);


        MenuGroupEntity registerMenuGroup = new MenuGroupEntity("register", "Register", "group", "icon-navigation", userRoleEntity);
        List<MenuItemEntity> registerMenuItems = new ArrayList<>();
        registerMenuItems.add(new MenuItemEntity("stockregister", "Stock Register", "item", "nav-item", "/stockregister", "ant-design", true, registerMenuGroup, userRoleEntity));
        registerMenuItems.add(new MenuItemEntity("contractorstockregister", "Contractor Stock Register", "item", "nav-item", "/contractorstockregister", "ant-design", true, registerMenuGroup, userRoleEntity));
        registerMenuItems.add(new MenuItemEntity("partychallanregister", "Party Challan Register", "item", "nav-item", "/partychallanregister", "ant-design", true, registerMenuGroup, userRoleEntity));
        registerMenuItems.add(new MenuItemEntity("orderregister", "Order Register", "item", "nav-item", "/orderregister", "ant-design", true, registerMenuGroup, userRoleEntity));
        registerMenuItems.add(new MenuItemEntity("contractorchallanregister", "Contractor Challan Register", "item", "nav-item", "/contractorchallanregister", "ant-design", true, registerMenuGroup, userRoleEntity));
        registerMenuItems.add(new MenuItemEntity("paymentregister", "Payment Register", "item", "nav-item", "/paymentregister", "ant-design", true, registerMenuGroup, userRoleEntity));
        registerMenuGroup.setChildren(registerMenuItems);


        MenuGroupEntity masterdataMenuGroup = new MenuGroupEntity("masterdata", "Master Data", "group", "icon-navigation", userRoleEntity);
        List<MenuItemEntity> masterdataMenuItems = new ArrayList<>();
        masterdataMenuItems.add(new MenuItemEntity("color", "Color", "item", "nav-item", "/color", "ant-design", true, masterdataMenuGroup, userRoleEntity));
        masterdataMenuItems.add(new MenuItemEntity("quality", "Quality", "item", "nav-item", "/quality", "ant-design", true, masterdataMenuGroup, userRoleEntity));
        masterdataMenuItems.add(new MenuItemEntity("design", "Design", "item", "nav-item", "/design", "ant-design", true, masterdataMenuGroup, userRoleEntity));
        masterdataMenuItems.add(new MenuItemEntity("party", "Party", "item", "nav-item", "/party", "ant-design", true, masterdataMenuGroup, userRoleEntity));
        masterdataMenuItems.add(new MenuItemEntity("contractor", "Contractor", "item", "nav-item", "/contractor", "ant-design", true, masterdataMenuGroup, userRoleEntity));
        masterdataMenuItems.add(new MenuItemEntity("openingStock", "Design Opening Stock", "item", "nav-item", "/openingstock", "ant-design", true, masterdataMenuGroup, userRoleEntity));
        masterdataMenuItems.add(new MenuItemEntity("contractoropeningstock", "Contractor Opening Stock", "item", "nav-item", "/contractoropeningstock", "ant-design", true, masterdataMenuGroup, userRoleEntity));
        masterdataMenuGroup.setChildren(masterdataMenuItems);


        MenuGroupEntity usermanagementMenuGroup = new MenuGroupEntity("usermanagement", "User Management", "group", "icon-navigation", adminRoleEntity);
        List<MenuItemEntity> usermanagementMenuItems = new ArrayList<>();
        usermanagementMenuItems.add(new MenuItemEntity("newuser", "New User", "item", "nav-item", "/newuser", "ant-design", false, usermanagementMenuGroup, adminRoleEntity));
        usermanagementMenuGroup.setChildren(usermanagementMenuItems);


        MenuGroupEntity reportsMenuGroup = new MenuGroupEntity("reports", "Reports", "group", "icon-navigation", userRoleEntity);
        List<MenuItemEntity> reportsMenuItems = new ArrayList<>();
        reportsMenuItems.add(new MenuItemEntity("contractoraccounstatement", "Account Statement", "item", "nav-item", "/contractoraccountstatement", "ant-design", false, reportsMenuGroup, userRoleEntity));
        reportsMenuGroup.setChildren(reportsMenuItems);

        List<MenuGroupEntity> menuGroupEntityList = new ArrayList<>();
        menuGroupEntityList.add(dashboardMenuGroup);
        menuGroupEntityList.add(dayBookMenuGroup);
        menuGroupEntityList.add(registerMenuGroup);
        menuGroupEntityList.add(masterdataMenuGroup);
        menuGroupEntityList.add(usermanagementMenuGroup);
        menuGroupEntityList.add(reportsMenuGroup);
        menuGroupRepository.saveAllAndFlush(menuGroupEntityList);

        log.info("Menu and menu options initialization completed...");
    }
}