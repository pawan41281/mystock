package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.mystock.apiresponse.ApiResponseVo;
import org.mystock.apiresponse.ApiResponseVoWrapper;
import org.mystock.exception.ResourceNotFoundException;
import org.mystock.service.MenuGroupService;
import org.mystock.vo.MenuGroupVo;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/menuGroups")
@Tag(name = "MenuGroup Operations", description = "MenuGroup operations")
@SecurityRequirement(name = "Bearer Authentication")
public class MenuGroupController {

    private final MenuGroupService menuGroupService;

    // Find Existing MenuGroup
    @GetMapping("/")
    @Operation(summary = "Find Operation", description = "Find existing menuGroup")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ApiResponseVo<List<MenuGroupVo>>> findAll(Authentication authentication)
            throws ResourceNotFoundException {
        try {

            Set<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());
            List<MenuGroupVo> list;
            if (roles.contains("ROLE_ADMIN")) {
                // ✅ Admin → fetch all menu groups
                list = menuGroupService.findAll();
            } else if (roles.contains("ROLE_USER")) {
                // ✅ User → fetch all except specific ID (example: exclude 'dashboard')
                list = menuGroupService.findByIdNotIgnoreCase("usermanagement");
            } else {
                throw new ResourceNotFoundException("Unauthorized role access");
            }

            String message = !list.isEmpty() ? "MenuGroups exist" : "MenuGroups not exists";
            Map<String, String> metadata = new HashMap<>();
            metadata.put("recordcount", String.valueOf(list!=null && !list.isEmpty() ? list.size() : 0));
            return ResponseEntity.status(201).body(ApiResponseVoWrapper.success(message, list, metadata));
        } catch (Exception e) {
            return ResponseEntity.status(201).body(ApiResponseVoWrapper.failure(e.getMessage(), null, null));
        }
    }
}