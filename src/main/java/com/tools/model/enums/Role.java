package com.tools.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public enum Role implements GrantedAuthority {
    ADMIN("Администратор"),
    EMPLOYEE("Менеджер"),
    BUYER("Покупатель");

    private final String name;

    @Override
    public String getAuthority() {
        return name();
    }
}

