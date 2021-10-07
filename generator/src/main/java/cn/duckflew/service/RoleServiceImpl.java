package cn.duckflew.service;

import cn.duckflew.pojo.Role;
import cn.duckflew.mapper.RoleMapper;
import cn.duckflew.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台角色表 服务实现类
 * </p>
 *
 * @author duckflew
 * @since 2021-10-05
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
