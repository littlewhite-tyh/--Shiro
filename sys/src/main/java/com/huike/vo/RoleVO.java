package com.huike.vo;

import java.io.Serializable;

public class RoleVO implements Serializable {
    private String roleName;//角色名称
    private String roleDesc;//角色描述
    private String [] rolePermissionId;//资源权限

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String[] getRolePermissionId() {
        return rolePermissionId;
    }

    public void setRolePermissionId(String[] rolePermissionId) {
        this.rolePermissionId = rolePermissionId;
    }
}
