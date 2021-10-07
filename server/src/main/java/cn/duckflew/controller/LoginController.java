package cn.duckflew.controller;

import cn.duckflew.service.LoginService;
import cn.duckflew.util.CommonUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController
{

    @Autowired
    LoginService loginService;
    @PostMapping("/auth")
    public JSONObject authLogin(@RequestBody JSONObject authJson)
    {
        CommonUtils.hasAllRequired(authJson,"username,password");
        return loginService.authLogin(authJson);
    }

    /**
     * 获取当前登录用户的信息
     * @return
     */
    @GetMapping("/info")
    public JSONObject getInfo()
    {
        return loginService.getInfo();
    }
}
