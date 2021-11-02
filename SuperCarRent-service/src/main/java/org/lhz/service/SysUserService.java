package org.lhz.service;


import org.lhz.entity.SysRole;
import org.lhz.entity.SysUser;
import org.lhz.vo.SysUserVo;

import java.util.List;

public interface SysUserService {
     SysUser findUserByUsernameAndPassword(SysUser sysUser);

     SysUser findUserByUsername(String username);

     int addUser(SysUser sysUser);

    List<SysUser> findAllUserList(SysUserVo sysUserVo);

    int deleteUserById(Integer userid);

    int updateUser(SysUser sysUser);

    int resetUserPwd(Integer userid);
}
