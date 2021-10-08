package cn.duckflew.controller;


import cn.duckflew.config.annotation.RequiresPermissions;
import cn.duckflew.dao.PermissionMapper;
import cn.duckflew.pojo.Permission;
import cn.duckflew.service.RolePermissionService;
import cn.duckflew.util.CommonUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 角色-权限关联表 前端控制器
 * </p>
 *
 * @author duckflew
 * @since 2021-10-08
 */
@RestController
@RequestMapping("/rolePermission")
@Slf4j
public class RolePermissionController {

    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    PermissionMapper permissionMapper;
    /**
     * 获取角色的权限
     * @return
     */
    @RequiresPermissions({"role:list"})
    @GetMapping("/{roleId}")
    public JSONObject getRolePerms(@PathVariable Integer roleId)
    {
        List<Permission> permissions = rolePermissionService.getRolePerms(roleId);
        return CommonUtils.successJson(permissions);
    }

    /**
     * 为角色添加操作权限或者更新操作权限
     * @param jsonObject
     * @return
     */
    @RequiresPermissions({"role:update"})
    @PutMapping("/")
    public JSONObject addRolPerms(@RequestBody JSONObject jsonObject)
    {
        CommonUtils.hasAllRequired(jsonObject,"roleId,permIds");
        Integer roleId = jsonObject.getInteger("roleId");
        JSONArray permIds = jsonObject.getJSONArray("permIds");
        rolePermissionService.updateRolePerms(roleId,permIds);
        return CommonUtils.successJson();
    }
}
