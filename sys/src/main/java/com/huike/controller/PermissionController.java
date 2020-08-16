package com.huike.controller;

import com.huike.domain.Permission;
import com.huike.domain.Role;
import com.huike.domain.RolePermission;
import com.huike.services.PermissionService;
import com.huike.vo.PermissionVO;
import com.huike.vo.RoleVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    PermissionService permissionService;

    @RequestMapping(value = "/list.do",method = RequestMethod.GET)
    @RequiresPermissions("permission:list")
    public String doPermissionList(Model model,
                                   @RequestParam(value = "currPage", required = false) Long curr,
                                   @RequestParam(value = "pageSize", required = false) Long pageSize){
        if (curr == null) {
            curr = 1L;
        }
        if (pageSize == null) {
            pageSize = 10L;
        }
        Long currPage = (curr - 1) * pageSize;
        Long totalPage = 0L;
        Long count = 0L;
        count = permissionService.selectCount();
        if (count > 0L){
            totalPage = (count%pageSize == 0)?(count/pageSize):(count/pageSize +1);
        }
        List<Permission> permissionList = permissionService.selectByPage(currPage, pageSize);
        model.addAttribute("permissionList",permissionList);
        model.addAttribute("currPage",curr);
        model.addAttribute("pageSize",pageSize);
        model.addAttribute("totalPage",totalPage);
        model.addAttribute("count",count);

        return "permission/list";
    }

    /**
     * 伪删除permission中的权限
     *
     */
    @RequiresPermissions("permission:delete")
    @RequestMapping(value = "/delete.do",method = RequestMethod.GET)
    public String deletePermission(@RequestParam(value = "id",required = true) String Id){
        Boolean deleteRole = permissionService.deletePermission(Long.parseLong(Id.trim()));
        if (deleteRole == true){
            System.out.println("用户已被删除!");
        }else {
            System.out.println("删除失败!");
        }
        return "redirect:list.do";
    }

    /**
     * 更新permission中信息
     */
    @RequiresPermissions("permission:update")
    @RequestMapping(value = "/permissionUpdate.do",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> updatePermission(Model model,
                                         @RequestParam(value = "permission",required = true)String permission,
                                         @RequestParam(value = "permissionID",required = true)String permissionID,
                                         @RequestParam(value = "description",required = false)String description){
        Map<String, Object> resultMap = new HashMap<>();
        Permission permission1 = new Permission();
        permission1.setId(permissionID);
        permission1.setPermission(permission);
        permission1.setDescription(description);
        Boolean update = permissionService.updatePermission(permission1);
        if (update == true){
            resultMap.put("status",200);
            resultMap.put("message","更新成功");
        }else {
            resultMap.put("status",500);
            resultMap.put("message","更新失败");
        }
        return resultMap;
    }

    /**
     *
     *add
     */
    @ResponseBody
    @RequiresPermissions("permission:add")
    @RequestMapping(value = "/permissionAdd.do",method = RequestMethod.POST)
    public Map<String,Object> doRoleAdd( PermissionVO permissionVO){
        Map<String, Object> resultMap = new HashMap<>();
        Permission permission = new Permission();
        permission.setDescription(permissionVO.getDescription());
        permission.setPermission(permissionVO.getDescription());
        permission.setIsDeleted(false);
        Boolean addPermission = permissionService.addPermission(permission);
        if (addPermission == true){
            resultMap.put("status",200);
            resultMap.put("message","更新成功");
        }else {
            resultMap.put("status",500);
            resultMap.put("message","更新失败");
        }
        return resultMap;
    }


}
