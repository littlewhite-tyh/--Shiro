package com.huike.controller;

import com.huike.common.PasswordHelper;
import com.huike.domain.AdminUser;
import com.huike.domain.AdminUserRole;
import com.huike.domain.Role;
import com.huike.services.AdminUserService;
import com.huike.services.RoleService;
import com.huike.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.graalvm.compiler.replacements.nodes.CStringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.ObjectView;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("adminUser")
public class AdminUserController {
    @Autowired
    @Qualifier("adminUserService")
    AdminUserService adminUserService;

    @Autowired
    @Qualifier("roleService")
    RoleService roleService;

    /**
     * @Description: 用户列表
     */
    @RequiresPermissions("adminUser:list")
    @RequestMapping("list.do")
    public String doList(Model model,
                         @RequestParam(value = "currPage", required = false) Long curr,
                         @RequestParam(value = "pageSize", required = false) Long pageSize) {
        if (curr == null) {
            curr = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        //当前页-1乘每页显示的记录数，主要用来求limit  起始记录
        Long currPage = (curr - 1) * pageSize;
        //求总记录条数
        Long count = adminUserService.selectCount();
        Long totalPage = 0L;
        //求总页数，总页数=总记录数%每页显示，如果刚好整除和有余数，2种情况。有余数的话，页码+1
        if (count > 0) {
            totalPage = count % pageSize == 0 ? count / pageSize : (count / pageSize) + 1;
        }
        List<AdminUser> adminUserList = adminUserService.selectByPage(currPage, pageSize);
//        AdminUser adminUser = adminUserList.get(1);
//        System.out.println(adminUser.getisDisabled());
        model.addAttribute("adminUserList", adminUserList);
        model.addAttribute("count", count);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("currPage", curr);
        model.addAttribute("pageSize", pageSize);
        return "adminUser/list";//返回视图资源
//        return adminUserList;
    }

    /**
     * 跳转到添加用户的页面
     */
//    @ResponseBody
    @RequiresPermissions("adminUser:add")
    @RequestMapping("adminUserAdd.go")
    public String goAdd(Model model) {

        List<Role> roleList = roleService.QueryAllRoles();
        model.addAttribute("roleList", roleList);
//        return roleList;
        return "adminUser/add";

    }

    /**
     * 提交添加用户请求
     */
    @RequiresPermissions("adminUser:add")
    @ResponseBody
    @RequestMapping(value = "/adminUserAdd.do", method = RequestMethod.POST)
    public Map<String, Object> doAdd(HttpServletRequest request,
                                     @RequestParam(value = "account", required = true) String account,
                                     @RequestParam(value = "password", required = true) String password,
                                     @RequestParam(value = "checkPid", required = false) String checkPid) {
        //处理数据添加
        Map<String, Object> maps = new HashMap<>();
        try {
            adminUserService.addAdminUserRole2(account, password, checkPid);
            maps.put("status", 200);
            maps.put("message", "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            maps.put("status", 500);
            maps.put("message", "添加失败");
        }
        return maps;
    }

    /**
     * 检查用户名是否已存在
     */
    @RequiresPermissions("adminUser:add")
    @RequestMapping(value = "checkUserName.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> checkAccountName(@RequestParam(value = "account", required = true) String account) {

        Map<String, Object> resultMap = new HashMap<>();
        Boolean isNamed = false;
        isNamed = adminUserService.checkUserName(account);
        if (isNamed == true) {
            resultMap.put("status", 200);
            resultMap.put("message", true);
        } else {
            resultMap.put("status", 204);
            resultMap.put("message", false);
        }
        return resultMap;
    }

    /**
     * 跳转至用户修改页面
     */
    @RequiresPermissions("adminUser:updateAdminUserRole")
    @RequestMapping(value = "updateAdminUserRole.go", method = RequestMethod.GET)
    public String updateAdminUserRole(@RequestParam(value = "adminUserId", required = true) String adminUserId,
                                      Model model) {

        //1.查询当前用户信息
//        AdminUser adminUser = (AdminUser) adminUserService.selectOne(Long.parseLong(adminUserId));
//        System.out.println("当前用户ID：" + adminUserId);
        //2.当前角色表
        List<Role> roleList = roleService.QueryAllRoles();
        //3.查询当前用户的角色信息列表
        AdminUserRole adminUserRole = new AdminUserRole();
        adminUserRole.setAdminUserId(Long.parseLong(adminUserId));
        List<AdminUserRole> adminUserRoleList = adminUserService.selectAdminUserRole(adminUserRole);
        for (AdminUserRole userRole : adminUserRoleList) {
            System.out.println("当前用户角色ID：" + userRole.getRoleId());
        }
//        model.addAttribute("adminUser", adminUser);
        model.addAttribute("adminUserRoleList", adminUserRoleList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("adminUserId", adminUserId);

        return "adminUserRole/update";
    }

    /**
     * 修改用户信息
     */
    @RequiresPermissions("adminUser:updateAdminUserRole")
    @RequestMapping(value = "updateAdminUserRole.do", method = RequestMethod.POST)
//    @ResponseBody
    public String updateAdminUserRoleDo(@RequestParam(value = "adminUserId", required = true) String adminUserId,
//                                                     @RequestParam(value = "account", required = false) String account,
//                                                     @RequestParam(value = "password", required = false) String password,
                                        @RequestParam(value = "roleId", required = false) String[] roleId, RedirectAttributes redirectAttributes
    ) {

        /**
         * 1.修改用户信息
         * 2.清空与拿来的用户角色信息表的关联
         * 3.重新添加用户角色信息关联
         */

//        Map<String, Object> resultMap = new HashMap<>();

        try {
            //1
//            AdminUser adminUser = new AdminUser();
//            adminUser.setAccount(account);
//            adminUser.setPassword(ShiroUtils.getMD5Str(password));
//            int i = adminUserService.update(adminUser);
            //2
            if (adminUserId == null && adminUserId.equals("")) {
                throw new Exception("当前用户ID为空");
            } else if (roleId != null) {
                AdminUserRole adminUserRole = new AdminUserRole();
                adminUserRole.setAdminUserId(Long.parseLong(adminUserId));
                adminUserService.delAdminUserRole(adminUserRole);
                for (String s : roleId) {
                    adminUserService.addAdminUserRole1(Long.parseLong(adminUserId), Long.parseLong(s.trim()));
                }


            }

            //3.

//            adminUserService.addAdminUserRole2(account, password, checkPid);
//            resultMap.put("status", 200);
//            resultMap.put("message", "修改成功");


        } catch (Exception e) {
            e.printStackTrace();
//            resultMap.put("status", 500);
//            resultMap.put("message", "修改失败");
        }


        return "redirect:list.do";
    }

    /**
     * 启用/禁用用户
     */
    @RequiresPermissions("adminUser:updateDisabled")
    @RequestMapping(value = "updateAdminUserDisabled.do",method = RequestMethod.GET)
//    @ResponseBody
    public String  updateAdminUserDisabled(@RequestParam(value = "adminUserId", required = true) String adminUserId) {
        //1.通过id查找到AdminUser
        //2.实现启用禁用
        Map<String, Object> map = new HashMap<>();
        Boolean updateAdminUserDisabled = adminUserService.updateAdminUserDisabled(Long.parseLong(adminUserId.trim()));
        if (updateAdminUserDisabled == true) {
            return "redirect:list.do";
//            map.put("status", 200);
//            map.put("message", "修改成功");

        }
           else return "error";
//        else {
//            map.put("status", 500);
//            map.put("message", "修改失败");
//        }
//        return map;
    }

    /**
     * 删除用户（前提是先禁用该用户）
     */
    @RequiresPermissions("adminUser:delete")
    @RequestMapping(value = "delete.do",method = RequestMethod.GET)
    public String deleteAdminUser(@RequestParam(value = "id",required = true) String adminUserId){
        AdminUser user = adminUserService.getAdminUserById(Long.parseLong(adminUserId.trim()));
        if (user.getIsDisabled() == true){
            adminUserService.deleteUser(Long.parseLong(adminUserId.trim()));
            System.out.println("用户已被删除!");
        }else {
            System.out.println("请先禁用该用户之后在此尝试删除!");
        }

        return "redirect:list.do";
    }

    /**
     * 认证用户密码
     */
    @RequiresPermissions("adminUser:updatePassWord")
    @ResponseBody
    @RequestMapping("/authentication.do")
    public Map<String, Object> doAuthentication(@RequestParam(value = "userID",required = true)String adminUserId,
                                                @RequestParam(value = "password",required = true)String password
    ){
        Map<String, Object> map = new HashMap<>();
        AdminUser adminUser = adminUserService.getAdminUserById(Long.parseLong(adminUserId));
        AdminUser checkUser = adminUser;
        checkUser.setPassword(password);
        PasswordHelper.generateMD5Pwd(checkUser);
//        System.out.println(checkUser.getPassword());
//        System.out.println(adminUser.getPassword());
        if (checkUser.getPassword() .equals(adminUser.getPassword()) ){
            map.put("status",200);
            map.put("message",true);
        }else {
            map.put("status",500);
            map.put("message",false);
        }
        return map;
    }

    /**
     * 更新用户密码
     */
    @RequiresPermissions("adminUser:updatePassWord")
    @ResponseBody
    @RequestMapping("/update.do")
    public Map<String, Object> doUpdate(@RequestParam(value = "userID",required = true)String adminUserId,
                                                @RequestParam(value = "newpassword",required = true)String newpassword
    ){
        Map<String, Object> map = new HashMap<>();
        AdminUser adminUser = adminUserService.getAdminUserById(Long.parseLong(adminUserId));
        adminUser.setPassword(newpassword);
        PasswordHelper.generateMD5Pwd(adminUser);
        Boolean updatePwd = adminUserService.updatePwd(adminUser);
        if (updatePwd){
            map.put("status",200);
            map.put("message","密码修改成功");
        }else {
            map.put("status",500);
            map.put("message","密码修改失败");
        }
        return map;
    }



}
