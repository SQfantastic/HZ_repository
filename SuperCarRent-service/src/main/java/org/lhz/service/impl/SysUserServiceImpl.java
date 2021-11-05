package org.lhz.service.impl;


import cn.hutool.crypto.SecureUtil;
import org.lhz.dao.SysUserMapper;
import org.lhz.dao.impl.SysUserMapperImpl;
import org.lhz.entity.SysUser;
import org.lhz.service.SysUserService;
import org.lhz.vo.SysUserVo;
import utils.SysTips;


import java.util.List;
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
        String pwd = SecureUtil.md5(sysUser.getPwd());
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
        sysUser.setPwd(SecureUtil.md5(sysUser.getPwd()));
        sysUser.setType(2);
        sysUser.setAvailable(1);
        return sysUserMapper.insertUser(sysUser);
    }

    /**
     * Infor: 查找所有的用户信息，包括表头的模糊查询
     * @param sysUserVo
     * @return : java.util.List<org.lhz.entity.SysUser>
     * @author : LHZ
     * @date : 2021/11/1 14:02
     */
    @Override
    public List<SysUser> findAllUserList(SysUserVo sysUserVo) {
        return sysUserMapper.findAllUserList(sysUserVo);
    }

    /**
     * Infor: 根据用户id删除用户信息
     * @param userid
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 15:15
     */
    @Override
    public int deleteUserById(Integer userid) {
        return sysUserMapper.deleteUserById(userid);
    }
    /**
     * Infor: 更新用户信息
     * @param sysUser
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 15:16
     */
    @Override
    public int updateUser(SysUser sysUser) {
        return sysUserMapper.updateUser(sysUser);
    }

    /**
     * Infor: 根据用户id重置用户的密码,赋值为123456
     * @param userid
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 18:43
     */
    @Override
    public int resetUserPwd(Integer userid) {
        SysUser sysUser = new SysUser();
        sysUser.setUserid(userid);
        sysUser.setPwd(SecureUtil.md5(SysTips.USER_DEFAULT_PWD));
        return sysUserMapper.resetUserPwd(sysUser);
    }

    /**
     * Infor: 根据用户id查询用户信息
     * @param userid
     * @return : org.lhz.entity.SysUser
     * @author : LHZ
     * @date : 2021/11/4 0:12
     */
    @Override
    public SysUser findUserByUserId(Integer userid) {
        return sysUserMapper.findUserByUserId(userid);
    }

    @Override
    public Long getTotal() {
        return sysUserMapper.getTotal();
    }
}
