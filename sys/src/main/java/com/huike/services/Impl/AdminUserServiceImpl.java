package com.huike.services.Impl;

import com.huike.common.PasswordHelper;
import com.huike.domain.AdminUser;
import com.huike.domain.AdminUserRole;
import com.huike.mapper.AdminUserMapper;
import com.huike.services.AdminUserService;
import com.huike.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("adminUserService")
public class AdminUserServiceImpl extends BaseServiceImpl implements AdminUserService {
    @Autowired
    @Qualifier("adminUserMapper")
    AdminUserMapper adminUserMapper;

    Map<String,Long> map = new HashMap<>();

    @Override
    public List<AdminUser> selectByPage(Long currPage, Long pageSize) {
        map.put("currPage",currPage);
        map.put("pageSize",pageSize);
        return adminUserMapper.selectByPage(map);
    }

    @Override
    public Long selectCount() {
        return adminUserMapper.selectCount();
    }

    @Override
    public Boolean addAdminUserRole1(Long adminUserId,Long roleId) {
        AdminUserRole adminUserRole = new AdminUserRole();
        adminUserRole.setAdminUserId(adminUserId);
        adminUserRole.setRoleId(roleId);
        return adminUserMapper.addAdminUserRole(adminUserRole);
    }

    @Override
    public Boolean addAdminUserRole2(String account, String password, String checkPid)  {
        AdminUser adminUser = null;
        try {
            adminUser = new AdminUser();
            adminUser.setAccount(account);
            adminUser.setPassword(password);
            PasswordHelper.generateMD5Pwd(adminUser);
            adminUser.setIsDeleted(false);
            adminUser.setIsDisabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = adminUserMapper.insert(adminUser);
        if (checkPid != null && !"".equals(checkPid)){
            String[] ids = checkPid.split(",");
            for (String roleId : ids) {
                AdminUserRole adminUserRole = new AdminUserRole();
                adminUserRole.setAdminUserId(adminUser.getId());
                adminUserRole.setRoleId(Long.parseLong(roleId));
                adminUserMapper.addAdminUserRole(adminUserRole);
            }
        }
        return true;
    }

    @Override
    public List<AdminUserRole> selectAdminUserRole(AdminUserRole adminUserRole) {
        return adminUserMapper.selectAdminUserRole(adminUserRole);
    }

    @Override
    public Boolean checkUserName(String account) {
        AdminUser adminUser = new AdminUser();
        adminUser.setAccount(account);
        List<AdminUser> adminUserList = adminUserMapper.select(adminUser);
        if (adminUserList.isEmpty()) {
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Boolean delAdminUserRole(AdminUserRole adminUserRole) {
        return adminUserMapper.delAdminUserRole(adminUserRole);
    }

    @Override
    public Boolean updateAdminUserDisabled(Long adminUserId) {
        AdminUser adminUser = new AdminUser();
        adminUser.setId(adminUserId);
        Boolean adminUserDisabled = adminUserMapper.getAdminUserDisabledById(adminUserId);
//        System.out.println(adminUserDisabled);
        if (adminUserDisabled == false){
            adminUser.setIsDisabled(true);
        }else {
            adminUser.setIsDisabled(false);
        }

        return adminUserMapper.updateAdminUserDisabled(adminUser);
    }

    @Override
    public AdminUser getAdminUserById(Long adminUserId) {
        AdminUser adminUser = (AdminUser) adminUserMapper.getAdminUserById(adminUserId);
        return adminUser;
    }

    @Override
    public Boolean deleteUser(Long adminUserId) {
         adminUserMapper.delete(adminUserId);
        return true;
    }

    @Override
    public AdminUser selectAdminUserByName(String account) {
        AdminUser adminUser = (AdminUser) adminUserMapper.selectAdminUserByName(account);
        return adminUser;
    }

    @Override
    public List<String> findRolesByUserName(String username) {
        AdminUser adminUser = (AdminUser) adminUserMapper.selectAdminUserByName(username);
        if (adminUser == null){
            return Collections.EMPTY_LIST;
        }
        return adminUserMapper.findRolesById(adminUser.getId());
    }

    @Override
    public List<String> findPermissionsByUserName(String username) {
        AdminUser adminUser = (AdminUser) adminUserMapper.selectAdminUserByName(username);
        if (adminUser == null){
            return Collections.EMPTY_LIST;
        }
        return adminUserMapper.findPermissionsById(adminUser.getId());
    }

//    @Override
//    public Boolean doAuthentication(AdminUser adminUser) {
//
//        AdminUser adminUser1 = (AdminUser) adminUserMapper.doAuthentication(adminUser);
//        if (adminUser != null){
//            return true;
//        }
//        return false;
//    }


    @Override
    public Boolean updatePwd(AdminUser adminUser) {
        int update = adminUserMapper.update(adminUser);
        if (update>0){
            return true;
        }
        return false;
    }
}
