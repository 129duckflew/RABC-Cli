package cn.duckflew.service;

import cn.duckflew.pojo.User;
import cn.duckflew.mapper.UserMapper;
import cn.duckflew.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author duckflew
 * @since 2021-10-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
