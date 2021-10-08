package cn.duckflew.controller;


import cn.duckflew.config.annotation.RequiresPermissions;
import cn.duckflew.pojo.Role;
import cn.duckflew.service.RoleService;
import cn.duckflew.util.CommonUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 后台角色表 前端控制器
 * </p>
 *
 * @author duckflew
 * @since 2021-10-05
 */
@RestController
@RequestMapping("/role")
public class RoleController {


    @Autowired
    RoleService roleService;


    @RequiresPermissions({"role:list"})
    @GetMapping("/")
    public JSONObject getAllRoles()
    {
        List<Role> roles = roleService.list();
        return CommonUtils.successJson(roles);
    }

    @RequiresPermissions({"role:add"})
    @PostMapping("/")
    public JSONObject addRole(@RequestBody JSONObject jsonObject)
    {
        CommonUtils.hasAllRequired(jsonObject,"roleName");
        Role role = new Role();
        role.setCreateTime(LocalDateTime.now());
        role.setDeleteStatus("1");
        role.setRoleName(jsonObject.getString("roleName"));
        roleService.save(role);
        return CommonUtils.successJson(role);
    }
    @RequiresPermissions({"role:update"})
    @PutMapping("/")
    public JSONObject updateRole(@RequestBody JSONObject jsonObject)
    {
        CommonUtils.hasAllRequired(jsonObject,"id,roleName,deleteStatus");
        Role role = new Role();
        role.setRoleName(jsonObject.getString("roleName"));
        role.setDeleteStatus(jsonObject.getString("deleteStatus"));
        role.setId(jsonObject.getInteger("id"));
        role.setUpdateTime(LocalDateTime.now());
        roleService.updateById(role);
        return CommonUtils.successJson();
    }
    @RequiresPermissions("role:delete")
    @DeleteMapping("/{id}")
    public JSONObject deleteRole(@PathVariable Integer id)
    {
        return roleService.deleteRoleById(id);
    }
}
