package org.lhz.service;

import org.lhz.entity.SysUser;

public interface SysProfileAndPasswordService {
    int updatePassword(SysUser sysUser);

    int updateProfile(SysUser sysUser);
}
