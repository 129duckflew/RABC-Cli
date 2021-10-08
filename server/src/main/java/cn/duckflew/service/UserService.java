package cn.duckflew.service;

import cn.duckflew.dao.UserMapper;
import cn.duckflew.pojo.User;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author duckflew
 * @since 2021-10-08
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User>
{

}
