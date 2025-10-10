package org.mystock.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "MenuGroup Operations", description = "Endpoints for fetching MenuGroup data based on user roles")
@SecurityRequirement(name = "Bearer Authentication")
public class MenuGroupController {

    private final MenuGroupService menuGroupService;

    /**
     * Endpoint: /v1/menuGroups/
     *
     * Fetches MenuGroup details based on the logged-in user's role.
     * - **ADMIN** users can fetch all menu groups.
     * - **USER** users can fetch all except certain restricted menu groups.
     */
    @GetMapping("/")
    @Operation(
            summary = "Fetch MenuGroup List",
            description = "Retrieve a list of available MenuGroups based on the user's role. "
                    + "Admins receive the full list, while Users receive a filtered list."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "MenuGroups fetched successfully"),
            @ApiResponse(responseCode = "404", description = "No MenuGroups found or unauthorized role"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<ApiResponseVo<List<MenuGroupVo>>> findAll(
            @Parameter(hidden = true) Authentication authentication) throws ResourceNotFoundException {

        try {
            // Extract user roles from authentication
            Set<String> roles = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            List<MenuGroupVo> list;

            if (roles.contains("ROLE_ADMIN")) {
                // ✅ Admin → fetch all menu groups
                list = menuGroupService.findAll();
            } else if (roles.contains("ROLE_USER")) {
                // ✅ User → fetch all except specific menu group (e.g., user management)
                list = menuGroupService.findByIdNotIgnoreCase("usermanagement");
            } else {
                throw new ResourceNotFoundException("Unauthorized role access");
            }

            String message = (list != null && !list.isEmpty()) ? "MenuGroups found" : "No MenuGroups available";

            Map<String, String> metadata = new HashMap<>();
            metadata.put("recordcount", String.valueOf(list != null ? list.size() : 0));

            return ResponseEntity.ok(ApiResponseVoWrapper.success(message, list, metadata));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(ApiResponseVoWrapper.failure(e.getMessage(), null, null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponseVoWrapper.failure("Error fetching menu groups: " + e.getMessage(), null, null));
        }
    }
}