package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lhz.dao.SysMenuMapper;
import org.lhz.entity.SysMenu;
import org.lhz.vo.SysMenuVo;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.*;

public class SysMenuMapperImpl implements SysMenuMapper {
    /**
     * Infor:查询所有的菜单列表
     * @param sysMenuVo
     * @return : java.util.List<org.lhz.entity.SysMenu>
     * @author : LHZ
     * @date : 2021/10/27 22:36
     */
    @Override
    public List<SysMenu> findAllMenuList(SysMenuVo sysMenuVo) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="select * from sys_menu ";
            return runner.query(sql, new BeanListHandler<SysMenu>(SysMenu.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor: 通过用户id查询到对应的菜单列表
     * @param userid
     * @return : java.util.List<org.lhz.entity.SysMenu>
     * @author : LHZ
     * @date : 2021/10/30 12:04
     */
    @Override
    public List<SysMenu> findMenuByUserId(Integer userid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        //三表联查,通过该用户的id查询改用所对应的
        try {
            String sql ="select t1.* from sys_menu t1  join sys_role_menu t2 " +
                    " join sys_role_user t3 on(t1.id=t2.mid and t2.rid=t3.rid)"+
                    " where t3.uid=?";
            return runner.query(sql,new BeanListHandler<SysMenu>(SysMenu.class),userid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor:查询所有的菜单数据，包括模糊查询搜索功能
     * @param sysMenuVo
     * @return : java.util.List<org.lhz.entity.SysMenu>
     * @author : LHZ
     * @date : 2021/10/30 12:47
     */
    @Override
    public List<SysMenu> findAllMenu(SysMenuVo sysMenuVo) {
        //获取需要动态查询的字段
        String title = sysMenuVo.getTitle();
        Integer id = sysMenuVo.getId();
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        //为后面能够将参数和sql都交给preparedStatement对象来处理  将参数包装在一个集合中存储
        ArrayList<Object> paramsList = new ArrayList<>();
        //使用动态sql拼接的形式进行查询
        try {
            String sql="select * from sys_menu where 1=1";
            if(title!=null&&!"".equals(title)) {
                sql += " and title like ?";
                paramsList.add("%" + title + "%");
            }
            if(id!=null&&!"".equals(id)){
                sql+=" and (id =? or pid =?)";
                paramsList.add(id);
                paramsList.add(id);
            }
            sql+=";";
            return  runner.query(sql, new BeanListHandler<SysMenu>(SysMenu.class),paramsList.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Infor:根据菜单id查询该菜单下是否还有子菜单，有的话就不能删除
     * @param id
     * @return : int
     * @author : LHZ
     * @date : 2021/10/30 21:39
     */
    @Override
    public int checkMenuById(Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "select count(1) from sys_menu where pid=?";
            //这里是个坑，queryrunner在query时只能返回一个Long类型的数值，需要转换一下
            return ((Long)runner.query(sql, new ScalarHandler<>(), id)).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 根据菜单id删除菜单，前提是已经验证过了该菜单下没有子菜单了
     * @param id
     * @return : int
     * @author : LHZ
     * @date : 2021/10/30 22:02
     */
    @Override
    public int deleteMenuById(Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql = "delete from sys_menu where id =?";
            return runner.update(sql,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 添加菜单
     * @param sysMenu
     * @return : int
     * @author : LHZ
     * @date : 2021/10/31 8:45
     */
    @Override
    public int addMenu(SysMenu sysMenu) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="insert into sys_menu (pid,href,title,icon,target,spread,available)" +
                    "values(?,?,?,?,?,?,?)";
            return runner.update(sql,sysMenu.getPid(),sysMenu.getHref(),sysMenu.getTitle(),
                    sysMenu.getIcon(),sysMenu.getTarget()
                    ,sysMenu.getSpread(),sysMenu.getAvailable());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 更新菜单
     * @param sysMenu
     * @return : int
     * @author : LHZ
     * @date : 2021/10/31 12:03
     */
    @Override
    public int updateMenu(SysMenu sysMenu) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="update sys_menu set pid = ?,href =?,title = ?,icon=?,target =?,spread =?,available =? where id=?";
           return runner.update(sql,sysMenu.getPid(),sysMenu.getHref(),sysMenu.getTitle(),
                    sysMenu.getIcon(),sysMenu.getTarget()
                    ,sysMenu.getSpread(),sysMenu.getAvailable(),sysMenu.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 根据角色id更新该角色所有的菜单权限信息
     * @param roleid
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 10:31
     */
    @Override
    public int insertMenuByRoleId(Integer roleid,Integer id) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="insert into sys_role_menu(rid,mid) values (?,?)";
            return runner.update(sql,roleid,id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Infor: 根据角色id删除该角色下的所有菜单权限
     * @param roleid
     * @return : int
     * @author : LHZ
     * @date : 2021/11/1 11:21
     */
    @Override
    public int deleteMenuByRoleId(Integer roleid) {
        QueryRunner runner = new QueryRunner(DruidUtil.getDataSource());
        try {
            String sql ="delete from sys_role_menu where rid =?";
            return runner.update(sql ,roleid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
