package org.lhz.service;


import org.lhz.entity.SysUser;

public interface SysUserService {
     SysUser findUserByUsernameAndPassword(SysUser sysUser);

     SysUser findUserByUsername(String username);

     int addUser(SysUser sysUser);
}
