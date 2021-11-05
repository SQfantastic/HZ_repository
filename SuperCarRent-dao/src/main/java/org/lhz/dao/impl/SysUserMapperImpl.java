package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lhz.dao.SysUserMapper;
import org.lhz.entity.SysUser;
import org.lhz.vo.SysUserVo;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * Infor: 查找所有的用户信息，包括表头的模糊查询
     * @param sysUserVo
     * @return : java.util.List<org.lhz.entity.SysUser>
     * @author : LHZ
     * @date : 2021/11/1 14:05
     */
    @Override
    public List<SysUser> findAllUserList(SysUserVo sysUserVo) {
        String realname = sysUserVo.getRealname();
        String loginname = sysUserVo.getLoginname();
        String identity = sysUserVo.getIdentity();
        String address = sysUserVo.getAddress();
        String phone = sysUserVo.getPhone();
        Integer sex = sysUserVo.getSex();
        Integer page = sysUserVo.getPage();
        Integer limit = sysUserVo.getLimit();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        ArrayList<Object> paramsList = new ArrayList<>();
        try {
            String sql = "select * from sys_user where 1=1";
            if (realname!=null && !"".equals(realname)) {
                sql+=" and realname like  ? ";
                paramsList.add("%"+realname+"%");
            }
            if (loginname!=null&&!"".equals(loginname)){
                sql+=" and loginname like ?";
                paramsList.add("%"+loginname+"%");
            }
            if (identity !=null&& !"".equals(identity)){
                sql+=" and identity like ?";
                paramsList.add("%"+identity+"%");
            }
            if (address !=null&& !"".equals(address)){
                sql+=" and address like ?";
                paramsList.add("%"+address+"%");
            }
            if (phone !=null&& !"".equals(phone)){
                sql+=" and phone like ?";
                paramsList.add("%"+phone+"%");
            }
            if (sex !=null){
                sql+=" and sex = ? ";
                paramsList.add(sex);
            }
            sql+=" limit "+(page-1)*limit+","+limit;
            return  runner.query(sql, new BeanListHandler<SysUser>(SysUser.class),paramsList.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 根据用户id删除用户
     * @param userid
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 15:02
     */
    @Override
    public int deleteUserById(Integer userid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "delete from sys_user where userid =?";
            return runner.update(sql,userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 更新用户信息
     * @param sysUser
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 15:17
     */
    @Override
    public int updateUser(SysUser sysUser) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "update sys_user set realname=?,loginname=?,identity=?,phone=?," +
                    "address=?,sex=?,position=?,available=? where userid =?";
            return runner.update(sql,sysUser.getRealname(),sysUser.getLoginname()
            ,sysUser.getIdentity(),sysUser.getPhone(),sysUser.getAddress(),sysUser.getSex(),
                    sysUser.getPosition(),sysUser.getAvailable(),sysUser.getUserid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 重置用户的密码，为默认值123456
     * @param sysUser
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 18:46
     */
    @Override
    public int resetUserPwd(SysUser sysUser) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "update sys_user set pwd=? where userid =?";
            return runner.update(sql,sysUser.getPwd(),sysUser.getUserid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 通过用户id查询到用户信息
     * @param userid
     * @return : org.lhz.entity.SysUser
     * @author : LHZ
     * @date : 2021/11/4 0:14
     */
    @Override
    public SysUser findUserByUserId(Integer userid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String  sql = "select * from sys_user where userid = ?";
            return runner.query(sql,new BeanHandler<SysUser>(SysUser.class),userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 获取用户总数
     * @param
     * @return : int
     * @author : LHZ
     * @date : 2021/11/4 22:39
     */
    @Override
    public Long getTotal() {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String  sql = "select count(*) from sys_user ";
            return runner.query(sql,new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
