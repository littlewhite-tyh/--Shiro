package com.huike.services.Impl;

import com.huike.domain.AdminUser;
import com.huike.domain.Permission;
import com.huike.domain.RolePermission;
import com.huike.mapper.PermissionMapper;
import com.huike.services.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    Map<String, Long> map = new HashMap<>();

    @Override
    public List<Permission> selectByPage(Long currPage, Long pageSize) {
        map.put("currPage",currPage);
        map.put("pageSize",pageSize);
        return permissionMapper.selectByPage(map);
    }

    @Override
    public Long selectCount() {
        return permissionMapper.selectCount();
    }
    @Override
    public List<Permission> getAllPermissions(Permission permission) {

        return permissionMapper.select(permission);
    }

    @Override
    public Boolean delRolePermissions(RolePermission rolePermission) {
        return permissionMapper.delRolePermissions(rolePermission);
    }

    @Override
    public Boolean addRolePermissions(RolePermission rolePermission) {
        return permissionMapper.addRolePermissions(rolePermission);
    }

    @Override
    public List<RolePermission> getPermissionIdsByRoleId(Long roleId) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        return permissionMapper.getPermissionIds(rolePermission);
    }

//    @Override
//    public Permission getPermissionById(Long permissionId) {
//        return permissionMapper.getPermissionById(permissionId);
//    }


    @Override
    public Boolean deletePermission(Long permissionId) {
        Integer deletePermission = permissionMapper.deletePermission(permissionId);
        if (deletePermission != null){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updatePermission(Permission permission) {
        Integer updatePermission = permissionMapper.updatePermission(permission);
        if (updatePermission != null){
            return true;
        }
        return false;
    }

    @Override
    public Boolean addPermission(Permission permission) {
        Integer addPermission = permissionMapper.addPermission(permission);
        if (addPermission != null){
            return true;
        }
        return false;
    }
}
