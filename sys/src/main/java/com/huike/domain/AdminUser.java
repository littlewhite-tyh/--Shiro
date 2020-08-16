package com.huike.domain;

import java.io.Serializable;

public class AdminUser implements Serializable {
    private static final long serialVersionUID = 1L;
    /**编号*/
    private Long id;
    /** 用户名*/
    private String account;
    /** 密码 */
    private String password;
    /** 密码盐 */
    private String passwordSalt;
    /** 禁用/启用 */
    private Boolean isDisabled;
    /** 是否删除*/
    private Boolean isDeleted;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public Boolean getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(boolean disabled) {
        isDisabled = disabled;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
