package cn.duckflew.service;

import cn.duckflew.dao.RoleMapper;
import cn.duckflew.dao.RolePermissionMapper;
import cn.duckflew.pojo.Role;
import cn.duckflew.pojo.RolePermission;
import cn.duckflew.util.CommonUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role>
{

    @Autowired
    RoleMapper roleMapper;
    @Autowired
    RolePermissionMapper rolePermissionMapper;
    @Transactional(rollbackFor = Exception.class)
    public JSONObject deleteRoleById(Integer id)
    {
        rolePermissionMapper.delete(new QueryWrapper<RolePermission>().eq("role_id",id));
        roleMapper.deleteById(id);
        return CommonUtils.successJson();
    }
}
