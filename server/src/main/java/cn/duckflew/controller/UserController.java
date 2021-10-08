package cn.duckflew.controller;


import cn.duckflew.config.annotation.RequiresPermissions;
import cn.duckflew.pojo.User;
import cn.duckflew.service.UserService;
import cn.duckflew.util.CommonUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author duckflew
 * @since 2021-10-08
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/")
    @RequiresPermissions("user:add")
    JSONObject addUser(@RequestBody JSONObject userData)
    {
        CommonUtils.hasAllRequired(userData,"username,password,nickname");
        User user = new User();
        user.setUsername((String) userData.get("username"));
        user.setPassword((String) userData.get("password"));
        user.setNickname((String) userData.get("nickname"));
        user.setCreateTime(LocalDateTime.now());
        user.setDeleteStatus("1");
        userService.save(user);
        return CommonUtils.successJson(user);
    }
    @PutMapping("/")
    @RequiresPermissions("user:update")
    JSONObject updateUser(@RequestBody JSONObject userData)
    {
        CommonUtils.hasAllRequired(userData,"id,username,password,nickname,deleteStatus");
        User user = new User();
        user.setId( userData.getInteger("id"));
        user.setNickname((String) userData.get("nickname"));
        user.setUsername((String) userData.get("username"));
        user.setPassword((String) userData.get("password"));
        user.setDeleteStatus((String) userData.get("deleteStatus"));
        user.setUpdateTime(LocalDateTime.now());
        userService.updateById(user);
        return CommonUtils.successJson();
    }
    @GetMapping("/")
    @RequiresPermissions("user:list")
    JSONObject getUserByPage(@RequestBody JSONObject pageParams)
    {
        CommonUtils.hasAllRequired(pageParams,"currentPage,pageSize");
        Page<User> page=new Page<>(pageParams.getInteger("currentPage"),pageParams.getInteger("pageSize"));
        Page<User> pageRes = userService.page(page);
        JSONObject resJson = new JSONObject();
        resJson.put("total",pageRes.getTotal());
        resJson.put("records",pageRes.getRecords());
        return CommonUtils.successJson(resJson);
    }
    @DeleteMapping("/{id}")
    @RequiresPermissions("user:delete")
    JSONObject deleteUser(@PathVariable Integer id)
    {
        userService.removeById(id);
        return CommonUtils.successJson();
    }
}
