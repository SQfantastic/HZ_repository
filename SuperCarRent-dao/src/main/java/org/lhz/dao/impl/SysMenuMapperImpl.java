package org.lhz.dao.impl;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lhz.dao.SysMenuMapper;
import org.lhz.entity.SysMenu;
import org.lhz.vo.SysMenuVo;
import utils.DruidUtil;

import java.sql.SQLException;
import java.util.List;

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

    @Override
    public List<SysMenu> findMenuByUserId(Integer userid) {
        return null;
    }
}
