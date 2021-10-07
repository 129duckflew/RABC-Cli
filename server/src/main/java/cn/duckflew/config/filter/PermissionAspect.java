package cn.duckflew.config.filter;


import cn.duckflew.config.annotation.Logical;
import cn.duckflew.config.annotation.RequiresPermissions;
import cn.duckflew.config.exception.UnAuthorizationException;
import cn.duckflew.service.TokenService;
import cn.duckflew.vo.SessionUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;

@Aspect
@Slf4j
@Component
@Order(3)
public class PermissionAspect
{
    @Autowired
    TokenService tokenService;

    @Before("@annotation(cn.duckflew.config.annotation.RequiresPermissions)")
    public void before(JoinPoint joinPoint)
    {
        log.debug("开始校验操作权限");
        SessionUserInfo userInfo = tokenService.getUserInfo();
        Set<String> userInfoPermissionList = userInfo.getPermissionList();//权限列表
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature= (MethodSignature) signature;
        RequiresPermissions annotationOnMethod=methodSignature.getMethod().getAnnotation(RequiresPermissions.class);
        String[] permissionsOnMethod=annotationOnMethod.value();
        log.debug("校验权限code: {}", Arrays.toString(permissionsOnMethod));
        log.debug("用户已有权限: {}", userInfoPermissionList);

        if (annotationOnMethod.Logical()== Logical.AND)
        {
            for (String requirePermission : permissionsOnMethod)
            {
                if (!userInfoPermissionList.contains(requirePermission))
                {
                    log.warn("用户缺少权限:{}",requirePermission);
                    throw new UnAuthorizationException();
                }
            }
        }
        else {
            boolean flag=false;  //至少具有一个权限
            for (String requirePermission : permissionsOnMethod)
            {
                if (userInfoPermissionList.contains(requirePermission))
                {
                    flag=true;
                    break;
                }
            }
            if (!flag)
            {
                log.warn("用户缺少权限 code= : {} (任意有一种即可)", Arrays.toString(permissionsOnMethod));
                throw new UnAuthorizationException();
            }
        }
    }
}
