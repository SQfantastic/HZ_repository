package org.lhz.dao;

import org.lhz.entity.SysMenu;
import org.lhz.vo.SysMenuVo;

import java.util.List;

public interface SysMenuMapper {
    List<SysMenu> findAllMenuList(SysMenuVo sysMenuVo);//查询到所有的菜单信息

    List<SysMenu> findMenuByUserId(Integer userid);

    List<SysMenu> findAllMenu(SysMenuVo sysMenuVo);//查询所有的菜单数据，包括表头的模糊查询

    int checkMenuById(Integer id);//根据菜单id查询该菜单是否有子菜单

    int deleteMenuById(Integer id);//根据菜单id删除该菜单

    int addMenu(SysMenu sysMenu);//添加菜单

    int updateMenu(SysMenu sysMenu);//更新菜单

    int insertMenuByRoleId(Integer roleid,Integer id);//根据角色id更新该角色下的菜单信息

    int deleteMenuByRoleId(Integer roleid);//根据角色id删除该角色下的所有菜单信息
}
