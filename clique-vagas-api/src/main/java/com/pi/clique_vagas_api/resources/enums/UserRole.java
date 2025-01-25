package com.pi.clique_vagas_api.resources.enums;

public enum UserRole {
    ADMIN("admin"),
    INTERN("intern"),
    COMPANY("company");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
