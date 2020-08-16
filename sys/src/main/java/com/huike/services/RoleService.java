package com.huike.services;

import com.huike.domain.Role;

import java.util.List;

public interface RoleService extends BaseService {
    /**
     * 查询所有的角色信息
     * @return
     */
    public List<Role> QueryAllRoles();
    /**
     * 查询role表所有的记录数
     */
    public Long selectCount();

    /**
     * 分页
     */
    public List<Role> selectRoleByPage(Long currPage,Long pageSize);

    /**
     * 删除用户
     */
    Boolean deleteRole(Long roleId);

    /**
     * 更新role中信息
     */
    Boolean updateRole(Role role);

    /**
     * 添加用户
     */
    Boolean addRole(Role role);
}
