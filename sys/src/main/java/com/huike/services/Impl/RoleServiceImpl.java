package com.huike.services.Impl;

import com.huike.domain.Role;
import com.huike.mapper.RoleMapper;
import com.huike.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

    @Autowired
    RoleMapper roleMapper;

    Map<String,Long> map = new HashMap<>();

    @Override
    public List<Role> QueryAllRoles() {
        return roleMapper.QueryAllRoles();
    }

    @Override
    public Long selectCount() {

        return roleMapper.selectCount();
    }

    @Override
    public List<Role> selectRoleByPage(Long currPage, Long pageSize) {
        map.put("currPage",currPage);
        map.put("pageSize",pageSize);
        List<Role> roleList = roleMapper.selectByPage(map);
        return roleList;
    }

    @Override
    public Boolean deleteRole(Long roleId) {
        return roleMapper.deleteRole(roleId);
    }

    @Override
    public Boolean updateRole(Role role) {

        int i = roleMapper.updateRole(role);
        if (i == 1)
            return true;
        else
            return false;
    }

    @Override
    public Boolean addRole(Role role) {
        int insert = roleMapper.insert(role);
        if (insert == 1)
            return true;
        else
            return false;
    }
}
