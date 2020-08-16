package com.huike.domain;

import java.io.Serializable;

public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    /** 角色名称  */
    private String name;
    /** 角色描述 */
    private String description;
    /** 是否删除*/
    private Boolean isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
