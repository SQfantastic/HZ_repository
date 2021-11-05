package org.lhz.dao;

import org.lhz.entity.SysUser;
import org.lhz.vo.SysUserVo;

import java.util.List;

public interface SysUserMapper {

    SysUser findUserByUsernameAndPassword(SysUser sysUser);//用户登录验证

    SysUser findUserByUsername(String username);//用户的注册名的验重

    int insertUser(SysUser sysUser);

    List<SysUser> findAllUserList(SysUserVo sysUserVo);//查找所有的用户信息，包括标头的模糊查询

    int deleteUserById(Integer userid);//根据用户id删除该用户

    int updateUser(SysUser sysUser);//更新用户信息

    int resetUserPwd(SysUser sysUser);//重置用户的密码为默认值123456

    SysUser findUserByUserId(Integer userid);//通过用户id查询到用户信息

    Long getTotal();//获取信息总数
}
