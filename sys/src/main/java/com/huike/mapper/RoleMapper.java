package com.huike.mapper;



import java.util.List;
import java.util.Map;

public interface RoleMapper<Role> extends Imapper {
    /**
     * 分页
     */
    List<Role> selectByPage(Map<String, Long> map);
    /**
     * 查询所有roles
     * @return
     */
    List<Role> QueryAllRoles();
    /**
     * 查询记录总数
     */
    Long selectCount();

    /**
     * 删除用户
     */
    Boolean deleteRole(Long roleId);

    /**
     * 更新用户信息，不返回Role
     */
    Integer updateRole(Role role);


}
