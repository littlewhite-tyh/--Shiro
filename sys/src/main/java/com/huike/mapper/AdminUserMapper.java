package com.huike.mapper;

import com.huike.domain.AdminUser;
import com.huike.domain.AdminUserRole;

import java.util.List;
import java.util.Map;

public interface AdminUserMapper<AdminUser> extends Imapper{
    /**
     * @Description: 分页查询
     */
    List<AdminUser> selectByPage(Map<String, Long> map);

    /**
     * @Description: 查询总数
     */
    Long selectCount();

    /**
     * 添加用户角色信息表
     */
    Boolean addAdminUserRole(AdminUserRole adminUserRole);

    /**
     * 删除角色用户表关联
     */
    Boolean delAdminUserRole(AdminUserRole adminUserRole);

    /**
     * 查询用户当前所有的角色
     */
    List<AdminUserRole> selectAdminUserRole(AdminUserRole adminUserRole);

    /**
     * 通过当前id得到AdminUser启用状态
     */
    Boolean getAdminUserDisabledById(Long id);

    /**
     * 更改当前用户的启用状态
     */
    Boolean updateAdminUserDisabled(AdminUser adminUser);

    /**
     * 通过查询Id得到AdminUser
     */
    AdminUser getAdminUserById(Long adminUserId);


    /**
     * 通过用户姓名得到用户
     */
    AdminUser selectAdminUserByName(String account);

    /*
    查询当前用户的角色
     */
    List<String> findRolesById(Long adminUserId);
    /**
     * 查询当前用户的所有权限
     */
    List<String> findPermissionsById(Long adminUserId);

//    /**
//     * 查询是否能改密码
//     */
//    AdminUser doAuthentication(AdminUser adminUser);



}
