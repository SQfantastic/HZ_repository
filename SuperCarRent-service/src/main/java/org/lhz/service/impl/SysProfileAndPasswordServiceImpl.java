package org.lhz.service.impl;

import org.lhz.dao.SysProfileAndPasswordMapper;
import org.lhz.dao.impl.SysProfileAndPasswordMapperImpl;
import org.lhz.entity.SysUser;
import org.lhz.service.SysProfileAndPasswordService;

public class SysProfileAndPasswordServiceImpl implements SysProfileAndPasswordService {
    //注入dao层对象
    private SysProfileAndPasswordMapper sysProfileAndPasswordMapper = new SysProfileAndPasswordMapperImpl();
    @Override
    public int updatePassword(SysUser sysUser) {
        return sysProfileAndPasswordMapper.updatePassword(sysUser);
    }

    @Override
    public int updateProfile(SysUser sysUser) {
        return sysProfileAndPasswordMapper.updateProfile(sysUser);
    }
}
