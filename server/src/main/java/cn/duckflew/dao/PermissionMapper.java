package cn.duckflew.dao;

import cn.duckflew.po.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Set;

/**
 * <p>
 * 后台权限表 Mapper 接口
 * </p>
 *
 * @author duckflew
 * @since 2021-10-05
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    Set<String> getAllPerCodes();

    Set<String> getAllMenuCodes();
}
