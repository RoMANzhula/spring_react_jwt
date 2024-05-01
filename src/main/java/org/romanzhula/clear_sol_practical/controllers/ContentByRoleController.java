package org.romanzhula.clear_sol_practical.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/content")
public class ContentByRoleController {

    @GetMapping("/public")
    public String forPublicAccess() {
        return "Public content. Can see all.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER') or hasRole('MODERAOR') or hasRole('ADMIN')")
    public String forAllRolesContent() {
        return "Content for all roles. Can see Users, Moderators and Admins.";
    }

    @GetMapping("/moderator")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public String forModeratorAndAdminContent() {
        return "Content for moderator role. Can see Moderators and Admins";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String forAdminContent() {
        return "Content for Admin role. Can see only Admins.";
    }
}
