package org.lhz.dao;

import org.lhz.entity.SysUser;

public interface SysUserMapper {

    SysUser findUserByUsernameAndPassword(SysUser sysUser);//用户登录验证

    SysUser findUserByUsername(String username);//用户的注册名的验重

    int insertUser(SysUser sysUser);
}
