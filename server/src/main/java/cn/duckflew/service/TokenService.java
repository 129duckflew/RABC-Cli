package cn.duckflew.service;

import cn.duckflew.config.exception.CommonJsonException;
import cn.duckflew.dao.LoginMapper;
import cn.duckflew.dao.PermissionMapper;
import cn.duckflew.po.Permission;
import cn.duckflew.util.StringTools;
import cn.duckflew.util.constants.ErrorEnum;
import cn.duckflew.vo.SessionUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;


@Service
@Slf4j
public class TokenService
{
    @Autowired
    LoginMapper loginMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    PermissionMapper  permissionMapper;
    public String generateToken(String username)
    {
        //设置用户信息缓存
        String token= UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        setCache(token,username);
        return token;
    }

    private void setCache(String token, String username)
    {
        ValueOperations ops = redisTemplate.opsForValue();
        SessionUserInfo userInfo= getUserInfoByUsername(username);
        ops.set(token,userInfo, Duration.ofDays(3));
    }

    private SessionUserInfo getUserInfoByUsername(String username)
    {
        SessionUserInfo sessionUserInfo=loginMapper.getUserInfo(username);
        if (sessionUserInfo.getRoleIds().contains(1))
        {
            sessionUserInfo.setPermissionList(permissionMapper.getAllPerCodes());
            sessionUserInfo.setMenuList(permissionMapper.getAllMenuCodes());
        }
        return sessionUserInfo;
    }

    public SessionUserInfo getUserInfo()
    {
        String token = MDC.get("token");
        return getUserInfoFromCache(token);
    }

    /**
     * 未完待续
     * @param token
     * @return
     */
    private SessionUserInfo getUserInfoFromCache(String token)
    {
        if (StringTools.isNullOrEmpty(token)) {
            throw new CommonJsonException(ErrorEnum.E_20011);
        }
        log.debug("根据token从缓存中查询用户信息,{}", token);
        SessionUserInfo info = (SessionUserInfo) redisTemplate.opsForValue().get(token);
        if (info == null) {
            log.info("没拿到缓存 token={}", token);
            throw new CommonJsonException(ErrorEnum.E_20011);
        }
        return info;
    }
}
