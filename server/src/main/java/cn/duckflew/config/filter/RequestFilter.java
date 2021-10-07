package cn.duckflew.config.filter;

import cn.duckflew.config.exception.CommonJsonException;
import cn.duckflew.service.TokenService;
import cn.duckflew.util.StringTools;
import cn.duckflew.vo.SessionUserInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class RequestFilter extends OncePerRequestFilter implements Filter
{
    @Autowired
    TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException
    {
        try
        {
            setUsername(req);

            filterChain.doFilter(req,resp);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            MDC.clear();
        }
    }

    private void setUsername(HttpServletRequest request)
    {
        String token = request.getHeader("token");
        if (!StringTools.isNullOrEmpty(token))
        {
            MDC.put("token",token);
            try
            {
                SessionUserInfo userInfo = tokenService.getUserInfo();
                if (userInfo!=null)
                {
                    String username = userInfo.getUsername();
                    MDC.put("username",username);
                }
            } catch (CommonJsonException e)
            {
                log.info("无效的token:{}",token);
            }
        }
    }
}
