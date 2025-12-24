package org.mystock.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mystock.entity.*;
import org.mystock.repository.*;
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

    private final ColorRepository colorRepository;

    private final DesignRepository designRepository;

    private final QualityRepository qualityRepository;

    private final ClientRepository clientRepository;

    private final ContractorRepository contractorRepository;

    private final PropertyRepository propertyRepository;

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

        UserEntity adminUser = new UserEntity(1L, "admin", "admin", "admin@gmail.com", "1234567890", encoder.encode("admin"), false);
        Set<RoleEntity> roles = new HashSet<>(roleList);  //resolveRoles(adminRole);
        Set<RoleEntity> adminRoles = new HashSet<>();
        adminRoles.add(adminRoleEntity);
        adminUser.setRoles(adminRoles);
        UserEntity ashish = new UserEntity(2L, "ashish", "ashish", "ashish@gmail.com", "1020304050", encoder.encode("ashish"), false);
        Set<RoleEntity> userRoles = new HashSet<>();
        userRoles.add(userRoleEntity);
        ashish.setRoles(userRoles);

        userRepository.saveAndFlush(adminUser);
        userRepository.saveAndFlush(ashish);

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
        usermanagementMenuItems.add(new MenuItemEntity("newuser", "Users", "item", "nav-item", "/newuser", "ant-design", false, usermanagementMenuGroup, adminRoleEntity));
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

        log.info("Initializing default colors...");
        List<ColorEntity> colorEntityList = new ArrayList<>();
        colorEntityList.add(new ColorEntity(1L,"Red",adminUser));
        colorEntityList.add(new ColorEntity(2L, "Green",adminUser));
        colorEntityList.add(new ColorEntity(3L, "Blue",adminUser));
        colorEntityList.add(new ColorEntity(4L, "Yellow",adminUser));
        colorEntityList.add(new ColorEntity(5L, "Navy",adminUser));
        colorEntityList.add(new ColorEntity(6L, "Maroon",adminUser));
        colorRepository.saveAll(colorEntityList);
        log.info("Colors initialization completed...");

        log.info("Initializing default designs...");
        List<DesignEntity> designEntityList = new ArrayList<>();
        designEntityList.add(new DesignEntity(1L, "3570",adminUser));
        designEntityList.add(new DesignEntity(2L, "3590",adminUser));
        designEntityList.add(new DesignEntity(3L, "2210",adminUser));
        designEntityList.add(new DesignEntity(4L, "2250",adminUser));
        designEntityList.add(new DesignEntity(5L, "5540",adminUser));
        designEntityList.add(new DesignEntity(6L, "3070",adminUser));
        designRepository.saveAll(designEntityList);
        log.info("Designs initialization completed...");

        log.info("Initializing default qualities...");
        List<QualityEntity> qualityEntityList = new ArrayList<>();
        qualityEntityList.add(new QualityEntity(1L, "Superfine",adminUser));
        qualityEntityList.add(new QualityEntity(2L, "Standard",adminUser));
        qualityEntityList.add(new QualityEntity(3L, "Eco",adminUser));
        qualityEntityList.add(new QualityEntity(4L, "Hector",adminUser));
        qualityEntityList.add(new QualityEntity(5L, "Pure",adminUser));
        qualityRepository.saveAll(qualityEntityList);
        log.info("Qualities initialization completed...");

        log.info("Initializing default parties...");
        List<ClientEntity> clientEntityList = new ArrayList<>();
        clientEntityList.add(new ClientEntity(1L, "Aman Handloom", "4122334455", adminUser));
        clientEntityList.add(new ClientEntity(2L, "Shree Textile", "5478956878", adminUser));
        clientEntityList.add(new ClientEntity(3L, "Jainsons Textile", "5845721478", adminUser));
        clientEntityList.add(new ClientEntity(4L, "Golden Fabs", "5478521111", adminUser));
        clientEntityList.add(new ClientEntity(5L, "Maa Durga Traders", "4751521478", adminUser));
        clientRepository.saveAll(clientEntityList);
        log.info("Parties initialization completed...");

        log.info("Initializing default contractors...");
        List<ContractorEntity> contractorEntityList = new ArrayList<>();
        contractorEntityList.add(new ContractorEntity(1L, "Ramesh Singh", "1234512345", adminUser));
        contractorEntityList.add(new ContractorEntity(2L, "Shambhu Nath", "2477815478", adminUser));
        contractorEntityList.add(new ContractorEntity(3L, "Jairam Saini", "3778154784", adminUser));
        contractorEntityList.add(new ContractorEntity(4L, "Mukesh Kumar", "9978154730", adminUser));
        contractorEntityList.add(new ContractorEntity(5L, "Deepak Sharma", "8378154720", adminUser));
        contractorRepository.saveAll(contractorEntityList);
        log.info("Contractors initialization completed...");

        log.info("Initializing default properties...");
        List<PropertyEntity> propertyEntityList = new ArrayList<>();
        propertyEntityList.add(new PropertyEntity(1L, "VALD", "31", adminUser));
        propertyEntityList.add(new PropertyEntity(2L, "VALM", "12", adminUser));
        propertyEntityList.add(new PropertyEntity(3L, "VALY", "2025", adminUser));
        propertyRepository.saveAll(propertyEntityList);
        log.info("Properties initialization completed...");

    }
}