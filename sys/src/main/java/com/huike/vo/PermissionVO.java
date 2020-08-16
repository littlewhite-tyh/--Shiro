package com.huike.vo;

import java.io.Serializable;

public class PermissionVO implements Serializable {
    private String permission;
    private String description;

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
