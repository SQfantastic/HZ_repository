package org.lhz.service.impl;


import org.lhz.dao.SysUserMapper;
import org.lhz.dao.impl.SysUserMapperImpl;
import org.lhz.entity.SysUser;
import org.lhz.service.SysUserService;
import org.springframework.util.DigestUtils;

import java.util.UUID;


public class SysUserServiceImpl implements SysUserService {
    //注入dao层对象
   private SysUserMapper sysUserMapper = new SysUserMapperImpl();


    /**
     * Infor:验证登录，需要使用md5进行密码的加密
     * @param sysUser
     * @return : org.lhz.entity.SysUser
     * @author : LHZ
     * @date : 2021/10/27 9:54
     */
    @Override
    public SysUser findUserByUsernameAndPassword(SysUser sysUser) {
        String pwd = DigestUtils.md5DigestAsHex(sysUser.getPwd().getBytes());
        sysUser.setPwd(pwd);
        return sysUserMapper.findUserByUsernameAndPassword(sysUser);
    }

    /**
     * Infor:验证用户名是否重复
     * @param username
     * @return : org.lhz.entity.SysUser
     * @author : LHZ
     * @date : 2021/10/27 9:54
     */
    @Override
    public SysUser findUserByUsername(String username) {
        return sysUserMapper.findUserByUsername(username);
    }

    /**
     * Infor:
     * @param sysUser
     * @return : int
     * @author : LHZ
     * @date : 2021/10/27 11:40
     */
    @Override
    public int addUser(SysUser sysUser) {
        //对密码进行加密，并对部分属性设置默认值
        sysUser.setIdentity(UUID.randomUUID().toString());
        sysUser.setPwd(DigestUtils.md5DigestAsHex(sysUser.getPwd().getBytes()));
        sysUser.setType(2);
        sysUser.setAvailable(1);
        return sysUserMapper.insertUser(sysUser);
    }
}
