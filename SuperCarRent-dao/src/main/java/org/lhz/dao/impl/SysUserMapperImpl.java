package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.lhz.dao.SysUserMapper;
import org.lhz.entity.SysUser;
import utils.DruidUtil;

import java.sql.SQLException;

public class SysUserMapperImpl implements SysUserMapper {
    /**
     * Infor: 用户登录
     * @param sysUser
     * @return : org.lhz.entity.SysUser
     * @author : LHZ
     * @date : 2021/10/27 10:01
     */
    @Override
    public SysUser findUserByUsernameAndPassword(SysUser sysUser) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="select * from sys_user where loginname =? and pwd =?";
            return runner.query(sql,new BeanHandler<SysUser>(SysUser.class),sysUser.getLoginname(),sysUser.getPwd());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 用于用户名的验重
     * @param username
     * @return : org.lhz.entity.SysUser
     * @author : LHZ
     * @date : 2021/10/27 10:01
     */
    @Override
    public SysUser findUserByUsername(String username) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String  sql = "select loginname from sys_user where loginname = ?";
            return runner.query(sql,new BeanHandler<SysUser>(SysUser.class),username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor:注册用户
     * @param sysUser
     * @return : int
     * @author : LHZ
     * @date : 2021/10/27 11:46
     */
    @Override
    public int insertUser(SysUser sysUser) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="insert into sys_user (loginname,identity,pwd,type,available) values (?,?,?,?,?)";
            return runner.update(sql,sysUser.getLoginname(),sysUser.getIdentity(),sysUser.getPwd(),sysUser.getType(),sysUser.getAvailable());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
