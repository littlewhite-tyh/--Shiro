package com.huike.services;

import com.huike.domain.AdminUser;
import com.huike.domain.Permission;
import com.huike.domain.RolePermission;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PermissionService extends BaseService {
    /**
     * @Description: 分页查询
     */
    public List<Permission> selectByPage(Long currPage, Long pageSize);

    /**
     * @Description: 查询总数
     */
    public Long selectCount();
    /**'
     *得到所有的权限
     */
    List<Permission> getAllPermissions(Permission permission);

    /**
     * 删除角色权限关联
     */
    Boolean delRolePermissions(RolePermission rolePermission);

    /**
     * 添加角色权限关联
     */

    Boolean addRolePermissions(RolePermission rolePermission);

    /**
     * 通过角色ID得到所关联的权限列表
     */
    List<RolePermission> getPermissionIdsByRoleId(Long roleId);

//    /**
//     * 通过permissionId得到permission
//     */
//    Permission getPermissionById(Long permissionId);

    /**
     * 伪删除
     * @param permissionId
     * @return
     */

    Boolean deletePermission(Long permissionId);

    /**
     * 更新
     */
    Boolean updatePermission(Permission permission);

    /**
     * add
     */
    Boolean addPermission(Permission permission);

}
