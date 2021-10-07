package cn.duckflew.service;

import cn.duckflew.pojo.Permission;
import cn.duckflew.mapper.PermissionMapper;
import cn.duckflew.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台权限表 服务实现类
 * </p>
 *
 * @author duckflew
 * @since 2021-10-05
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
