package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lhz.dao.SysRoleMapper;
import org.lhz.entity.SysMenu;
import org.lhz.entity.SysRole;
import org.lhz.vo.SysRoleVo;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SysRoleMapperImpl implements SysRoleMapper {
    /**
     * Infor: 查询所有的角色信息，包括模糊查询
     * @param sysRoleVo
     * @return : java.util.List<org.lhz.entity.SysRole>
     * @author : LHZ
     * @date : 2021/10/31 17:08
     */
    @Override
    public List<SysRole> findAllRoleList(SysRoleVo sysRoleVo) {
        String roleName = sysRoleVo.getRolename();
        String roleDesc = sysRoleVo.getRoledesc();
        Integer available = sysRoleVo.getAvailable();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        //创建一个集合存储查询参数
        ArrayList<Object> paramsList= new ArrayList<>();
        try {
            String sql = "select * from sys_role where 1=1";
            if (roleName!=null && !"".equals(roleName)) {
                sql+=" and rolename like  ? ";
                paramsList.add("%"+roleName+"%");
            }
            if (roleDesc!=null&&!"".equals(roleDesc)){
                sql+=" and roledesc like ?";
                paramsList.add("%"+roleDesc+"%");
            }
            if (available !=null&& !"".equals(available)){
                sql+=" and available =?";
                paramsList.add(available);
            }
            return runner.query(sql,new BeanListHandler<SysRole>(SysRole.class), paramsList.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 添加角色
     * @param sysRole
     * @return : int
     * @author : LHZ
     * @date : 2021/10/31 17:02
     */
    @Override
    public int addRole(SysRole sysRole) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "insert into sys_role (rolename,roledesc,available) values" +
                    "(?,?,?)";
            return runner.update(sql,sysRole.getRolename(), sysRole.getRoledesc(),sysRole.getAvailable());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 删除角色
     * @param roleid
     * @return : int
     * @author : LHZ
     * @date : 2021/10/31 17:19
     */
    @Override
    public int deleteRoleById(Integer roleid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "delete from sys_role where roleid =?";
            return runner.update(sql,roleid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 更新角色菜单
     * @param sysRole
     * @return : int
     * @author : LHZ
     * @date : 2021/10/31 23:37
     */
    @Override
    public int updateRole(SysRole sysRole) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "update sys_role set rolename=?,roledesc=?,available=? where roleid = ?";
            return runner.update(sql,sysRole.getRolename(),sysRole.getRoledesc(),sysRole.getAvailable(),sysRole.getRoleid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public List<SysMenu> findMenuListByRoleId(Integer roleid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "select t1.* from sys_menu t1 join sys_role_menu t2 on t1.id =t2.mid  " +
                    "where t2.rid =?";
            return runner.query(sql ,new BeanListHandler<SysMenu>(SysMenu.class),roleid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 根据用户id查询到该用户所包含的所有角色
     * @param userid
     * @return : java.util.List<org.lhz.entity.SysRole>
     * @author : LHZ
     * @date : 2021/11/1 16:37
     */
    @Override
    public List<SysRole> findRoleListByUserId(Integer userid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "select t1.* from sys_role t1 join sys_role_user t2 on t1.roleid =t2.rid  " +
                    "where t2.uid =?";
            return runner.query(sql ,new BeanListHandler<SysRole>(SysRole.class),userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 通过用户id删除该用户下的所有角色信息
     * @param userid
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 17:18
     */
    @Override
    public int deleteRoleByUserId(Integer userid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="delete from sys_role_user where uid =?";
            return runner.update(sql ,userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 查询总菜单数
     * @param
     * @return : java.lang.Long
     * @author : LHZ
     * @date : 2021/11/4 22:46
     */
    @Override
    public Long getTotal() {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "select count(*) from sys_role";
            return runner.query(sql,new ScalarHandler<>());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 根据用户id更新该用户的角色信息
     * @param roleid
     * @param userid
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 17:27
     */
    @Override
    public int insertRoleByUserId(Integer roleid,Integer userid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="insert into sys_role_user(rid,uid) values (?,?)";
            return runner.update(sql,roleid,userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
