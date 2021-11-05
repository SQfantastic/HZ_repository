package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.lhz.dao.SysProfileAndPasswordMapper;
import org.lhz.entity.SysUser;
import utils.DruidUtil;

import java.sql.SQLException;

public class SysProfileAndPasswordMapperImpl implements SysProfileAndPasswordMapper {
    /**
     * Infor: 更新用户密码
     * @param sysUser
     * @return : int
     * @author : LHZ
     * @date : 2021/11/3 23:26
     */
    @Override
    public int updatePassword(SysUser sysUser) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "update sys_user set pwd =? where userid =?";
            return runner.update(sql,sysUser.getPwd(),sysUser.getUserid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    /**
     * Infor:更新用户信息
     * @param sysUser
     * @return : int
     * @author : LHZ
     * @date : 2021/11/3 23:50
     */
    @Override
    public int updateProfile(SysUser sysUser) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "update sys_user set realname=?,loginname=?,identity=?,phone=?," +
                    "address=?,sex=? where userid =?";
            return runner.update(sql,sysUser.getRealname(),sysUser.getLoginname()
                    ,sysUser.getIdentity(),sysUser.getPhone(),sysUser.getAddress(),sysUser.getSex(),sysUser.getUserid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
