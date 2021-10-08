package cn.duckflew.service;

import cn.duckflew.dao.PermissionMapper;
import cn.duckflew.dao.RolePermissionMapper;
import cn.duckflew.pojo.Permission;
import cn.duckflew.pojo.RolePermission;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RolePermissionService extends ServiceImpl<RolePermissionMapper, RolePermission>
{

    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Autowired
    PermissionMapper permissionMapper;
    public List<Permission> getRolePerms(Integer roleId)
    {
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("role_id", roleId));
        Set<Integer> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
        List<Permission> permissions = permissionMapper.selectBatchIds(permissionIds);
        return permissions;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRolePerms(Integer roleId, Collection<?> permIds)
    {
        rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        for (Object permId : permIds)
        {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setUpdateTime(LocalDateTime.now());
            rolePermission.setPermissionId((Integer) permId);
            rolePermissionMapper.insert(rolePermission);
        }
    }
}
