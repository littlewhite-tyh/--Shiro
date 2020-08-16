package com.huike.controller;

import com.huike.domain.AdminUser;
import com.huike.domain.Permission;
import com.huike.domain.Role;
import com.huike.domain.RolePermission;
import com.huike.services.PermissionService;
import com.huike.services.RoleService;
import com.huike.vo.RoleVO;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @Autowired
    PermissionService permissionService;

//    Map<String,Object> map = new HashMap<>();
    @RequiresPermissions("role:list")
    @RequestMapping("/list.do")
    public String doRoleList(Model model,
                             @RequestParam(value = "currPage", required = false) Long curr,
                             @RequestParam(value = "pageSize", required = false) Long pageSize
    ) {
        if (curr == null) {
            curr = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        Long currPage = (curr - 1) * pageSize;
        Long totalPage = 0L;
        Long count = 0L;
        count = roleService.selectCount();
        if (count > 0L){
            totalPage = (count%pageSize == 0)?(count/pageSize):(count/pageSize +1);
        }
        List<Role> roleList = roleService.selectRoleByPage(currPage, pageSize);
        model.addAttribute("roleList",roleList);
        model.addAttribute("currPage",curr);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("count",count);

        return "role/list";
    }

    /**
     * 删除role中的用户
     *
     */
    @RequiresPermissions("role:delete")
    @RequestMapping(value = "/delete.do",method = RequestMethod.GET)
    public String deleteRole(@RequestParam(value = "id",required = true) String roleId){
        Boolean deleteRole = roleService.deleteRole(Long.parseLong(roleId.trim()));
        if (deleteRole == true){
            System.out.println("用户已被删除!");
        }else {
            System.out.println("删除失败!");
        }
        return "redirect:list.do";
    }

    /**
     * 更新role中信息
     */
    @RequiresPermissions("role:update")
    @RequestMapping(value = "/updateRole.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updateRole(Model model,
                                         @RequestParam(value = "roleID",required = true)String roleId,
                                         @RequestParam(value = "roleName",required = false)String roleName,
                                         @RequestParam(value = "description",required = false)String description){
        Map<String, Object> resultMap = new HashMap<>();
        Role role = new Role();
        role.setId(Long.parseLong(roleId.trim()));
//        System.out.println(description);
        if (roleName != null && !"".equals(roleName)){
            role.setName(roleName);
        }
        if (description != null && !"".equals(description)){
            role.setDescription(description);
        }
        Boolean updateRole = roleService.updateRole(role);
        if (updateRole == true){
            resultMap.put("status",200);
            resultMap.put("message","更新成功");
        }else {
            resultMap.put("status",500);
            resultMap.put("message","更新失败");
        }

        return resultMap;
    }

    /**
     * to add
     *
     */
    @RequiresPermissions("role:add")
    @RequestMapping(value = "/roleAdd.go",method = RequestMethod.GET)
    public String goRoleAdd( Model model){
        model.addAttribute("permissionList",permissionService.getAllPermissions(null));
        return "role/add";
    }

    @RequiresPermissions("role:add")
    @RequestMapping(value = "/roleAdd.do",method = RequestMethod.POST)
    public String doRoleAdd( RoleVO roleVO){
        Role role = new Role();
        role.setDescription(roleVO.getRoleDesc());
        role.setName(roleVO.getRoleName());
        role.setIsDeleted(false);
        roleService.addRole(role);
        String[] rolePermissionIdList = roleVO.getRolePermissionId();
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(role.getId());
        for (String s : rolePermissionIdList) {
            rolePermission.setPermissionId(Long.parseLong(s.trim()));
            permissionService.addRolePermissions(rolePermission);
        }

        return "redirect:list.do";
    }

    /**
     * go更新分配权限
     */
    @RequestMapping(value = "/goRolePermission",method = RequestMethod.GET)
    @RequiresPermissions("role:updateRolePermission")
    public String goRolePermission(@RequestParam(value = "roleId",required = true)String roleId,Model model){
        List<Permission> permissionList = permissionService.getAllPermissions(null);
        List<RolePermission> rolePermissions = permissionService.getPermissionIdsByRoleId(Long.parseLong(roleId));
        model.addAttribute("permissionList",permissionList);
        model.addAttribute("rolePermissions",rolePermissions);
        model.addAttribute("roleId",roleId);
        return "rolePermission/update";
    }
    /**
     * do更新分配权限
     */
    @ResponseBody
    @RequestMapping(value = "/updateRolePermission.do",method = RequestMethod.POST)
    @RequiresPermissions("role:updateRolePermission")
    public Map<String,Object> doRolePermission(@RequestParam(value = "checkPID",required = false)String [] checkPID,
                                               @RequestParam(value = "pids",required = false)String [] pIds,
                                               @RequestParam(value = "roleId",required = true) String roleId){
        Map<String, Object> map = new HashMap<>();
        if (!roleId.equals("")&&roleId != null){
            String[] rolePermissionIdList = checkPID;
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(Long.parseLong(roleId.trim()));
            permissionService.delRolePermissions(rolePermission);
            for (String s : rolePermissionIdList) {
                rolePermission.setPermissionId(Long.parseLong(s.trim()));
                permissionService.addRolePermissions(rolePermission);
            }
            map.put("status",200);
            map.put("message","分配成功");
        }
        else {
            map.put("status",500);
            map.put("message","分配失败");
        }

        return map;

    }








}
