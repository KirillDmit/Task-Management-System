package org.example.entity;

import org.springframework.security.core.GrantedAuthority;

public class RoleAuthority implements GrantedAuthority {

    private final Role role;

    public RoleAuthority(Role role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role.name();
    }
}

