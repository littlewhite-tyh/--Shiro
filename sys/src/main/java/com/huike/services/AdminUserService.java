package com.huike.services;

import com.huike.domain.AdminUser;
import com.huike.domain.AdminUserRole;
import org.springframework.stereotype.Service;

import javax.swing.text.StyledEditorKit;
import java.util.List;

public interface AdminUserService extends BaseService {
    /**
     * @Description: 分页查询
     */
    public List<AdminUser> selectByPage(Long currPage, Long pageSize);

    /**
     * @Description: 查询总数
     */
    public Long selectCount();

    /**
     * 添加用户角色信息表
     */
    public Boolean addAdminUserRole1(Long adminUserId,Long roleId);

    public Boolean addAdminUserRole2(String account,String password,String checkPid) ;

    /**
     *
     *验证重名
     */
    public Boolean checkUserName(String account);

    /**
     * 查询用户当前所有的角色
     */
    List<AdminUserRole> selectAdminUserRole(AdminUserRole adminUserRole);

    /**
     *    删除用户角色表关联
     */
    Boolean delAdminUserRole(AdminUserRole adminUserRole);

    /**
     * 启用或禁用用户
     */
    Boolean updateAdminUserDisabled(Long adminUserId);

    /**
     * 通过查询Id得到AdminUser
     */
    AdminUser getAdminUserById(Long adminUserId);

    /**
     * 删除用户
     */
    Boolean deleteUser(Long adminUserId);

    /**
     * 通过用户姓名得到用户
     */
    AdminUser selectAdminUserByName(String account);


    /**
     * 查询当前账号的所有角色
     */
    List<String> findRolesByUserName(String account);

    /**
     * 查询当前账号的所有权限
     */
    List<String> findPermissionsByUserName(String account);

//    /**
//     * 查询是否能改密码
//     */
//    Boolean doAuthentication(AdminUser adminUser);
    /**
     * 更改密码
     */
    Boolean updatePwd(AdminUser adminUser);

}
