package cn.duckflew.dao;

import cn.duckflew.po.User;
import cn.duckflew.vo.SessionUserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface LoginMapper extends BaseMapper<User>
{

    SessionUserInfo getUserInfo(String username);
}
