package cn.duckflew.service;

import cn.duckflew.config.exception.CommonJsonException;
import cn.duckflew.dao.LoginMapper;
import cn.duckflew.pojo.User;
import cn.duckflew.util.CommonUtils;
import cn.duckflew.util.constants.ErrorEnum;
import cn.duckflew.vo.SessionUserInfo;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginService extends ServiceImpl<LoginMapper,User>
{

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    TokenService tokenService;

    public JSONObject authLogin(JSONObject authJson)
    {
        String username=authJson.getString("username");
        String password=authJson.getString("password");
        JSONObject info=new JSONObject();
        User user = loginMapper.selectOne(new QueryWrapper<User>().eq("username", username).eq("password", password));
        if (user==null)
        {
            throw new CommonJsonException(ErrorEnum.E_10010);
        }
        String token=tokenService.generateToken(username);
        info.put("token",token);
        return CommonUtils.successJson(info);
    }

    public JSONObject getInfo()
    {
        SessionUserInfo userInfo=tokenService.getUserInfo();
        log.info(userInfo.toString());
        return CommonUtils.successJson(userInfo);
    }
}
