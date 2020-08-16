package com.huike.domain;

import java.io.Serializable;

/**
 * 用户角色中间表
 */
public class AdminUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /** 外键表，指向管理员用户表  */
    private Long adminUserId;
    /** 外键表，指向角色表 */
    private Long roleId;

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
