package com.szabolcs.SpringbootWebshop.Model;

public enum RoleEnum {

    USER,
    ADMIN;

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public String toString() {
        return ROLE_PREFIX + this.name();
    }
}
