package com.gorent.api.model.enums;

public enum UserRole {
    GUEST("GUEST"),
    AGENCY("AGENCY"),
    TENANT("TENANT"),
    SUPER_ADMIN("SUPER_ADMIN");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
