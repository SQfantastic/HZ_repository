package org.lhz.dao;

import org.lhz.entity.SysUser;

public interface SysProfileAndPasswordMapper {
    int updatePassword(SysUser sysUser);

    int updateProfile(SysUser sysUser);
}
