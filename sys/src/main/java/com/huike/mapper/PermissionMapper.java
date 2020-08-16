package com.huike.mapper;

import com.huike.domain.Permission;
import com.huike.domain.RolePermission;
import com.sun.org.apache.bcel.internal.generic.INEG;

import java.util.List;
import java.util.Map;

public interface PermissionMapper extends Imapper {
    /**
     * @Description: 分页查询
     */
    List<Permission> selectByPage(Map<String, Long> map);

    /**
     * @Description: 查询总数
     */
    Long selectCount();

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
    List<RolePermission> getPermissionIds(RolePermission rolePermission);

//    /**
//     * 通过permissionId得到permission
//     */
//    Permission getPermissionById(Long permissionId);

    /**
     * 伪删除
     * @param permissionId
     * @return
     */

    Integer deletePermission(Long permissionId);

    /**
     * 更新
     */
    Integer updatePermission(Permission permission);

    /**
     * add
     */
    Integer addPermission(Permission permission);
}
